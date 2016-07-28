package io.jasonsparc.chemistry;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.LongSparseArray;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;

import io.jasonsparc.chemistry.internal.util.ThreadUtils;
import io.jasonsparc.chemistry.internal.util.ThrowableSignal;
import io.jasonsparc.chemistry.internal.util.ViewTypes;

import static io.jasonsparc.chemistry.Chemistry.getItemClass;

/**
 * TODO Docs
 *
 * Created by jason on 07/07/2016.
 */
@SuppressWarnings("unchecked")
public abstract class ChemistryAdapter<Item> extends RecyclerView.Adapter<ViewHolder> {
	@NonNull final Chemistry chemistry;
	@Nullable CacheState cacheState;

	public ChemistryAdapter(@NonNull Chemistry chemistry) {
		this.chemistry = chemistry;
	}

	@Override
	public abstract int getItemCount();

	public abstract Item getItem(int position);

	@Override
	public long getItemId(int position) {
		if (!hasStableIds()) {
			return RecyclerView.NO_ID;
		}
		return getItemIdInternal(getItem(position));
	}

	public long getItemId(Item item) {
		if (!hasStableIds()) {
			return RecyclerView.NO_ID;
		}
		return getItemIdInternal(item);
	}

	private long getItemIdInternal(Item item) {
		final CacheState cacheState = getCacheState();
		final Class<?> itemClass = getItemClass(item);
		IdSelector idSelector = cacheState.idSelectors.get(itemClass);

		if (idSelector == null) {
			idSelector = chemistry.findIdSelector(itemClass, 0);

			if (idSelector == null)
				idSelector = NO_ID_SELECTOR;

			cacheState.idSelectors.put(itemClass, idSelector);
		}

		return idSelector.getItemId(item);
	}

	@Override
	public int getItemViewType(int position) {
		final Item item = getItem(position);
		try {
			return getItemViewTypeInternal(item);
		} catch (NullFlaskSignal s) {
			throw new NullPointerException(item == null
					? "Null `Flask` associated for null item at position " + position
					: "Null `Flask` associated for item at position " + position + " with class: `" + item.getClass().getName() + "`"
			);
		}
	}

	public int getItemViewType(Item item) {
		try {
			return getItemViewTypeInternal(item);
		} catch (NullFlaskSignal s) {
			throw new NullPointerException(item == null
					? "Null `Flask` associated for null item."
					: "Null `Flask` associated for item! Class: " + item.getClass().getName() + "; Item: " + item
			);
		}
	}

	private int getItemViewTypeInternal(Item item) {
		final CacheState cacheState = getCacheState();
		final Class<?> itemClass = getItemClass(item);
		FlaskSelector flaskSelector = cacheState.flaskSelectors.get(itemClass);

		if (flaskSelector == null) {
			flaskSelector = chemistry.findFlaskSelector(itemClass, 0);

			if (flaskSelector == null) {
				throw new NullPointerException(item == null
						? "No `FlaskSelector` found for null items."
						: "No `FlaskSelector` found for items with class: `" + itemClass.getName() + "`"
				);
			}

			cacheState.flaskSelectors.put(itemClass, flaskSelector);
		}

		final Flask itemFlask = flaskSelector.getItemFlask(item);
		if (itemFlask == null) {
			throw new NullFlaskSignal();
		}

		@ViewType
		final int viewType = itemFlask.getViewType();
		ViewTypes.validateState(viewType);

		final Flask cachedFlask = cacheState.flasks.get(viewType);
		if (cachedFlask == null) {
			cacheState.flasks.put(viewType, itemFlask);
		}

		return viewType;
	}

	private static class NullFlaskSignal extends ThrowableSignal {
		NullFlaskSignal() { }
	}

	@NonNull
	public final Chemistry getChemistry() {
		return chemistry;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		final Flask flask = getCacheState().flasks.get(viewType);
		if (flask == null) {
			throw new NullPointerException("`Flask` for the specified `viewType` either does not exist or has not yet been initialized.");
		}
		return flask.createViewHolder(parent);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		final Item item = getItem(position);

		final CacheState cacheState = getCacheState();
		final Class<?> itemClass = getItemClass(item);
		final int viewType = holder.getItemViewType();

		final long itemBinderKey = (((long) viewType) << 32) | itemClass.hashCode();
		ItemBinder itemBinder = cacheState.itemBinders.get(itemBinderKey);

		if (itemBinder == null) {
			final Flask flask = cacheState.flasks.get(viewType);
			if (flask == null) {
				// TODO Try to find associated flask before throwing an error

				throw new NullPointerException("`Flask` for the specified `viewType` either does not exist or has not yet been initialized.");
			}

			itemBinder = chemistry.findItemBinder(itemClass, holder.getClass(), flask, 0);

			if (itemBinder == null) {
				throw new NullPointerException(item == null
						? "No `ItemBinder` found! Item Class: null item; VH Class: " + holder.getClass().getName() + "; Flask: " + flask
						: "No `ItemBinder` found! Item Class: " + itemClass.getName() + "; VH Class: " + holder.getClass().getName() + "; Flask: " + flask);
			}

			cacheState.itemBinders.put(itemBinderKey, itemBinder);
		}

		itemBinder.bindViewHolder(holder, item);
	}

	@NonNull
	CacheState getCacheState() {
		if (cacheState == null) {
			cacheState = new CacheState();
		}
		return cacheState;
	}

	static class CacheState {
		IdentityHashMap<Class<?>, FlaskSelector> flaskSelectors = new IdentityHashMap<>();
		IdentityHashMap<Class<?>, IdSelector> idSelectors = new IdentityHashMap<>();
		SparseArray<Flask> flasks = new SparseArray<>();
		LongSparseArray<ItemBinder> itemBinders = new LongSparseArray<>();
		ArrayList<ItemState<?>> itemStates = new ArrayList<>();
	}

	private static final IdSelector NO_ID_SELECTOR = new IdSelector() {
		@Override
		public long getItemId(Object o) {
			return RecyclerView.NO_ID;
		}
	};

	// Item States

	public <T> ItemState<T> getItemState(ViewHolder vh, @ValidRes int stateKey) {
		final int position = vh.getAdapterPosition();
		if (position < 0) {
			throw new IllegalArgumentException("Attempted to get item state of unbound ViewHolder.");
		}
		return getItemStateInternal(position, vh.getItemId(), stateKey);
	}

	public <T> ItemState<T> getItemState(int position, @ValidRes int stateKey) {
		return getItemStateInternal(position, getItemId(position), stateKey);
	}

	public <T> ItemState<T> getItemStateForId(long itemId, @ValidRes int stateKey) {
		return getItemStateForIdInternal(itemId, stateKey);
	}

	public <T> ItemState<T> initItemState(ViewHolder vh, @ValidRes int stateKey) {
		final int position = vh.getAdapterPosition();
		if (position < 0) {
			throw new IllegalArgumentException("Attempted to get item state of unbound ViewHolder.");
		}
		return initItemStateInternal(position, vh.getItemViewType(), vh.getItemId(), stateKey);
	}

	public <T> ItemState<T> initItemState(int position, @ValidRes int stateKey) {
		final Item item = getItem(position);
		return initItemStateInternal(position, 0, getItemId(item), stateKey);
	}

	public <T> ItemState<T> initItemStateForId(long itemId, @ValidRes int stateKey) {
		if (itemId == RecyclerView.NO_ID) {
			throw new IllegalArgumentException("itemId == RecyclerView.NO_ID");
		}
		return initItemStateForIdInternal(itemId, stateKey);
	}

	// Item States Internals

	private <T> ItemState<T> getItemStateInternal(int position, long id, @ValidRes int stateKey) {
		return (ItemState<T>) (id == RecyclerView.NO_ID
				? getItemStateForPositionInternal(position, stateKey)
				: getItemStateForIdInternal(id, stateKey));
	}

	private <T> ItemState<T> getItemStateForPositionInternal(int position, @ValidRes int stateKey) {
		final ArrayList<ItemState<?>> itemStates = getCacheState().itemStates;
		final int index = Collections.binarySearch(itemStates, PositionalItemState.getFinder(position, stateKey));
		return index < 0 ? null : (ItemState<T>) itemStates.get(index);
	}

	private <T> ItemState<T> getItemStateForIdInternal(long id, @ValidRes int stateKey) {
		final ArrayList<ItemState<?>> itemStates = getCacheState().itemStates;
		final int index = Collections.binarySearch(itemStates, IdBasedItemState.getFinder(id, stateKey));
		return index < 0 ? null : (ItemState<T>) itemStates.get(index);
	}

	private <T> ItemState<T> initItemStateInternal(int position, int viewType, long id, @ValidRes int stateKey) {
		return (ItemState<T>) (id == RecyclerView.NO_ID
				? initItemStateForPositionInternal(position, viewType, stateKey)
				: initItemStateForIdInternal(id, stateKey));
	}

	private <T> ItemState<T> initItemStateForPositionInternal(int position, int viewType, @ValidRes int stateKey) {
		final ArrayList<ItemState<?>> itemStates = getCacheState().itemStates;
		int index = Collections.binarySearch(itemStates, PositionalItemState.getFinder(position, stateKey));

		if (index < 0) {
			index = ~index;

			if (viewType == 0)
				viewType = getItemViewType(position);

			ItemState itemState = new PositionalItemState<>(position, viewType, stateKey);
			itemStates.add(index, itemState);
			return itemState;
		}
		return (ItemState<T>) itemStates.get(index);
	}

	private <T> ItemState<T> initItemStateForIdInternal(long id, @ValidRes int stateKey) {
		final ArrayList<ItemState<?>> itemStates = getCacheState().itemStates;
		int index = Collections.binarySearch(itemStates, IdBasedItemState.getFinder(id, stateKey));

		if (index < 0) {
			index = ~index;

			ItemState itemState = new IdBasedItemState<>(id, stateKey);
			itemStates.add(index, itemState);
			return itemState;
		}
		return (ItemState<T>) itemStates.get(index);
	}

	public static abstract class ItemState<T> implements Comparable<ItemState> {
		int stateKey;

		public T data;

		ItemState() { }

		ItemState(int stateKey) {
			this.stateKey = stateKey;
		}

		@Override
		public int compareTo(@NonNull ItemState other) {
			final int lhs = this.stateKey;
			final int rhs = other.stateKey;

			return lhs < rhs ? -1 : lhs == rhs ? 0 : 1;
		}
	}

	static final class IdBasedItemState<T> extends ItemState<T> {
		long id;

		IdBasedItemState() { }

		IdBasedItemState(long id, int stateKey) {
			super(stateKey);
			this.id = id;
		}

		@Override
		public int compareTo(@NonNull ItemState other) {
			if (other instanceof IdBasedItemState) {
				long otherId = ((IdBasedItemState) other).id;
				long id = this.id;

				if (id < otherId)
					return -1;
				if (id == otherId)
					return super.compareTo(other);
				return 1;
			}
			return -1; // We are always lower than other types.
		}

		static <T> IdBasedItemState<T> getFinder(long id, int stateKey) {
			final IdBasedItemState<T> finder = getFinderInternal();
			finder.id = id;
			finder.stateKey = stateKey;
			return finder;
		}

		static <T> IdBasedItemState<T> getFinderInternal() {
			// Optimizes for the main thread.
			return Thread.currentThread() == ThreadUtils.MAIN
					? sMainThreadInstance : OtherInternal.sInstance.get();
		}

		static final IdBasedItemState sMainThreadInstance = new IdBasedItemState();

		private static class OtherInternal {
			static final ThreadLocal<IdBasedItemState> sInstance = new ThreadLocal<IdBasedItemState>() {
				@Override
				protected IdBasedItemState initialValue() {
					return new IdBasedItemState();
				}
			};
		}
	}

	static final class PositionalItemState<T> extends ItemState<T> {
		int position;

		// Use only to properly distinguish between data set changes, as well as to prevent
		// assigning a state to an unexpected/incompatible item of a different view type.
		int viewType;

		PositionalItemState() { }

		PositionalItemState(int position, int viewType, int stateKey) {
			super(stateKey);
			this.position = position;
			this.viewType = viewType;
		}

		@Override
		public int compareTo(@NonNull ItemState other) {
			if (other instanceof PositionalItemState) {
				long otherPos = ((PositionalItemState) other).position;
				long position = this.position;

				if (position < otherPos)
					return -1;
				if (position == otherPos)
					return super.compareTo(other);
				return 1;
			}
			return 1; // We are always higher than other types.
		}

		static <T> PositionalItemState<T> getFinder(int position, int stateKey) {
			final PositionalItemState<T> finder = getFinderInternal();
			finder.position = position;
			finder.stateKey = stateKey;
			return finder;
		}

		static <T> PositionalItemState<T> getFinderInternal() {
			// Optimizes for the main thread.
			return Thread.currentThread() == ThreadUtils.MAIN
					? sMainThreadInstance : OtherInternal.sInstance.get();
		}

		static final PositionalItemState sMainThreadInstance = new PositionalItemState();

		private static class OtherInternal {
			static final ThreadLocal<PositionalItemState> sInstance = new ThreadLocal<PositionalItemState>() {
				@Override
				protected PositionalItemState initialValue() {
					return new PositionalItemState();
				}
			};
		}
	}

	final class PositionalItemStatesInternals extends AdapterDataObserver {

		@Override
		public void onChanged() {
			final ArrayList<ItemState<?>> itemStates = getCacheState().itemStates;

			int index = Collections.binarySearch(itemStates, PositionalItemState.getFinder(0, 0));
			if (index < 0) index = ~index;

			final int size = itemStates.size();
			if (index < size)
				itemStates.subList(index, size).clear();
		}

		@Override
		public void onItemRangeChanged(int positionStart, int itemCount) {
			super.onItemRangeChanged(positionStart, itemCount);
		}

		@Override
		public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
			super.onItemRangeChanged(positionStart, itemCount, payload);
		}

		@Override
		public void onItemRangeInserted(int positionStart, int itemCount) {
			if (itemCount == 0) {
				return;
			}

			final ArrayList<ItemState<?>> itemStates = getCacheState().itemStates;

			for (int i = itemStates.size() - 1; i >= 0; i--) {
				final PositionalItemState<?> ps;
				{
					final ItemState<?> s = itemStates.get(i);
					if (s instanceof PositionalItemState<?>) {
						ps = (PositionalItemState<?>) s;
					} else
						break;
				}
				if (ps.position < positionStart) {
					break;
				}
				ps.position += itemCount;
			}
		}

		@Override
		public void onItemRangeRemoved(int positionStart, int itemCount) {
			if (itemCount == 0) {
				return;
			}

			final ArrayList<ItemState<?>> itemStates = getCacheState().itemStates;

			int removeStart = -1, removeEnd = -1; // Both are inclusive indices

			for (int i = itemStates.size() - 1; i >= 0; i--) {
				final PositionalItemState<?> ps;
				{
					final ItemState<?> s = itemStates.get(i);
					if (s instanceof PositionalItemState<?>) {
						ps = (PositionalItemState<?>) s;
					} else
						break;
				}
				int position = ps.position;
				if (position < positionStart) {
					break;
				}
				position -= itemCount;
				if (position < 0) {
					removeStart = i;
					if (removeEnd < 0)
						removeEnd = i;
				} else {
					ps.position = position;
				}
			}

			if (removeStart >= 0)
				itemStates.subList(removeStart, removeEnd + 1).clear();
		}

		@Override
		public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
			super.onItemRangeMoved(fromPosition, toPosition, itemCount);
		}
	}
}

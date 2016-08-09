package io.jasonsparc.chemistry;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.LongSparseArray;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.IdentityHashMap;

import io.jasonsparc.chemistry.util.ItemBinder2;
import io.jasonsparc.chemistry.util.ViewTypes;

import static io.jasonsparc.chemistry.Chemistry.getItemClass;

/**
 * TODO Docs
 *
 * Created by jason on 07/07/2016.
 */
public abstract class ChemistryAdapter<Item> extends RecyclerView.Adapter<ViewHolder> {
	@NonNull final Chemistry chemistry;
	@Nullable CacheState<Item> cacheState;

	public ChemistryAdapter(@NonNull Chemistry chemistry) {
		this.chemistry = chemistry;
	}

	@Override
	public abstract int getItemCount();

	public abstract Item getItem(int position);

	@Override
	public long getItemId(int position) {
		return hasStableIds()
				? getItemIdInternal(getItem(position))
				: RecyclerView.NO_ID;
	}

	public long getItemId(Item item) {
		return hasStableIds()
				? getItemIdInternal(item)
				: RecyclerView.NO_ID;
	}

	@SuppressWarnings("unchecked")
	private long getItemIdInternal(Item item) {
		final CacheState<Item> cacheState = getCacheState();
		final Class<? extends Item> itemClass = getItemClass(item);
		IdSelector<? super Item> idSelector = cacheState.idSelectors.get(itemClass);

		if (idSelector == null) {
			idSelector = chemistry.findIdSelector(itemClass);

			if (idSelector == null) {
				idSelector = NO_ID_SELECTOR;
			}

			cacheState.idSelectors.put(itemClass, idSelector);
		}

		return idSelector.getItemId(item);
	}

	@Override
	public int getItemViewType(int position) {
		final Item item = getItem(position);
		try {
			return getItemViewTypeInternal(item);
		} catch (NullFlaskException nfe) {
			throw new NullPointerException(item == null
					? "Null `Flask` associated for null item at position " + position
					: "Null `Flask` associated for item at position " + position + " with class: `" + item.getClass().getName() + "`"
			);
		}
	}

	public int getItemViewType(Item item) {
		try {
			return getItemViewTypeInternal(item);
		} catch (NullFlaskException nfe) {
			throw new NullPointerException(item == null
					? "Null `Flask` associated for null item."
					: "Null `Flask` associated for item! Class: " + item.getClass().getName() + "; Value: " + item
			);
		}
	}

	private int getItemViewTypeInternal(Item item) throws NullFlaskException {
		final CacheState<Item> cacheState = getCacheState();
		final Class<? extends Item> itemClass = getItemClass(item);
		FlaskSelector<? super Item> flaskSelector = cacheState.flaskSelectors.get(itemClass);

		if (flaskSelector == null) {
			flaskSelector = chemistry.findFlaskSelector(itemClass);

			if (flaskSelector == null) {
				throw new NullPointerException(item == null
						? "No `FlaskSelector` found for null items."
						: "No `FlaskSelector` found for items with class: `" + itemClass.getName() + "`"
				);
			}

			cacheState.flaskSelectors.put(itemClass, flaskSelector);
		}

		final Flask<?> itemFlask = flaskSelector.getItemFlask(item);
		if (itemFlask == null) {
			throw new NullFlaskException();
		}

		@ViewType
		final int viewType = itemFlask.getViewType();
		ViewTypes.validateForState(viewType);

		final Flask<?> cachedFlask = cacheState.flasks.get(viewType);
		if (cachedFlask == null) {
			cacheState.flasks.put(viewType, itemFlask);
		}

		return viewType;
	}

	private static class NullFlaskException extends NullPointerException {
		NullFlaskException() { }
	}

	@NonNull
	public final Chemistry getChemistry() {
		return chemistry;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		final Flask<?> flask = getCacheState().flasks.get(viewType);
		if (flask == null) {
			throw new NullPointerException("`Flask` for the specified `viewType` either does not exist or has not yet been initialized.");
		}
		return flask.createViewHolder(parent);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		final Item item = getItem(position);

		final CacheState<Item> cacheState = getCacheState();
		final Class<? extends Item> itemClass = getItemClass(item);
		final int viewType = holder.getItemViewType();

		final long itemBinderKey = (((long) viewType) << 32) | itemClass.hashCode();
		ItemBinder<? super Item, ? super ViewHolder> itemBinder = cacheState.itemBinders.get(itemBinderKey);

		if (itemBinder == null) {
			final Flask<?> flask = cacheState.flasks.get(viewType);
			if (flask == null) {
				// TODO Try to find associated flask before throwing an error

				throw new NullPointerException("`Flask` for the specified `viewType` either does not exist or has not yet been initialized.");
			}

			itemBinder = chemistry.findItemBinder(itemClass, holder.getClass(), flask);

			if (itemBinder == null) {
				throw new NullPointerException(item == null
						? "No `ItemBinder` found! Item Class: null; VH Class: " + holder.getClass().getName() + "; Flask: " + flask
						: "No `ItemBinder` found! Item Class: " + itemClass.getName() + "; VH Class: " + holder.getClass().getName() + "; Flask: " + flask);
			}

			cacheState.itemBinders.put(itemBinderKey, itemBinder);
		}

		if (itemBinder instanceof ItemBinder2<?, ?>) {
			if (holder.itemView.getTag(R.id.io_chem_vh_initialized) == null) {
				@SuppressWarnings("unchecked")
				ItemBinder2<? super Item, ? super ViewHolder> itemBinder2 = (ItemBinder2) itemBinder;
				itemBinder2.initViewHolder(holder);
				holder.itemView.setTag(R.id.io_chem_vh_initialized, Boolean.TRUE);
			}
		}

		itemBinder.bindViewHolder(holder, item);
	}

	@NonNull
	private CacheState<Item> getCacheState() {
		if (cacheState == null) {
			cacheState = new CacheState<>();
		}
		return cacheState;
	}

	private static class CacheState<Item> {
		IdentityHashMap<Class<? extends Item>, FlaskSelector<? super Item>> flaskSelectors = new IdentityHashMap<>();
		IdentityHashMap<Class<? extends Item>, IdSelector<? super Item>> idSelectors = new IdentityHashMap<>();
		SparseArray<Flask<?>> flasks = new SparseArray<>();
		LongSparseArray<ItemBinder<? super Item, ? super ViewHolder>> itemBinders = new LongSparseArray<>();

		CacheState() { }
	}

	private static final IdSelector NO_ID_SELECTOR = new IdSelector() {
		@Override
		public long getItemId(Object o) {
			return RecyclerView.NO_ID;
		}
	};
}

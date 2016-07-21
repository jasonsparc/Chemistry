package io.jasonsparc.chemistry;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.ViewGroup;

import static io.jasonsparc.chemistry.Chemistry.getItemClass;

/**
 * TODO Docs
 *
 * Created by jason on 07/07/2016.
 */
@SuppressWarnings("unchecked")
public abstract class ChemistryAdapter<Item> extends RecyclerView.Adapter<ViewHolder> {
	@NonNull final Chemistry chemistry;
	@Nullable InternalState internalState;

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

		final Item item = getItem(position);

		final InternalState internalState = getInternalState();
		final Class<?> itemClass = getItemClass(item);
		IdSelector idSelector = internalState.idSelectors.get(itemClass);

		if (idSelector == null) {
			idSelector = chemistry.findIdSelector(itemClass, 0);

			if (idSelector == null)
				idSelector = NO_ID_SELECTOR;

			internalState.idSelectors.put(itemClass, idSelector);
		}

		return idSelector.getItemId(item);
	}

	@Override
	public int getItemViewType(int position) {
		final Item item = getItem(position);

		final InternalState internalState = getInternalState();
		final Class<?> itemClass = getItemClass(item);
		FlaskSelector flaskSelector = internalState.flaskSelectors.get(itemClass);

		if (flaskSelector == null) {
			flaskSelector = chemistry.findFlaskSelector(itemClass, 0);

			if (flaskSelector == null) {
				throw new NullPointerException(item == null
						? "No `FlaskSelector` found for null items."
						: "No `FlaskSelector` found for items with class: `" + itemClass.getName() + "`"
				);
			}

			internalState.flaskSelectors.put(itemClass, flaskSelector);
		}

		final Flask itemFlask = flaskSelector.getItemFlask(item);
		if (itemFlask == null) {
			throw new NullPointerException(item == null
					? "Null `Flask` associated for null item at position " + position
					: "Null `Flask` associated for item at position " + position + " with class: `" + itemClass.getName() + "`"
			);
		}

		final int viewType = itemFlask.getViewType();
		final Flask cachedFlask = internalState.flasks.get(viewType);
		if (cachedFlask == null) {
			internalState.flasks.put(viewType, itemFlask);
		}

		return viewType;
	}

	@NonNull
	public final Chemistry getChemistry() {
		return chemistry;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		final Flask flask = getInternalState().flasks.get(viewType);
		if (flask == null) {
			throw new NullPointerException("`Flask` for the specified `viewType` either does not exist or has not yet been initialized.");
		}
		return flask.createViewHolder(parent);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		final Item item = getItem(position);

		final InternalState internalState = getInternalState();
		final Class<?> itemClass = getItemClass(item);
		final int viewType = holder.getItemViewType();

		final long itemBinderKey = (((long) viewType) << 32) | itemClass.hashCode();
		ItemBinder itemBinder = internalState.itemBinders.get(itemBinderKey);

		if (itemBinder == null) {
			final Flask flask = internalState.flasks.get(viewType);
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

			internalState.itemBinders.put(itemBinderKey, itemBinder);
		}

		itemBinder.bindViewHolder(holder, item);
	}

	@NonNull
	protected InternalState onCreateInternalState() {
		return new InternalState();
	}

	@NonNull
	protected InternalState getInternalState() {
		if (internalState == null) {
			internalState = onCreateInternalState();
		}
		return internalState;
	}

	protected static class InternalState {
		ArrayMap<Class<?>, FlaskSelector> flaskSelectors = new ArrayMap<>();
		ArrayMap<Class<?>, IdSelector> idSelectors = new ArrayMap<>();
		SparseArray<Flask> flasks = new SparseArray<>();
		LongSparseArray<ItemBinder> itemBinders = new LongSparseArray<>();
	}

	private static final IdSelector NO_ID_SELECTOR = new IdSelector() {
		@Override
		public long getItemId(Object o) {
			return RecyclerView.NO_ID;
		}
	};
}

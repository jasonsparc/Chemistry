package io.jasonsparc.chemistry;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.ViewGroup;

import io.jasonsparc.chemistry.util.ViewTypes;

/**
 * TODO Docs
 *
 * Created by jason on 07/07/2016.
 */
public abstract class ChemistryAdapter<Item> extends RecyclerView.Adapter<ViewHolder> {
	@NonNull final Chemistry<? super Item> chemistry;

	public ChemistryAdapter(@NonNull Chemistry<? super Item> chemistry) {
		this.chemistry = chemistry;
	}

	@NonNull
	public final Chemistry<? super Item> getChemistry() {
		return chemistry;
	}

	@Override
	public abstract int getItemCount();

	public abstract Item getItem(int position);

	@Override
	public long getItemId(int position) {
		return hasStableIds()
				? chemistry.getItemId(getItem(position))
				: RecyclerView.NO_ID;
	}

	@Override
	public int getItemViewType(int position) {
		final Item item = getItem(position);
		final int viewType = chemistry.getItemViewType(item);

		SparseArray<VhFactory<?>> vhFactories = getVhFactories();
		if (vhFactories.get(viewType) == null) {
			ViewTypes.validateForState(viewType);

			VhFactory<?> vhFactory = chemistry.getVhFactory(item);
			if (vhFactory == null) {
				throw new NullPointerException("vhFactory == null");
			}

			vhFactories.put(viewType, vhFactory);
		}

		return viewType;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		VhFactory<?> vhFactory = getVhFactories().get(viewType);
		if (vhFactory == null) {
			throw new NullPointerException("`VhFactory` for the specified `viewType` either does not exist or has not yet been initialized.");
		}
		return vhFactory.createViewHolder(parent);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		final Item item = getItem(position);

		@SuppressWarnings("unchecked")
		ItemBinder<Item, ViewHolder> itemBinder = (ItemBinder) chemistry.getItemBinder(item);
		itemBinder.bindViewHolder(holder, item);
	}

	// Internals

	@Nullable
	private SparseArray<VhFactory<?>> vhFactories;

	@NonNull
	private SparseArray<VhFactory<?>> getVhFactories() {
		if (vhFactories == null) {
			vhFactories = new SparseArray<>();
		}
		return vhFactories;
	}
}

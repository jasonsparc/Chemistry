package io.jasonsparc.chemistry;

import android.support.annotation.AnyRes;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

/**
 * Created by Jason on 19/08/2016.
 */
public abstract class BasicChemistry<Item, VH extends ViewHolder> extends Chemistry<Item, VH> implements VhFactory<VH>, ItemBinder<Item, VH> {

	@Override
	public final int getItemViewType(Item item) {
		return getViewType();
	}

	@Override
	public final VhFactory<? extends VH> getVhFactory(Item item) {
		return this;
	}

	@Override
	public final ItemBinder<? super Item, ? super VH> getItemBinder(Item item) {
		return this;
	}

	@ViewType
	@AnyRes
	public abstract int getViewType();

	@Override
	public abstract VH createViewHolder(ViewGroup parent);

	@Override
	public abstract void bindViewHolder(VH holder, Item item);
}

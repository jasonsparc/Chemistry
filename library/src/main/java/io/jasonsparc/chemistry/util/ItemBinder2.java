package io.jasonsparc.chemistry.util;

import android.support.v7.widget.RecyclerView.ViewHolder;

import io.jasonsparc.chemistry.ItemBinder;

/**
 * Created by jason on 05/08/2016.
 */
public interface ItemBinder2<Item, VH extends ViewHolder> extends VhInitializer<VH>, ItemBinder<Item, VH> {

	@Override
	void initViewHolder(VH holder);

	@Override
	void bindViewHolder(VH holder, Item item);
}

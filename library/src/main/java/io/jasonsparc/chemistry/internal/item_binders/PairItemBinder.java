package io.jasonsparc.chemistry.internal.item_binders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import io.jasonsparc.chemistry.ItemBinder;

/**
 * Created by jason on 05/08/2016.
 */
public class PairItemBinder<Item, VH extends RecyclerView.ViewHolder> implements ItemBinder<Item, VH> {
	@NonNull final ItemBinder<? super Item, ? super VH> first, second;

	public PairItemBinder(@NonNull ItemBinder<? super Item, ? super VH> first, @NonNull ItemBinder<? super Item, ? super VH> second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public void bindViewHolder(VH holder, Item item) {
		first.bindViewHolder(holder, item);
		second.bindViewHolder(holder, item);
	}
}

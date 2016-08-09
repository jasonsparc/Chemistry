package io.jasonsparc.chemistry.internal.item_binders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import io.jasonsparc.chemistry.ItemBinder;

/**
 * Created by jason on 05/08/2016.
 */
public final class ArrayItemBinder<Item, VH extends RecyclerView.ViewHolder> implements ItemBinder<Item, VH> {
	@NonNull final ItemBinder<? super Item, ? super VH>[] itemBinders;

	public ArrayItemBinder(@NonNull final ItemBinder<? super Item, ? super VH>[] itemBinders) {
		this.itemBinders = itemBinders;
	}

	@Override
	public void bindViewHolder(VH holder, Item item) {
		for (ItemBinder<? super Item, ? super VH> itemBinder : itemBinders) {
			itemBinder.bindViewHolder(holder, item);
		}
	}
}

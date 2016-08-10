package io.jasonsparc.chemistry;

import android.support.v7.widget.RecyclerView;

/**
 * Created by jason on 08/07/2016.
 */
public interface ItemBinder2Override<Item, VH extends RecyclerView.ViewHolder> extends ItemBinderOverride<Item, VH> {

	void bindViewHolder(ItemBinder<? super Item, ? super VH> base, VH holder, Item item);
}

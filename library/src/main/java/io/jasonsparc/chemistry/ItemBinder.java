package io.jasonsparc.chemistry;

import android.support.v7.widget.RecyclerView;

/**
 * Created by jason on 08/07/2016.
 */
public interface ItemBinder<Item, VH extends RecyclerView.ViewHolder> {

	void bindViewHolder(VH holder, Item item);
}

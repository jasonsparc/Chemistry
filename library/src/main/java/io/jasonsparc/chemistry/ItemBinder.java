package io.jasonsparc.chemistry;

import android.support.v7.widget.RecyclerView;

/**
 * Created by jason on 28/06/2016.
 */
public interface ItemBinder<VH extends RecyclerView.ViewHolder, T> {

	void bindItem(VH holder, T item);
}

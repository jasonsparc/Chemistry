package io.jasonsparc.chemistry;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jason on 28/06/2016.
 */
public abstract class ItemAdapter<VH extends RecyclerView.ViewHolder, T> implements ViewFactory, ViewHolderFactory<VH>, ItemBinder<VH, T> {

	@Override
	public abstract View createView(ViewGroup parent);

	@Override
	public abstract VH createViewHolder(View itemView);

	@Override
	public abstract void bindItem(VH holder, T item);
}

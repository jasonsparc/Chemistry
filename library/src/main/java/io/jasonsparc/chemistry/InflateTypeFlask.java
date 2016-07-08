package io.jasonsparc.chemistry;

import android.support.annotation.AnyRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by jason on 07/07/2016.
 */
final class InflateTypeFlask<VH extends RecyclerView.ViewHolder> extends TypeFlask<VH> {
	@LayoutRes final int itemLayout;
	@NonNull final VhFactory<? extends VH> vhFactory;

	public InflateTypeFlask(@ViewTypeId @AnyRes final int viewType, @LayoutRes final int itemLayout, @NonNull VhFactory<? extends VH> vhFactory) {
		super(viewType);
		this.itemLayout = itemLayout;
		this.vhFactory = vhFactory;
	}

	@NonNull
	@Override
	public VH createViewHolder(ViewGroup parent) {
		return vhFactory.createViewHolder(InflateUtils.inflate(itemLayout, parent));
	}
}

package io.jasonsparc.chemistry;

import android.support.annotation.AnyRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by jason on 07/07/2016.
 */
final class CompositeTypeFlask<VH extends RecyclerView.ViewHolder> extends TypeFlask<VH> {
	@NonNull final ViewFactory viewFactory;
	@NonNull final VhFactory<? extends VH> vhFactory;

	public CompositeTypeFlask(@IntRange(from = MIN_RES_ID) @AnyRes final int viewType, @NonNull ViewFactory viewFactory, @NonNull VhFactory<? extends VH> vhFactory) {
		super(viewType);
		this.viewFactory = viewFactory;
		this.vhFactory = vhFactory;
	}

	@NonNull
	@Override
	public VH createViewHolder(ViewGroup parent) {
		return vhFactory.createViewHolder(viewFactory.createView(parent));
	}
}

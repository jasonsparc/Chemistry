package io.jasonsparc.chemistry.internal.flasks;

import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

import io.jasonsparc.chemistry.VhFactory;
import io.jasonsparc.chemistry.ViewFactory;
import io.jasonsparc.chemistry.ViewType;

/**
 * Created by jason on 07/07/2016.
 */
public class CompositeTypedFlask<VH extends ViewHolder> extends TypedFlask<VH> {
	@NonNull final ViewFactory viewFactory;
	@NonNull final VhFactory<? extends VH> vhFactory;

	public CompositeTypedFlask(@ViewType @AnyRes final int viewType, @NonNull ViewFactory viewFactory, @NonNull VhFactory<? extends VH> vhFactory) {
		super(viewType);
		this.viewFactory = viewFactory;
		this.vhFactory = vhFactory;
	}

	@Override
	public VH createViewHolder(ViewGroup parent) {
		return vhFactory.createViewHolder(viewFactory.createView(parent));
	}
}

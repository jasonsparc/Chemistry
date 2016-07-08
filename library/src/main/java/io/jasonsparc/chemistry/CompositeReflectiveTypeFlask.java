package io.jasonsparc.chemistry;

import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jason on 07/07/2016.
 */
final class CompositeReflectiveTypeFlask<VH extends RecyclerView.ViewHolder> extends ReflectiveTypeFlask<VH> {
	@NonNull final ViewFactory viewFactory;

	public CompositeReflectiveTypeFlask(@ViewTypeId @AnyRes final int viewType, @NonNull ViewFactory viewFactory, @NonNull Class<VH> vhCls) {
		super(viewType, vhCls);
		this.viewFactory = viewFactory;
	}

	@Override
	protected View createView(ViewGroup parent) {
		return viewFactory.createView(parent);
	}
}

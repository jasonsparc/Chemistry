package io.jasonsparc.chemistry.internal.flasks;

import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;

import io.jasonsparc.chemistry.ViewFactory;
import io.jasonsparc.chemistry.ViewType;

/**
 * Created by jason on 07/07/2016.
 */
public class CompositeReflectiveTypedFlask<VH extends ViewHolder> extends ReflectiveTypedFlask<VH> {
	@NonNull final ViewFactory viewFactory;

	public CompositeReflectiveTypedFlask(@ViewType @AnyRes final int viewType, @NonNull ViewFactory viewFactory, @NonNull Class<? extends VH> vhCls) {
		super(viewType, vhCls);
		this.viewFactory = viewFactory;
	}

	@Override
	protected View createView(ViewGroup parent) {
		return viewFactory.createView(parent);
	}
}

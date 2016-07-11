package io.jasonsparc.chemistry.internal.flasks;

import android.support.annotation.AnyRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;

import io.jasonsparc.chemistry.ViewType;
import io.jasonsparc.chemistry.internal.util.InflateUtils;

/**
 * Created by jason on 07/07/2016.
 */
public class InflateReflectiveTypedFlask<VH extends ViewHolder> extends ReflectiveTypedFlask<VH> {
	@LayoutRes final int itemLayout;

	public InflateReflectiveTypedFlask(@ViewType @AnyRes final int viewType, @LayoutRes final int itemLayout, @NonNull Class<? extends VH> vhCls) {
		super(viewType, vhCls);
		this.itemLayout = itemLayout;
	}

	@Override
	protected View createView(ViewGroup parent) {
		return InflateUtils.inflate(itemLayout, parent);
	}
}

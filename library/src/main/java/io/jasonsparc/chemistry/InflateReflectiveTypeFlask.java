package io.jasonsparc.chemistry;

import android.support.annotation.AnyRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jason on 07/07/2016.
 */
final class InflateReflectiveTypeFlask<VH extends RecyclerView.ViewHolder> extends ReflectiveTypeFlask<VH> {
	@LayoutRes final int itemLayout;

	public InflateReflectiveTypeFlask(@ViewTypeId @AnyRes final int viewType, @LayoutRes final int itemLayout, @NonNull Class<VH> vhCls) {
		super(viewType, vhCls);
		this.itemLayout = itemLayout;
	}

	@Override
	protected View createView(ViewGroup parent) {
		return InflateUtils.inflate(itemLayout, parent);
	}
}

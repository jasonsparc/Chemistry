package io.jasonsparc.chemistry.internal.flasks;

import android.support.annotation.AnyRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

import io.jasonsparc.chemistry.ViewType;
import io.jasonsparc.chemistry.internal.util.InflateUtils;
import io.jasonsparc.chemistry.util.VhFactory;

/**
 * Created by jason on 07/07/2016.
 */
public class InflateTypedFlask<VH extends ViewHolder> extends TypedFlask<VH> {
	@LayoutRes final int itemLayout;
	@NonNull final VhFactory<? extends VH> vhFactory;

	public InflateTypedFlask(@ViewType @AnyRes final int viewType, @LayoutRes final int itemLayout, @NonNull VhFactory<? extends VH> vhFactory) {
		super(viewType);
		this.itemLayout = itemLayout;
		this.vhFactory = vhFactory;
	}

	@Override
	public VH createViewHolder(ViewGroup parent) {
		return vhFactory.createViewHolder(InflateUtils.inflate(itemLayout, parent));
	}
}

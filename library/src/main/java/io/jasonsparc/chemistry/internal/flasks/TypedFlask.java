package io.jasonsparc.chemistry.internal.flasks;

import android.support.annotation.AnyRes;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

import io.jasonsparc.chemistry.Flask;
import io.jasonsparc.chemistry.ViewType;
import io.jasonsparc.chemistry.util.ViewTypes;

/**
 * Created by jason on 11/07/2016.
 */
public abstract class TypedFlask<VH extends ViewHolder> implements Flask<VH> {
	@ViewType @AnyRes
	final int viewType;

	public TypedFlask(@ViewType @AnyRes int viewType) {
		ViewTypes.validateArgument(viewType);
		this.viewType = viewType;
	}

	@Override
	public int getViewType() {
		return viewType;
	}

	@Override
	public abstract VH createViewHolder(ViewGroup parent);
}

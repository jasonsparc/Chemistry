package io.jasonsparc.chemistry.internal.predicates;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;

import io.jasonsparc.chemistry.BindPredicate;
import io.jasonsparc.chemistry.Flask;

/**
 * Created by jason on 11/07/2016.
 */
public class VhClassBindPredicate<VH extends ViewHolder> implements BindPredicate<VH> {
	@NonNull final Class<? extends VH> vhCls;

	public VhClassBindPredicate(@NonNull Class<? extends VH> vhCls) {
		this.vhCls = vhCls;
	}

	@Override
	public boolean checkBind(@NonNull Flask<?> flask, @NonNull Class<? extends ViewHolder> vhCls) {
		return this.vhCls.isAssignableFrom(vhCls);
	}
}

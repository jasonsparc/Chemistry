package io.jasonsparc.chemistry.internal.bind_predicates;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;

import io.jasonsparc.chemistry.BindPredicate;
import io.jasonsparc.chemistry.Flask;

/**
 * Created by jason on 02/08/2016.
 */
public final class FusedBindPredicate<VH extends ViewHolder> implements BindPredicate<VH> {
	@NonNull final BindPredicate<? extends VH> first;
	@NonNull final BindPredicate<? extends VH> second;

	public FusedBindPredicate(@NonNull BindPredicate<? extends VH> first, @NonNull BindPredicate<? extends VH> second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public boolean checkBind(@NonNull Class<? extends ViewHolder> vhClass, @NonNull Flask<?> flask) {
		return first.checkBind(vhClass, flask) && second.checkBind(vhClass, flask);
	}
}

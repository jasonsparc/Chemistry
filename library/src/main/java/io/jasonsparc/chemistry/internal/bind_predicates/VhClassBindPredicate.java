package io.jasonsparc.chemistry.internal.bind_predicates;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;

import io.jasonsparc.chemistry.BindPredicate;
import io.jasonsparc.chemistry.Flask;

/**
 * Created by jason on 11/07/2016.
 */
public class VhClassBindPredicate<VH extends ViewHolder> implements BindPredicate<VH> {
	@NonNull final Class<? extends VH> vhClass;

	public VhClassBindPredicate(@NonNull Class<? extends VH> vhClass) {
		this.vhClass = vhClass;
	}

	@Override
	public boolean checkBind(@NonNull Flask<?> flask, @NonNull Class<? extends ViewHolder> vhClass) {
		return this.vhClass.isAssignableFrom(vhClass);
	}
}

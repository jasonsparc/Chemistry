package io.jasonsparc.chemistry.internal.bindpredicates;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;

import io.jasonsparc.chemistry.BindPredicate;
import io.jasonsparc.chemistry.Flask;

/**
 * Created by jason on 11/07/2016.
 */
public class VhClassArrayBindPredicate<VH extends ViewHolder> implements BindPredicate<VH> {
	@NonNull final Class<? extends VH>[] vhClasses;

	public VhClassArrayBindPredicate(@NonNull Class<? extends VH>[] vhClasses) {
		this.vhClasses = vhClasses;
	}

	@Override
	public boolean checkBind(@NonNull Flask<?> flask, @NonNull Class<? extends ViewHolder> vhClass) {
		for (Class<?> checkClass : vhClasses) {
			if (checkClass.isAssignableFrom(vhClass)) {
				return true;
			}
		}
		return false;
	}
}

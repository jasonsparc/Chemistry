package io.jasonsparc.chemistry.internal.bind_predicates;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;

import io.jasonsparc.chemistry.BindPredicate;
import io.jasonsparc.chemistry.Flask;

/**
 * Created by jason on 11/07/2016.
 */
public class FlaskArrayBindPredicate<VH extends ViewHolder> implements BindPredicate<VH> {
	@NonNull final Flask<? extends VH>[] flasks;

	public FlaskArrayBindPredicate(@NonNull Flask<? extends VH>[] flasks) {
		this.flasks = flasks;
	}

	@Override
	public boolean checkBind(@NonNull Class<? extends ViewHolder> vhClass, @NonNull Flask<?> flask) {
		for (Flask<?> checkFlask : flasks) {
			if (flask.equals(checkFlask)) {
				return true;
			}
		}
		return false;
	}
}

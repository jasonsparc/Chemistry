package io.jasonsparc.chemistry.internal.bindpredicates;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;

import java.util.Collection;

import io.jasonsparc.chemistry.BindPredicate;
import io.jasonsparc.chemistry.Flask;

/**
 * Created by jason on 11/07/2016.
 */
public class FlaskCollectionBindPredicate<VH extends ViewHolder> implements BindPredicate<VH> {
	@NonNull final Collection<? extends Flask<? extends VH>> flasks;

	public FlaskCollectionBindPredicate(@NonNull Collection<? extends Flask<? extends VH>> flasks) {
		this.flasks = flasks;
	}

	@Override
	public boolean checkBind(@NonNull Flask<?> flask, @NonNull Class<? extends ViewHolder> vhClass) {
		for (Flask<?> checkFlask : flasks) {
			if (flask.equals(checkFlask)) {
				return true;
			}
		}
		return false;
	}
}

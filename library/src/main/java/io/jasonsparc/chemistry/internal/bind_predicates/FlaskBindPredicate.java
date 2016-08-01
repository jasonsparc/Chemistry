package io.jasonsparc.chemistry.internal.bind_predicates;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;

import io.jasonsparc.chemistry.BindPredicate;
import io.jasonsparc.chemistry.Flask;

/**
 * Created by jason on 11/07/2016.
 */
public class FlaskBindPredicate<VH extends ViewHolder> implements BindPredicate<VH> {
	final Flask<? extends VH> flask;

	public FlaskBindPredicate(Flask<? extends VH> flask) {
		this.flask = flask;
	}

	@Override
	public boolean checkBind(@NonNull Class<? extends ViewHolder> vhClass, @NonNull Flask<?> flask) {
		return flask.equals(this.flask);
	}
}

package io.jasonsparc.chemistry;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;

import java.util.Collection;

/**
 * Created by jason on 12/07/2016.
 */
public class BindPredicates {

	public static <VH extends ViewHolder> BindPredicate<VH> matches(Flask<? extends VH> flask) {
		return Flasks.matches(flask);
	}

	@SafeVarargs
	public static <VH extends ViewHolder> BindPredicate<VH> matches(@NonNull Flask<? extends VH>... flasks) {
		return Flasks.matches(flasks);
	}

	public static <VH extends ViewHolder> BindPredicate<VH> matches(@NonNull Collection<? extends Flask<? extends VH>> flasks) {
		return Flasks.matches(flasks);
	}
}

package io.jasonsparc.chemistry.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;

import java.util.Collection;

import io.jasonsparc.chemistry.BindPredicate;
import io.jasonsparc.chemistry.Flask;
import io.jasonsparc.chemistry.internal.bindpredicates.VhClassArrayBindPredicate;
import io.jasonsparc.chemistry.internal.bindpredicates.VhClassBindPredicate;
import io.jasonsparc.chemistry.internal.bindpredicates.VhClassCollectionBindPredicate;

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

	public static <VH extends ViewHolder> BindPredicate<VH> of(@NonNull Class<? extends VH> vhClass) {
		return new VhClassBindPredicate<>(vhClass);
	}

	@SafeVarargs
	public static <VH extends ViewHolder> BindPredicate<VH> of(@NonNull Class<? extends VH>... vhClasses) {
		return new VhClassArrayBindPredicate<>(vhClasses);
	}

	public static <VH extends ViewHolder> BindPredicate<VH> of(@NonNull Collection<Class<? extends VH>> vhClasses) {
		return new VhClassCollectionBindPredicate<>(vhClasses);
	}
}

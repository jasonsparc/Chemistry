package io.jasonsparc.chemistry.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;

import java.util.Collection;

import io.jasonsparc.chemistry.BindPredicate;
import io.jasonsparc.chemistry.Flask;
import io.jasonsparc.chemistry.internal.bind_predicates.EitherBindPredicate;
import io.jasonsparc.chemistry.internal.bind_predicates.FusedBindPredicate;
import io.jasonsparc.chemistry.internal.bind_predicates.VhClassArrayBindPredicate;
import io.jasonsparc.chemistry.internal.bind_predicates.VhClassBindPredicate;

/**
 * Created by jason on 12/07/2016.
 */
@UtilityClass
public final class BindPredicates {

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

	@SuppressWarnings("unchecked")
	public static <VH extends ViewHolder> BindPredicate<VH> of(@NonNull Collection<Class<? extends VH>> vhClasses) {
		return new VhClassArrayBindPredicate<>(vhClasses.toArray(new Class[vhClasses.size()]));
	}

	// Logical Operations

	public static <VH extends ViewHolder> BindPredicate<VH> fuse(@NonNull BindPredicate<? extends VH> first, @NonNull BindPredicate<? extends VH> second) {
		return new FusedBindPredicate<>(first, second);
	}

	@SuppressWarnings("unchecked")
	@SafeVarargs
	public static <VH extends ViewHolder> BindPredicate<VH> fuse(@NonNull BindPredicate<? extends VH>... bindPredicates) {
		int l = bindPredicates.length;
		switch (l) {
			case 0:
				throw new IllegalArgumentException("length == 0");
			case 1:
				return (BindPredicate<VH>) bindPredicates[0];
			case 2:
				return fuse(bindPredicates[0], bindPredicates[1]);
		}
		BindPredicate<VH> r = (BindPredicate<VH>) bindPredicates[--l];
		do r = fuse(bindPredicates[--l], r); while (l > 0);
		return r;
	}

	public static <VH extends ViewHolder> BindPredicate<VH> fuse(Collection<? extends BindPredicate<? extends VH>> bindPredicates) {
		return fuse(array(bindPredicates));
	}

	public static <VH extends ViewHolder> BindPredicate<VH> either(@NonNull BindPredicate<? extends VH> first, @NonNull BindPredicate<? extends VH> second) {
		return new EitherBindPredicate<>(first, second);
	}

	@SuppressWarnings("unchecked")
	@SafeVarargs
	public static <VH extends ViewHolder> BindPredicate<VH> either(@NonNull BindPredicate<? extends VH>... bindPredicates) {
		int l = bindPredicates.length;
		switch (l) {
			case 0:
				throw new IllegalArgumentException("length == 0");
			case 1:
				return (BindPredicate<VH>) bindPredicates[0];
			case 2:
				return either(bindPredicates[0], bindPredicates[1]);
		}
		BindPredicate<VH> r = (BindPredicate<VH>) bindPredicates[--l];
		do r = either(bindPredicates[--l], r); while (l > 0);
		return r;
	}

	public static <VH extends ViewHolder> BindPredicate<VH> either(Collection<? extends BindPredicate<? extends VH>> bindPredicates) {
		return either(array(bindPredicates));
	}

	// Utilities

	@SafeVarargs
	public static <P extends BindPredicate<? extends ViewHolder>> P[] array(@NonNull P... bindPredicates) {
		return bindPredicates;
	}

	@SuppressWarnings("unchecked")
	public static <VH extends ViewHolder> BindPredicate<? extends VH>[] array(@NonNull Collection<? extends BindPredicate<? extends VH>> bindPredicates) {
		return bindPredicates.toArray(new BindPredicate[bindPredicates.size()]);
	}
}

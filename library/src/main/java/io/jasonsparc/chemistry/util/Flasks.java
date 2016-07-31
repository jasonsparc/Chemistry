package io.jasonsparc.chemistry.util;

import android.support.annotation.AnyRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;

import java.util.Collection;

import io.jasonsparc.chemistry.BindPredicate;
import io.jasonsparc.chemistry.Flask;
import io.jasonsparc.chemistry.FlaskSelector;
import io.jasonsparc.chemistry.ViewType;
import io.jasonsparc.chemistry.internal.bindpredicates.FlaskArrayBindPredicate;
import io.jasonsparc.chemistry.internal.bindpredicates.FlaskBindPredicate;
import io.jasonsparc.chemistry.internal.bindpredicates.FlaskCollectionBindPredicate;
import io.jasonsparc.chemistry.internal.flasks.CompositeReflectiveTypedFlask;
import io.jasonsparc.chemistry.internal.flasks.CompositeTypedFlask;
import io.jasonsparc.chemistry.internal.flasks.InflateReflectiveTypedFlask;
import io.jasonsparc.chemistry.internal.flasks.InflateTypedFlask;
import io.jasonsparc.chemistry.internal.flaskselectors.CompositeFlaskSelector;
import io.jasonsparc.chemistry.internal.flaskselectors.MapFlaskSwitch;
import io.jasonsparc.chemistry.internal.flaskselectors.SingletonFlaskSelector;

/**
 * TODO Docs
 *
 * Created by jason on 11/07/2016.
 */
public class Flasks {

	// Composite `Flask` factories

	public static <VH extends ViewHolder> Flask<VH> make(@ViewType @AnyRes final int viewType, @NonNull ViewFactory viewFactory, @NonNull VhFactory<? extends VH> vhFactory) {
		return new CompositeTypedFlask<>(viewType, viewFactory, vhFactory);
	}

	public static <VH extends ViewHolder> Flask<VH> make(@ViewType @AnyRes int viewType, @NonNull ViewFactory viewFactory, @NonNull Class<? extends VH> vhCls) {
		return new CompositeReflectiveTypedFlask<>(viewType, viewFactory, vhCls);
	}

	// Auto-inflate `Flask` factories

	public static <VH extends ViewHolder> Flask<VH> make(@ViewType @AnyRes int viewType, @LayoutRes int itemLayout, @NonNull VhFactory<? extends VH> vhFactory) {
		return new InflateTypedFlask<>(viewType, itemLayout, vhFactory);
	}

	public static <VH extends ViewHolder> Flask<VH> make(@LayoutRes int itemLayout, @NonNull VhFactory<? extends VH> vhFactory) {
		return new InflateTypedFlask<>(itemLayout, itemLayout, vhFactory);
	}

	public static <VH extends ViewHolder> Flask<VH> make(@ViewType @AnyRes int viewType, @LayoutRes int itemLayout, @NonNull Class<? extends VH> vhCls) {
		return new InflateReflectiveTypedFlask<>(viewType, itemLayout, vhCls);
	}

	public static <VH extends ViewHolder> Flask<VH> make(@LayoutRes int itemLayout, @NonNull Class<? extends VH> vhCls) {
		return new InflateReflectiveTypedFlask<>(itemLayout, itemLayout, vhCls);
	}

	// Utilities

	@SafeVarargs
	public static <FL extends Flask<? extends ViewHolder>> FL[] array(@NonNull FL... flasks) {
		return flasks;
	}

	@SuppressWarnings("unchecked")
	public static <VH extends ViewHolder> Flask<? extends VH>[] array(@NonNull Collection<? extends Flask<? extends VH>> flasks) {
		return flasks.toArray(new Flask[flasks.size()]);
	}

	@SafeVarargs
	public static <FL extends Flask<? extends ViewHolder>> SparseArray<FL> sparseArray(@NonNull FL... flasks) {
		SparseArray<FL> ret = new SparseArray<>(flasks.length);
		for (FL flask : flasks)
			ret.put(flask.getViewType(), flask);
		return ret;
	}

	public static <FL extends Flask<? extends ViewHolder>> SparseArray<FL> sparseArray(@NonNull Collection<? extends FL> flasks) {
		SparseArray<FL> ret = new SparseArray<>(flasks.size());
		for (FL flask : flasks)
			ret.put(flask.getViewType(), flask);
		return ret;
	}

	// Bind Predicates

	public static <VH extends ViewHolder> BindPredicate<VH> matches(Flask<? extends VH> flask) {
		return new FlaskBindPredicate<>(flask);
	}

	@SafeVarargs
	public static <VH extends ViewHolder> BindPredicate<VH> matches(@NonNull Flask<? extends VH>... flasks) {
		return new FlaskArrayBindPredicate<>(flasks);
	}

	public static <VH extends ViewHolder> BindPredicate<VH> matches(@NonNull Collection<? extends Flask<? extends VH>> flasks) {
		return new FlaskCollectionBindPredicate<>(flasks);
	}

	// Flask Selectors

	public static <Item> FlaskSelector<Item> select(Flask<?> flask) {
		return new SingletonFlaskSelector<>(flask);
	}

	public static <Item> FlaskSelector<Item> select(@NonNull TypeSelector<? super Item> typeSelector, @NonNull Flask<?>[] flaskSelections, @Nullable Flask<?> defaultCase) {
		return new CompositeFlaskSelector<>(typeSelector, flaskSelections, defaultCase);
	}

	public static <Item> FlaskSelector<Item> select(@NonNull TypeSelector<? super Item> typeSelector, @NonNull Collection<? extends Flask<?>> flaskSelections, @Nullable Flask<?> defaultCase) {
		return new CompositeFlaskSelector<>(typeSelector, flaskSelections, defaultCase);
	}

	public static <Item> FlaskSelector<Item> select(@NonNull TypeSelector<? super Item> typeSelector, @NonNull Flask<?>[] flaskSelections) {
		return new CompositeFlaskSelector<>(typeSelector, flaskSelections, null);
	}

	public static <Item> FlaskSelector<Item> select(@NonNull TypeSelector<? super Item> typeSelector, @NonNull Collection<? extends Flask<?>> flaskSelections) {
		return new CompositeFlaskSelector<>(typeSelector, flaskSelections, null);
	}

	public static <Item, K> FlaskSwitch<Item, K> mapSelect(@NonNull CaseSelector<? super Item, ? extends K> caseSelector, @NonNull SimpleArrayMap<? super K, ? extends Flask<?>> mapOfCases, @Nullable Flask<?> defaultCase) {
		return MapFlaskSwitch.make(caseSelector, mapOfCases, defaultCase);
	}

	public static <Item, K> FlaskSwitch<Item, K> mapSelect(@NonNull CaseSelector<? super Item, ? extends K> caseSelector, @NonNull SimpleArrayMap<? super K, ? extends Flask<?>> mapOfCases) {
		return MapFlaskSwitch.make(caseSelector, mapOfCases, null);
	}

	public static <Item, K> FlaskSwitch.Boiler<Item, K> mapSelect(@NonNull CaseSelector<? super Item, ? extends K> caseSelector) {
		return MapFlaskSwitch.boiler(caseSelector);
	}
}

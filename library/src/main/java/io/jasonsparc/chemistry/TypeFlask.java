package io.jasonsparc.chemistry;

import android.support.annotation.AnyRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by jason on 07/07/2016.
 */
public abstract class TypeFlask<VH extends RecyclerView.ViewHolder> implements Flask<VH> {
	@ViewTypeId @AnyRes
	final int viewType;

	protected TypeFlask(@ViewTypeId @AnyRes int viewType) {
		if (viewType < ViewTypeId.MIN_RES_ID) {
			throw new IllegalArgumentException("Invalid view type! Must be a resource identifier.");
		}
		this.viewType = viewType;
	}

	@Override
	public final int getViewType() {
		return viewType;
	}

	@Override
	public abstract VH createViewHolder(ViewGroup parent);

	public static <VH extends RecyclerView.ViewHolder> TypeFlask<VH> make(
			@ViewTypeId @AnyRes final int viewType,
			@NonNull final ViewFactory viewFactory,
			@NonNull final VhFactory<? extends VH> vhFactory) {
		return new CompositeTypeFlask<>(viewType, viewFactory, vhFactory);
	}

	public static <VH extends RecyclerView.ViewHolder> TypeFlask<VH> make(
			@ViewTypeId @AnyRes final int viewType,
			@LayoutRes final int itemLayout,
			@NonNull final VhFactory<? extends VH> vhFactory) {
		return new InflateTypeFlask<>(viewType, itemLayout, vhFactory);
	}

	public static <VH extends RecyclerView.ViewHolder> TypeFlask<VH> make(
			@LayoutRes final int itemLayout, @NonNull final VhFactory<? extends VH> vhFactory) {
		return make(itemLayout, itemLayout, vhFactory);
	}

	public static <VH extends RecyclerView.ViewHolder> TypeFlask<VH> make(
			@ViewTypeId @AnyRes final int viewType,
			@NonNull final ViewFactory viewFactory,
			@NonNull final Class<VH> vhCls) {
		return new CompositeReflectiveTypeFlask<>(viewType, viewFactory, vhCls);
	}

	public static <VH extends RecyclerView.ViewHolder> TypeFlask<VH> make(
			@ViewTypeId @AnyRes final int viewType,
			@LayoutRes final int itemLayout,
			@NonNull final Class<VH> vhCls) {
		return new InflateReflectiveTypeFlask<>(viewType, itemLayout, vhCls);
	}

	public static <VH extends RecyclerView.ViewHolder> TypeFlask<VH> make(
			@LayoutRes final int itemLayout, @NonNull final Class<VH> vhCls) {
		return make(itemLayout, itemLayout, vhCls);
	}
}

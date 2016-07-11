package io.jasonsparc.chemistry.internal.flasks;

import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;

import io.jasonsparc.chemistry.ViewType;

/**
 * Created by jason on 07/07/2016.
 */
public abstract class ReflectiveTypedFlask<VH extends ViewHolder> extends TypedFlask<VH> {
	@NonNull final Constructor<? extends VH> constructor;

	protected ReflectiveTypedFlask(@ViewType @AnyRes int viewType, @NonNull Class<? extends VH> vhCls) {
		super(viewType);
		this.constructor = ReflectiveVhFactory.getConstructor(vhCls);
	}

	protected abstract View createView(ViewGroup parent);

	@Override
	public VH createViewHolder(ViewGroup parent) {
		try {
			return constructor.newInstance(createView(parent));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

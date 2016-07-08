package io.jasonsparc.chemistry;

import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;

/**
 * Created by jason on 07/07/2016.
 */
public abstract class ReflectiveTypeFlask<VH extends RecyclerView.ViewHolder> extends TypeFlask<VH> {
	final Constructor<VH> constructor;

	protected ReflectiveTypeFlask(@ViewTypeId @AnyRes int viewType, @NonNull Class<VH> vhCls) {
		super(viewType);
		this.constructor = ReflectiveVhFactory.getConstructor(vhCls);
	}

	@NonNull
	protected abstract View createView(ViewGroup parent);

	@NonNull
	@Override
	public VH createViewHolder(ViewGroup parent) {
		try {
			return constructor.newInstance(createView(parent));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

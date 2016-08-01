package io.jasonsparc.chemistry.internal.vhfactories;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

import java.lang.reflect.Constructor;

import io.jasonsparc.chemistry.util.VhFactory;

/**
 * Created by jason on 07/07/2016.
 */
public class ReflectiveVhFactory<VH extends ViewHolder> implements VhFactory<VH> {
	static final Class<?>[] sConstructorSignature = {View.class};

	@NonNull final Constructor<? extends VH> constructor;

	public ReflectiveVhFactory(@NonNull Class<? extends VH> vhClass) {
		this.constructor = getConstructor(vhClass);
	}

	@Override
	public VH createViewHolder(View itemView) {
		try {
			return constructor.newInstance(itemView);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@NonNull
	static <VH> Constructor<? extends VH> getConstructor(@NonNull Class<? extends VH> vhClass) {
		try {
			Constructor<? extends VH> constructor = vhClass.getDeclaredConstructor(sConstructorSignature);
			// the constructor might not be public
			constructor.setAccessible(true);
			return constructor;
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("The specified `ViewHolder` class must have a constructor receiving an item view.", e);
		}
	}
}

package io.jasonsparc.chemistry;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.lang.reflect.Constructor;

/**
 * Created by jason on 07/07/2016.
 */
public class ReflectiveVhFactory<VH extends RecyclerView.ViewHolder> implements VhFactory<VH> {
	static final Class<?>[] sConstructorSignature = {View.class};

	final Constructor<VH> constructor;

	public ReflectiveVhFactory(Class<VH> cls) {
		this.constructor = getConstructor(cls);
	}

	@Override
	public VH createViewHolder(View itemView) {
		try {
			return constructor.newInstance(itemView);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	static <VH> Constructor<VH> getConstructor(Class<VH> cls) {
		try {
			Constructor<VH> constructor = cls.getDeclaredConstructor(sConstructorSignature);
			// the constructor might not be public
			constructor.setAccessible(true);
			return constructor;
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("The specified class must have a constructor receiving an item view.", e);
		}
	}
}

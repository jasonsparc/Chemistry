package io.jasonsparc.chemistry.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.jasonsparc.chemistry.Chemistry;
import io.jasonsparc.chemistry.Flask;
import io.jasonsparc.chemistry.FlaskSelector;

/**
 * Created by jason on 15/07/2016.
 */
public final class SingletonFlaskOpChemistry extends BaseFlaskOpChemistry implements FlaskSelector<Object> {
	@NonNull final Class<?> itemClass;
	final Flask<?> flask;

	public <Item> SingletonFlaskOpChemistry(@NonNull Chemistry base, @NonNull Class<? extends Item> itemClass, @NonNull Flask<?> flask) {
		super(base);
		this.itemClass = itemClass;
		this.flask = flask;
	}

	@SuppressWarnings("unchecked")
	@Nullable
	@Override
	public <Item> FlaskSelector<? super Item> findFlaskSelector(@NonNull Class<? extends Item> itemClass) {
		if (this.itemClass.isAssignableFrom(itemClass)) {
			return this;
		}
		return baseFlaskSelectorFinder.findFlaskSelector(itemClass);
	}

	@Override
	public Flask<?> getItemFlask(Object o) {
		return flask;
	}
}

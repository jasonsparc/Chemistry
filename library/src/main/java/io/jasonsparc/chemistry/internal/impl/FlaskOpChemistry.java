package io.jasonsparc.chemistry.internal.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.jasonsparc.chemistry.Chemistry;
import io.jasonsparc.chemistry.FlaskSelector;

/**
 * Created by jason on 15/07/2016.
 */
public final class FlaskOpChemistry extends BaseChemistry {
	@NonNull final Class<?> itemClass;
	@Nullable final FlaskSelector flaskSelector;

	public <Item> FlaskOpChemistry(@NonNull Chemistry base, @NonNull Class<? extends Item> itemClass, @Nullable FlaskSelector<? super Item> flaskSelector) {
		super(base);
		this.itemClass = itemClass;
		this.flaskSelector = flaskSelector;
	}

	@SuppressWarnings("unchecked")
	@Nullable
	@Override
	public <Item> FlaskSelector<? super Item> findFlaskSelector(@NonNull Class<? extends Item> itemClass, int flags) {
		if (this.itemClass.isAssignableFrom(itemClass)) {
			return flaskSelector;
		}
		return base.findFlaskSelector(itemClass, flags);
	}
}

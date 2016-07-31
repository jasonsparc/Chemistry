package io.jasonsparc.chemistry.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.jasonsparc.chemistry.Chemistry;
import io.jasonsparc.chemistry.FlaskSelector;

/**
 * Created by Jason on 31/07/2016.
 */
public abstract class BaseFlaskOpChemistry extends Chemistry {

	protected BaseFlaskOpChemistry(@NonNull Chemistry base) {
		super(base);
	}

	protected BaseFlaskOpChemistry(@NonNull Chemistry baseFlaskSelectorFinder, @NonNull Chemistry baseItemBinderFinder, @NonNull Chemistry baseIdSelectorFinder) {
		super(baseFlaskSelectorFinder, baseItemBinderFinder, baseIdSelectorFinder);
	}

	@Nullable
	@Override
	public abstract <Item> FlaskSelector<? super Item> findFlaskSelector(@NonNull Class<? extends Item> itemClass);

	@NonNull
	@Override
	protected final Chemistry getFlaskSelectorFinder() {
		return this;
	}
}

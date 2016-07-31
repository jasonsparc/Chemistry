package io.jasonsparc.chemistry.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.jasonsparc.chemistry.Chemistry;
import io.jasonsparc.chemistry.IdSelector;

/**
 * Created by Jason on 31/07/2016.
 */
public abstract class BaseIdentifyOpChemistry extends Chemistry {

	protected BaseIdentifyOpChemistry(@NonNull Chemistry base) {
		super(base);
	}

	protected BaseIdentifyOpChemistry(@NonNull Chemistry baseFlaskSelectorFinder, @NonNull Chemistry baseItemBinderFinder, @NonNull Chemistry baseIdSelectorFinder) {
		super(baseFlaskSelectorFinder, baseItemBinderFinder, baseIdSelectorFinder);
	}

	@Nullable
	@Override
	public abstract <Item> IdSelector<? super Item> findIdSelector(@NonNull Class<? extends Item> itemClass);

	@NonNull
	@Override
	protected final Chemistry getIdSelectorFinder() {
		return this;
	}
}

package io.jasonsparc.chemistry.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.jasonsparc.chemistry.IdSelector;
import io.jasonsparc.chemistry.internal.NullComponent;

/**
 * Created by Jason on 19/08/2016.
 */
@UtilityClass
public final class IdSelectors {

	// Factories

	// Utilities

	@SuppressWarnings("unchecked")
	public static <Item> IdSelector<Item> empty() {
		return EMPTY;
	}

	@SuppressWarnings("unchecked")
	@NonNull
	public static <Item> IdSelector<Item> ensureNoNull(@Nullable IdSelector<Item> idSelector) {
		return idSelector != null ? idSelector : EMPTY;
	}

	// Internals

	static final IdSelector EMPTY = NullComponent.INSTANCE;
}

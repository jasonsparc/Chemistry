package io.jasonsparc.chemistry.util;

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

	// Internals

	static final IdSelector EMPTY = NullComponent.INSTANCE;
}

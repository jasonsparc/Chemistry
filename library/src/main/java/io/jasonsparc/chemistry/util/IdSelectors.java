package io.jasonsparc.chemistry.util;

import io.jasonsparc.chemistry.IdSelector;
import io.jasonsparc.chemistry.internal.NullChemistry;

/**
 * Created by Jason on 19/08/2016.
 */
@UtilityClass
public final class IdSelectors {

	// Factories

	// Utilities

	public static <Item> IdSelector<Item> empty() {
		return NullChemistry.get();
	}
}

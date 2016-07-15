package io.jasonsparc.chemistry.internal.impl;

import io.jasonsparc.chemistry.Chemistry;

/**
 * Created by jason on 15/07/2016.
 */
public class ChemistryDefaults {

	public static final Chemistry EMPTY_BASE = TranscendentChemistry.INSTANCE;

	public static final Chemistry DEFAULT_FALLBACK;

	static {
		// TODO Provide defaults for the `DEFAULT_FALLBACK`...

		DEFAULT_FALLBACK = EMPTY_BASE;
	}

	public static final Chemistry DEFAULT_BASE = EMPTY_BASE.fallback(DEFAULT_FALLBACK);
}

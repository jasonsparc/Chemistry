package io.jasonsparc.chemistry.internal.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.jasonsparc.chemistry.Chemistry;

/**
 * Created by jason on 15/07/2016.
 */
public abstract class IdentityChemistry extends Chemistry {

	// Identity-like Overrides

	@Override
	public Chemistry prepend(@NonNull Chemistry chemistry) {
		return chemistry;
	}

	@Override
	public Chemistry append(@NonNull Chemistry chemistry) {
		return chemistry;
	}

	public Chemistry fallback(@Nullable Chemistry fallback) {
		return new TranscendentFallbackChemistry(fallback);
	}

	public Chemistry asFallback() {
		return this;
	}
}

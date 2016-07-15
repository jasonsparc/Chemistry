package io.jasonsparc.chemistry.internal.impl;

import android.support.annotation.NonNull;

import io.jasonsparc.chemistry.Chemistry;
import io.jasonsparc.chemistry.internal.util.ThreadUtils;
import io.jasonsparc.chemistry.internal.util.ThrowableSignal;

/**
 * Created by jason on 15/07/2016.
 */
@SuppressWarnings({"ThrowableInstanceNeverThrown", "ThrowableResultOfMethodCallIgnored"})
public final class TranscendentSignal extends ThrowableSignal {

	@NonNull
	public Chemistry fallbackRequest = NullChemistry.INSTANCE;

	public static TranscendentSignal get() {
		// Optimizes for the common case.
		return Thread.currentThread() == ThreadUtils.MAIN
				? sMainThreadInstance : OtherInternal.sInstance.get();
	}

	public static TranscendentSignal getWith(@NonNull Chemistry fallback) {
		TranscendentSignal signal = get();
		signal.fallbackRequest = fallback;
		return signal;
	}

	public final TranscendentSignal with(@NonNull Chemistry fallback) {
		this.fallbackRequest = fallback;
		return this;
	}

	public final void clear() {
		fallbackRequest = NullChemistry.INSTANCE;
	}

	// Internals

	static final TranscendentSignal sMainThreadInstance = new TranscendentSignal();

	private static class OtherInternal {

		static final ThreadLocal<TranscendentSignal> sInstance = new ThreadLocal<TranscendentSignal>() {
			@Override
			protected TranscendentSignal initialValue() {
				return new TranscendentSignal();
			}
		};
	}

	private TranscendentSignal() { }
}

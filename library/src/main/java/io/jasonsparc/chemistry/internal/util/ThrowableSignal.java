package io.jasonsparc.chemistry.internal.util;

/**
 * Created by jason on 15/07/2016.
 */
public class ThrowableSignal extends RuntimeException {

	public ThrowableSignal() {
	}

	public ThrowableSignal(Throwable throwable) {
		super(throwable);
	}

	@Override
	public Throwable fillInStackTrace() {
		return this; // Stacktrace-less!!
	}
}

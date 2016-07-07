package io.jasonsparc.chemistry;

/**
 * Created by jason on 07/07/2016.
 */
public class InvalidViewType extends RuntimeException {

	public InvalidViewType() { }

	public InvalidViewType(String detailMessage) {
		super(detailMessage);
	}

	public InvalidViewType(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public InvalidViewType(Throwable throwable) {
		super(throwable);
	}
}

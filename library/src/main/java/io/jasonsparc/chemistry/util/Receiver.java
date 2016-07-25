package io.jasonsparc.chemistry.util;

/**
 * Created by jason on 21/07/2016.
 */
public interface Receiver<T> {

	void accept(T value);
}

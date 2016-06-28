package io.jasonsparc.chemistry;

/**
 * Created by jason on 28/06/2016.
 */
public interface IdSelector<T> {

	int getItemId(T item);
}

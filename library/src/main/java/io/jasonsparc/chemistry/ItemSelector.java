package io.jasonsparc.chemistry;

/**
 * Created by jason on 28/06/2016.
 */
public interface ItemSelector<T, I> {

	I getItem(T src, int pos);
}

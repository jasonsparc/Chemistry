package io.jasonsparc.chemistry;

/**
 * Created by jason on 28/06/2016.
 */
public interface ItemContainer<T> {

	T getItem(int pos);

	int getItemCount();
}

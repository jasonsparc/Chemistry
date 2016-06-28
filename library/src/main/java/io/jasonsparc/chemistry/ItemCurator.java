package io.jasonsparc.chemistry;

/**
 * Created by jason on 28/06/2016.
 */
public interface ItemCurator<T, I> extends ItemSelector<T, I>, ItemCounter<T> {

	@Override
	I getItem(T src, int pos);

	@Override
	int getItemCount(T src);
}

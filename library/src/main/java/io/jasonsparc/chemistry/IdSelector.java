package io.jasonsparc.chemistry;

/**
 * Created by jason on 08/07/2016.
 */
public interface IdSelector<Item> {

	long getItemId(Item item);
}

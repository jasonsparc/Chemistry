package io.jasonsparc.chemistry;

/**
 * Created by jason on 12/07/2016.
 */
public interface CaseSelector<Item, K> {

	K getCaseKey(Item item);
}

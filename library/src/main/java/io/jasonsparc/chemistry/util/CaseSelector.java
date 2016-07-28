package io.jasonsparc.chemistry.util;

/**
 * Created by jason on 12/07/2016.
 */
public interface CaseSelector<Item, K> {

	K getCaseKey(Item item);
}

package io.jasonsparc.chemistry.util;

import java.util.Map;

import io.jasonsparc.chemistry.Flask;
import io.jasonsparc.chemistry.FlaskSelector;

/**
 * Created by jason on 12/07/2016.
 */
public interface FlaskSwitch<Item, K> extends FlaskSelector<Item> {

	Flask<?> getItemFlask(Item item);

	K getCaseKey(Item item);

	Flask<?> switchCase(K caseKey);

	int caseCount();

	Map<K, Flask<?>> toMap();

	Boiler<Item, K> newBoiler();

	interface Boiler<Item, K> {

		Boiler<Item, K> map(K key, Flask<?> flask);

		Boiler<Item, K> unmap(K key);

		Boiler<Item, K> mapDefault(Flask<?> defaultCase);

		Boiler<Item, K> unmapDefault();

		boolean hasBoiled();

		FlaskSwitch<Item, K> boil();
	}
}

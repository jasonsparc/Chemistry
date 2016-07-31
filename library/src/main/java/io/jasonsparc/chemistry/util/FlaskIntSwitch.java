package io.jasonsparc.chemistry.util;

import android.util.SparseArray;

import java.util.Map;

import io.jasonsparc.chemistry.Flask;

/**
 * Created by jason on 12/07/2016.
 */
public interface FlaskIntSwitch<Item> extends FlaskSwitch<Item, Integer> {

	Flask<?> getItemFlask(Item item);

	int getIntCaseKey(Item item);

	Flask<?> switchCase(int caseKey);

	Integer getCaseKey(Item item);

	Flask<?> switchCase(Integer caseKey);

	int caseCount();

	SparseArray<Flask<?>> toSparseArray();

	Map<Integer, Flask<?>> toMap();

	Boiler<Item> newBoiler();

	interface Boiler<Item> extends FlaskSwitch.Boiler<Item, Integer> {

		Boiler<Item> map(int key, Flask<?> flask);

		Boiler<Item> unmap(int key);

		Boiler<Item> map(Integer key, Flask<?> flask);

		Boiler<Item> unmap(Integer key);

		Boiler<Item> mapDefault(Flask<?> defaultCase);

		Boiler<Item> unmapDefault();

		boolean hasBoiled();

		FlaskIntSwitch<Item> boil();
	}
}

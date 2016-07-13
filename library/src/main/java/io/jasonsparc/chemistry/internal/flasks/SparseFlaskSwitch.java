package io.jasonsparc.chemistry.internal.flasks;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import io.jasonsparc.chemistry.Flask;
import io.jasonsparc.chemistry.IntCaseSelector;
import io.jasonsparc.chemistry.flasks.FlaskIntSwitch;

/**
 * Created by jason on 13/07/2016.
 */
public class SparseFlaskSwitch<Item> extends AbsSparseFlaskSwitch<Item> implements FlaskIntSwitch<Item> {
	@NonNull final IntCaseSelector<? super Item> caseSelector;

	public static <Item> FlaskIntSwitch<Item> make(@NonNull IntCaseSelector<? super Item> caseSelector, @NonNull SparseArray<? extends Flask<?>> mapOfCases, @Nullable Flask<?> defaultCase) {
		return new SparseFlaskSwitch<>(caseSelector, mapOfCases, defaultCase);
	}

	public static <Item> Boiler<Item> boiler(@NonNull IntCaseSelector<? super Item> caseSelector) {
		return new BoilerImpl<>(caseSelector);
	}

	protected SparseFlaskSwitch(@NonNull IntCaseSelector<? super Item> caseSelector, @NonNull SparseArray<? extends Flask<?>> mapOfCases, @Nullable Flask<?> defaultCase) {
		super(mapOfCases, defaultCase);
		this.caseSelector = caseSelector;
	}

	@Override
	public int getIntCaseKey(Item item) {
		return caseSelector.getIntCaseKey(item);
	}

	@Override
	public Boiler<Item> newBoiler() {
		return new BoilerImpl<>(this);
	}

	protected static class BoilerImpl<Item> extends AbsBoiler<Item> {
		final IntCaseSelector<? super Item> caseSelector;

		protected BoilerImpl(@NonNull IntCaseSelector<? super Item> caseSelector) {
			this.caseSelector = caseSelector;
		}

		protected BoilerImpl(@NonNull SparseFlaskSwitch<Item> flaskSwitch) {
			super(flaskSwitch);
			this.caseSelector = flaskSwitch.caseSelector;
		}

		@Override
		protected FlaskIntSwitch<Item> onBoil(@NonNull SparseArray<? extends Flask<?>> mapOfCases, @Nullable Flask<?> defaultCase) {
			return new SparseFlaskSwitch<>(caseSelector, mapOfCases, defaultCase);
		}
	}
}

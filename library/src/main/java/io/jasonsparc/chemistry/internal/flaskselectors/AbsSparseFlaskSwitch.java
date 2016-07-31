package io.jasonsparc.chemistry.internal.flaskselectors;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.SparseArray;

import java.util.Map;

import io.jasonsparc.chemistry.Flask;
import io.jasonsparc.chemistry.util.FlaskIntSwitch;

/**
 * Created by jason on 13/07/2016.
 */
public abstract class AbsSparseFlaskSwitch<Item> implements FlaskIntSwitch<Item> {
	@NonNull final SparseArray<? extends Flask<?>> mapOfCases;
	@Nullable final Flask<?> defaultCase;

	protected AbsSparseFlaskSwitch(@NonNull SparseArray<? extends Flask<?>> mapOfCases, @Nullable Flask<?> defaultCase) {
		this.mapOfCases = mapOfCases;
		this.defaultCase = defaultCase;
	}

	@Override
	public Flask<?> getItemFlask(Item item) {
		return switchCase(getIntCaseKey(item));
	}

	@Override
	public abstract int getIntCaseKey(Item item);

	@SuppressWarnings("unchecked")
	@Override
	public Flask<?> switchCase(int caseKey) {
		return ((SparseArray<Flask<?>>) mapOfCases).get(caseKey, defaultCase);
	}

	@Override
	public Integer getCaseKey(Item item) {
		return getIntCaseKey(item);
	}

	@Override
	public Flask<?> switchCase(Integer caseKey) {
		return switchCase((int) caseKey);
	}

	@Override
	public int caseCount() {
		return mapOfCases.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public SparseArray<Flask<?>> toSparseArray() {
		return (SparseArray<Flask<?>>) this.mapOfCases.clone();
	}

	@Override
	public Map<Integer, Flask<?>> toMap() {
		SparseArray<? extends Flask<?>> sparseArray = this.mapOfCases;
		final int size = sparseArray.size();

		ArrayMap<Integer, Flask<?>> ret = new ArrayMap<>(size);
		for (int i = 0; i < size; i++) {
			ret.put(sparseArray.keyAt(i), sparseArray.valueAt(i));
		}

		return ret;
	}

	@Override
	public abstract Boiler<Item> newBoiler();

	protected abstract static class AbsBoiler<Item> implements Boiler<Item> {
		SparseArray<Flask<?>> mapOfCases;
		Flask<?> defaultCase;

		protected AbsBoiler() {
			this.mapOfCases = new SparseArray<>();
		}

		protected AbsBoiler(@NonNull AbsSparseFlaskSwitch<Item> flaskSwitch) {
			this.mapOfCases = flaskSwitch.toSparseArray();
			this.defaultCase = flaskSwitch.defaultCase;
		}

		@Override
		public Boiler<Item> map(int key, Flask<?> flask) {
			try {
				mapOfCases.put(key, flask);
				return this;
			} catch (NullPointerException e) {
				throw checkBoiled(e);
			}
		}

		@Override
		public Boiler<Item> unmap(int key) {
			try {
				mapOfCases.delete(key);
				return this;
			} catch (NullPointerException e) {
				throw checkBoiled(e);
			}
		}

		@Override
		public Boiler<Item> map(Integer key, Flask<?> flask) {
			return map((int) key, flask);
		}

		@Override
		public Boiler<Item> unmap(Integer key) {
			return unmap((int) key);
		}

		@Override
		public Boiler<Item> mapDefault(Flask<?> defaultCase) {
			checkBoiled();
			this.defaultCase = defaultCase;
			return this;
		}

		@Override
		public Boiler<Item> unmapDefault() {
			return mapDefault(null);
		}

		@Override
		public boolean hasBoiled() {
			return mapOfCases == null;
		}

		protected abstract FlaskIntSwitch<Item> onBoil(@NonNull SparseArray<? extends Flask<?>> mapOfCases, @Nullable Flask<?> defaultCase);

		@Override
		public FlaskIntSwitch<Item> boil() {
			checkBoiled();
			try {
				return onBoil(mapOfCases, defaultCase);
			} finally {
				mapOfCases = null;
			}
		}

		protected void checkBoiled() {
			// Assumes single thread. No synchronization intended.
			if (mapOfCases == null) {
				throw new IllegalStateException("Boiler's content already boiled!");
			}
		}

		@CheckResult
		protected RuntimeException checkBoiled(RuntimeException re) {
			checkBoiled();
			return re;
		}
	}
}

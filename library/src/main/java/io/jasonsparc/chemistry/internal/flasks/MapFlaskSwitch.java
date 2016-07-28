package io.jasonsparc.chemistry.internal.flasks;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.SimpleArrayMap;

import java.util.Map;

import io.jasonsparc.chemistry.util.CaseSelector;
import io.jasonsparc.chemistry.Flask;
import io.jasonsparc.chemistry.flasks.FlaskSwitch;

/**
 * Created by jason on 13/07/2016.
 */
public class MapFlaskSwitch<Item, K> implements FlaskSwitch<Item, K> {
	@NonNull final CaseSelector<? super Item, ? extends K> caseSelector;
	@NonNull final SimpleArrayMap<? super K, ? extends Flask<?>> mapOfCases;
	@Nullable final Flask<?> defaultCase;

	public static <Item, K> FlaskSwitch<Item, K> make(@NonNull CaseSelector<? super Item, ? extends K> caseSelector, @NonNull SimpleArrayMap<? super K, ? extends Flask<?>> mapOfCases, @Nullable Flask<?> defaultCase) {
		return new MapFlaskSwitch<>(caseSelector, mapOfCases, defaultCase);
	}

	public static <Item, K> Boiler<Item, K> boiler(@NonNull CaseSelector<? super Item, ? extends K> caseSelector) {
		return new BoilerImpl<>(caseSelector);
	}

	protected MapFlaskSwitch(@NonNull CaseSelector<? super Item, ? extends K> caseSelector, @NonNull SimpleArrayMap<? super K, ? extends Flask<?>> mapOfCases, @Nullable Flask<?> defaultCase) {
		this.caseSelector = caseSelector;
		this.mapOfCases = mapOfCases;
		this.defaultCase = defaultCase;
	}

	@Override
	public Flask<?> getItemFlask(Item item) {
		return switchCase(getCaseKey(item));
	}

	@Override
	public K getCaseKey(Item item) {
		return caseSelector.getCaseKey(item);
	}

	@Override
	public Flask<?> switchCase(K caseKey) {
		final int i = mapOfCases.indexOfKey(caseKey);
		return i >= 0 ? mapOfCases.valueAt(i) : defaultCase;
	}

	@Override
	public int caseCount() {
		return mapOfCases.size();
	}

	@Override
	public Map<K, Flask<?>> toMap() {
		return new ArrayMap<>(mapOfCases);
	}

	@Override
	public Boiler<Item, K> newBoiler() {
		return new BoilerImpl<>(this);
	}

	protected static class BoilerImpl<Item, K> implements Boiler<Item, K> {
		final CaseSelector<? super Item, ? extends K> caseSelector;
		SimpleArrayMap<? super K, Flask<?>> mapOfCases;
		Flask<?> defaultCase;

		protected BoilerImpl(@NonNull CaseSelector<? super Item, ? extends K> caseSelector) {
			this.caseSelector = caseSelector;
			this.mapOfCases = new SimpleArrayMap<>();
		}

		protected BoilerImpl(@NonNull MapFlaskSwitch<Item, K> flaskSwitch) {
			this.caseSelector = flaskSwitch.caseSelector;
			this.mapOfCases = new SimpleArrayMap<>(flaskSwitch.mapOfCases);
			this.defaultCase = flaskSwitch.defaultCase;
		}

		@Override
		public Boiler<Item, K> map(K key, Flask<?> flask) {
			try {
				mapOfCases.put(key, flask);
				return this;
			} catch (NullPointerException e) {
				throw checkBoiled(e);
			}
		}

		@Override
		public Boiler<Item, K> unmap(K key) {
			try {
				mapOfCases.remove(key);
				return this;
			} catch (NullPointerException e) {
				throw checkBoiled(e);
			}
		}

		@Override
		public Boiler<Item, K> mapDefault(Flask<?> defaultCase) {
			checkBoiled();
			this.defaultCase = defaultCase;
			return this;
		}

		@Override
		public Boiler<Item, K> unmapDefault() {
			return mapDefault(null);
		}

		@Override
		public boolean hasBoiled() {
			return mapOfCases == null;
		}

		@Override
		public FlaskSwitch<Item, K> boil() {
			checkBoiled();
			try {
				return new MapFlaskSwitch<>(caseSelector, mapOfCases, defaultCase);
			} finally {
				mapOfCases = null;
			}
		}

		protected void checkBoiled() {
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

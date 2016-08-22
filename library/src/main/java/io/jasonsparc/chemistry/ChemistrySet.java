package io.jasonsparc.chemistry;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.util.SimpleArrayMap;

import java.util.ArrayList;

/**
 * TODO Docs
 *
 * Created by Jason on 21/08/2016.
 */
public abstract class ChemistrySet<Item> extends Chemistry<Item> {

	/**
	 * TODO Actual Doc
	 *
	 * Note: The returned item chemistry should only be used by the specified item; otherwise, the
	 * behavior is undefined.
	 *
	 * @param <T>
	 * @return
	 */
	public abstract <T extends Item> Chemistry<? super T> getItemChemistry(T item);

	/**
	 * TODO Actual Doc
	 * <br/>
	 * TODO Proper @throws Doc
	 *
	 * @param item
	 * @return
	 *
	 * @throws NullPointerException see, {@link #getItemViewType(Item)}
	 */
	public long getItemId(Item item) {
		final Chemistry<? super Item> chemistry = getItemChemistry(item);
		try {
			return chemistry.getItemId(item);
		} catch (NullPointerException npe) {
			throw throwCorrectNPE(chemistry, npe);
		}
	}

	/**
	 * TODO Actual Doc
	 *
	 * @param item
	 * @return
	 *
	 * @throws NullPointerException if the specified item has no Chemistry or the associated
	 * Chemistry is {@code null}
	 */
	@Override
	public int getItemViewType(Item item) {
		final Chemistry<? super Item> chemistry = getItemChemistry(item);
		try {
			return chemistry.getItemViewType(item);
		} catch (NullPointerException npe) {
			throw throwCorrectNPE(chemistry, npe);
		}
	}

	/**
	 * TODO Actual Doc
	 * <br/>
	 * TODO Proper @throws Doc
	 *
	 * @param item
	 * @return
	 *
	 * @throws NullPointerException see, {@link #getItemViewType(Item)}
	 */
	@Override
	public VhFactory<?> getVhFactory(Item item) {
		final Chemistry<? super Item> chemistry = getItemChemistry(item);
		try {
			return chemistry.getVhFactory(item);
		} catch (NullPointerException npe) {
			throw throwCorrectNPE(chemistry, npe);
		}
	}

	/**
	 * TODO Actual Doc
	 * <br/>
	 * TODO Proper @throws Doc
	 *
	 * @param item
	 * @return
	 *
	 * @throws NullPointerException see, {@link #getItemViewType(Item)}
	 */
	@Override
	public ItemBinder<? super Item, ?> getItemBinder(Item item) {
		final Chemistry<? super Item> chemistry = getItemChemistry(item);
		try {
			return chemistry.getItemBinder(item);
		} catch (NullPointerException npe) {
			throw throwCorrectNPE(chemistry, npe);
		}
	}

	static NullPointerException throwCorrectNPE(Chemistry<?> itemChemistry, NullPointerException npe) {
		throw itemChemistry != null ? npe
				// Perform error message correction
				: new NullPointerException("item's chemistry mapping is null");
		// Does not actually return anything
	}

	// Utilities

	public interface Selector<T, R> {

		R select(T t);
	}

	public interface Predicate<T> {

		boolean test(T t);
	}

	// Boiler implementations

	public static final class Boiler<Item, K> {
		final SimpleArrayMap<K, Chemistry<?>> mapOfCases = new SimpleArrayMap<>();
		@NonNull final Selector<? super Item, ? extends K> caseSelector;

		final ArrayList<Predicate<? super K>> testCases = new ArrayList<>();
		final ArrayList<Chemistry<?>> testResults = new ArrayList<>();

		int memoizeLimit = DEFAULT_MEMOIZE_LIMIT;

		Boiler(@NonNull Selector<? super Item, ? extends K> caseSelector) {
			this.caseSelector = caseSelector;
		}

		public ChemistrySet<Item> boil() {
			return testCases.size() == 0 ? new ChemistryKeyedSet<>(this) : new ChemistryCaseSet<>(this);
		}

		public <T extends Item> Boiler<Item, K> map(K caseKey, @NonNull Chemistry<? super T> mapping) {
			mapOfCases.put(caseKey, mapping);
			return this;
		}

		public <T extends Item> Boiler<Item, K> test(@NonNull Predicate<? super K> testCase, @NonNull Chemistry<? super T> result) {
			testCases.add(testCase);
			testResults.add(result);
			return this;
		}

		public Boiler<Item, K> defaultCase(@NonNull Chemistry<? super Item> defaultCase) {
			@SuppressWarnings("unchecked") K defaultKey = (K) Default.class;
			mapOfCases.put(defaultKey, defaultCase);
			return this;
		}

		public Boiler<Item, K> limitMemoize(@IntRange(from = 0) int memoizeLimit) {
			this.memoizeLimit = memoizeLimit;
			return this;
		}
	}

	public static final class ClassBoiler<Item> {
		final SimpleArrayMap<Class<?>, Chemistry<?>> mapOfCases = new SimpleArrayMap<>();
		final ArrayList<Object> testCases = new ArrayList<>(); // Either Class<?> or Predicate + Chemistry

		int memoizeLimit = DEFAULT_MEMOIZE_LIMIT;

		ClassBoiler() { }

		public ChemistrySet<Item> boil() {
			return new ChemistryClassSet<>(this);
		}

		public <T extends Item> ClassBoiler<Item> bind(@NonNull Class<? extends T> itemClass, @NonNull Chemistry<? super T> chemistry) {
			if (mapOfCases.put(itemClass, chemistry) != null) {
				testCases.remove(itemClass); // Remapped! Therefore, Reorder!
			}
			testCases.add(itemClass);
			return this;
		}

		public <T extends Item> ClassBoiler<Item> map(@NonNull Class<? extends T> caseKey, @NonNull Chemistry<? super T> mapping) {
			return bind(caseKey, mapping);
		}

		public <T extends Item> ClassBoiler<Item> test(@NonNull Predicate<? super Class<? extends Item>> testCase, @NonNull Chemistry<? super T> result) {
			testCases.add(testCase);
			testCases.add(result);
			return this;
		}

		public ClassBoiler<Item> defaultCase(@NonNull Chemistry<? super Item> defaultCase) {
			mapOfCases.put(Default.class, defaultCase);
			return this;
		}

		public ClassBoiler<Item> limitMemoize(@IntRange(from = 0) int memoizeLimit) {
			this.memoizeLimit = memoizeLimit;
			return this;
		}
	}

	// Internals

	static final int DEFAULT_MEMOIZE_LIMIT = 16;
	static final int MIN_CAPACITY_INCREMENT = 8;
	static final Object[] EMPTY = new Object[0];

	/** Default key/class/classKey **/
	static final class Default {
		private Default() { }
	}

	static final class ChemistryKeyedSet<Item, K> extends ChemistrySet<Item> {
		final SimpleArrayMap<K, Chemistry> mapOfCases = new SimpleArrayMap<>();
		@NonNull final Selector<? super Item, ? extends K> caseSelector;

		ChemistryKeyedSet(@NonNull Boiler<Item, K> boiler) {
			caseSelector = boiler.caseSelector;
			mapOfCases.putAll(boiler.mapOfCases);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T extends Item> Chemistry<? super T> getItemChemistry(T item) {
			return mapOfCases.get(caseSelector.select(item));
		}
	}

	static abstract class ChemistryMemoizedSet<Item, K> extends ChemistrySet<Item> {
		final SimpleArrayMap<K, Chemistry> mapOfCases = new SimpleArrayMap<>();

		Object[] memoizedKeys = EMPTY;
		int memoizeCursor;
		final int memoizeLimit;

		ChemistryMemoizedSet(int memoizeLimit) {
			this.memoizeLimit = memoizeLimit;
		}

		final void memoize(K key, Chemistry chemistry) {
			int limit = memoizeLimit;
			if (limit <= 0)
				return; // Memoization disabled

			int cursor = memoizeCursor;
			int capacity = memoizedKeys.length;

			if (cursor >= capacity) {
				if (cursor >= limit) {
					cursor -= limit;
				} else {
					Object[] newArray = new Object[Math.min(limit, capacity
							+ (capacity < (MIN_CAPACITY_INCREMENT / 2)
							? MIN_CAPACITY_INCREMENT : capacity >> 1))];
					System.arraycopy(memoizedKeys, 0, newArray, 0, capacity);
					memoizedKeys = newArray;
				}
			}

			Object prev = memoizedKeys[cursor];
			if (prev != null) {
				mapOfCases.remove(prev); // Remove staled memoized mapping
			}
			mapOfCases.put(key, chemistry);
			memoizedKeys[cursor] = key;
			memoizeCursor = cursor + 1;
		}
	}

	static final class ChemistryCaseSet<Item, K> extends ChemistryMemoizedSet<Item, K> {
		@NonNull final Selector<? super Item, ? extends K> caseSelector;

		final Predicate<? super K>[] testCases;
		final Chemistry[] testResults;

		@SuppressWarnings("unchecked")
		ChemistryCaseSet(@NonNull Boiler<Item, K> boiler) {
			super(boiler.memoizeLimit);
			caseSelector = boiler.caseSelector;
			mapOfCases.putAll(boiler.mapOfCases);

			int testCount = boiler.testCases.size();
			testCases = boiler.testCases.toArray(new Predicate[testCount]);
			testResults = boiler.testResults.toArray(new Chemistry[testCount]);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T extends Item> Chemistry<? super T> getItemChemistry(T item) {
			K key = caseSelector.select(item);
			Chemistry chemistry = mapOfCases.get(key);

			if (chemistry != null)
				return chemistry;

			final Predicate<? super K>[] t = this.testCases;
			for (int i = 0, len = t.length; i < len; i++) {
				if (t[i].test(key)) {
					chemistry = testResults[i];
					memoize(key, chemistry);
					return chemistry;
				}
			}
			return null;
		}
	}

	static final class ChemistryClassSet<Item> extends ChemistryMemoizedSet<Item, Class<?>> {
		final Object[] testCases; // Either Class<?> or Predicate + Chemistry

		ChemistryClassSet(@NonNull ClassBoiler<Item> boiler) {
			super(boiler.memoizeLimit);
			testCases = boiler.testCases.toArray(new Object[boiler.testCases.size()]);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T extends Item> Chemistry<? super T> getItemChemistry(T item) {
			Class<?> itemClass = item.getClass();
			Chemistry chemistry = mapOfCases.get(itemClass);

			if (chemistry != null)
				return chemistry;

			final Object[] t = this.testCases;
			for (int i = 0, len = t.length; i < len; i++) {
				Object testCase = t[i];
				if (testCase instanceof Class<?>) {
					Class<?> testClass = (Class<?>) testCase;
					if (!testClass.isAssignableFrom(itemClass)) {
						continue;
					}
					chemistry = mapOfCases.get(testClass);
				} else {
					i++; // Hop to Chemistry, to be processed below or be skipped
					if (!((Predicate) testCase).test(itemClass)) {
						continue;
					}
					chemistry = (Chemistry) t[i];
				}
				memoize(itemClass, chemistry);
				return chemistry;
			}
			return null;
		}
	}
}

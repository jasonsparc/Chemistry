package io.jasonsparc.chemistry.internal;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Created by jasonsparc on 6/7/2016.
 */
public class ClassMap<V> extends IdentityHashMap<Class<?>, V> {
	private static final Object DUMMY = new Object();

	@Nullable private IdentityHashMap<Class<?>, V> cache;

	public ClassMap() { }

	public ClassMap(int maxSize) {
		super(maxSize);
	}

	public ClassMap(Map<? extends Class<?>, ? extends V> map) {
		super(map);
	}

	@Override
	public V put(Class<?> key, V value) {
		cache = null; // Destroys cache.
		return super.put(key, value);
	}

	public V putIfUnresolved(@Nullable Class<?> key, @Nullable V value) {
		final V dummy = ClassMap.dummy();
		final V oldValue = resolve(key, dummy);
		return oldValue == dummy ? put(key, value) : oldValue;
	}

	public boolean isResolvable(@Nullable Class<?> cls) {
		final V dummy = ClassMap.dummy();
		return resolve(cls, dummy) != dummy;
	}

	/**
	 * Same as {@code resolve(cls, null)}.
	 *
	 * @see #resolve(Class, V)
	 */
	public V resolve(@Nullable Class<?> cls) {
		return resolve(cls, null);
	}

	/**
	 * Gets the mapping for the specified class, or finds the <b>nearest</b> class in its super
	 * hierarchy for any mappings, and returns the resolved mapping. If no mapping was found,
	 * returns {@code valueIfFailed}.
	 * <p>
	 * <b>"Nearest"</b> here is defined as follows...
	 * <p>
	 * Given the following declarations:
	 * <pre>
	 * interface I { }
	 *
	 * interface G { }
	 * interface F extends I { }
	 *
	 * class E implements I, G { }
	 *
	 * interface D extends F { }
	 * interface C extends I { }
	 *
	 * class B extends E { }
	 * class A extends B implements C, D, I { }
	 * </pre>
	 * We can create the following class tree diagram for class {@code A}, with the numbers on the
	 * left defining how far a hierarchy level is to {@code A}:
	 * <pre>
	 * [3]  I  G  I
	 *      | /   |
	 * [2]  E  I  F
	 *      |  |  |
	 * [1]  B  C  D  I
	 *       \ | /  /
	 *         A
	 * </pre>
	 * Now let's say that we want to resolve the mapping for {@code A}. To do that, we check every
	 * class in the above diagram for a mapping. The priority of who to check first is from left to
	 * right, then upwards.
	 * <p>
	 * The <b>nearest</b> class that has a mapping is the left most class with the lowest hierarchy
	 * level number relative to class {@code A}. This is how the <b>nearness</b> of a class is
	 * defined.
	 * <p>
	 * In the above diagram, we could see that interface {@code I} is implemented 4 times.
	 * Reimplementing an interface is one way of manipulating resolution priority.
	 * <p>
	 * If we were to visualize the diagram after all skipped {@code I}'s are removed, it would look
	 * like this:
	 * <pre>
	 * [3]  I  G
	 *      | /
	 * [2]  E     F
	 *      |     |
	 * [1]  B  C  D
	 *       \ | /
	 *         A
	 * </pre>
	 * <p>
	 * The resolution strategy is designed in such a way that interfaces such as {@link Cloneable}
	 * are not checked against yet before other classes or interfaces that should have been checked
	 * first. These classes also might have implemented {@link Cloneable}, so we must check against
	 * these classes first before finally checking against {@link Cloneable}. The same applies with
	 * checking against the {@link Object} class. Because {@link Object} is the superclass of
	 * everything, we might not want to check against it if we found it first in the resolution,
	 * but we rather skip it until every other class in the diagram has been checked against.
	 * <p>
	 * The design for the resolution strategy also takes into account that the interfaces of the
	 * superclass (which is a concrete class) has a higher priority in the resolution. This is why
	 * the ultimate class {@code I} is identified at the left most position, where the line of
	 * superclasses are located.
	 * <p>
	 * So in the recent diagram (that was modified to remove all skipped {@code I}'s), the steps
	 * for resolving a mapping is as follows:
	 * <ol>
	 * <li>Check class {@code A}. If a mapping wasn't found, proceed.</li>
	 * <li>Check class {@code B}, then {@code C}, then {@code D} until a mapping has been
	 * found.</li>
	 * <li>Otherwise, move up to level {@code [2]} and check against class {@code E}, then {@code
	 * F}.</li>
	 * <li>If still, no mapping was found, move up to level {@code [3]} and check against class
	 * {@code I}, then {@code G}.
	 * <li>Finally, after all classes have been checked and no mapping was found, we check against
	 * class {@code Object}, the superclass of every class.</li>
	 * </ol>
	 */
	public V resolve(@Nullable Class<?> cls, @Nullable V valueIfFailed) {
		if (cacheContainsKey(cls)) {
			//noinspection ConstantConditions
			return cache.get(cls);
		}
		if (containsKey(cls)) {
			return cacheMapping(cls, cls);
		}
		if (cls == null) {
			cacheFailedResolve(null);
			return valueIfFailed;
		}

		// Arrays are resolved differently.
		final boolean isArray = cls.isArray();

		if (isArray) {
			// Resolve against component type instead.
			cls = cls.getComponentType();
		}

		ArrayList<Class<?>> checkList = new ArrayList<>();
		fillInClassCheckList(checkList, cls);

		for (; ; ) {
			if (checkList.isEmpty()) {
				break;
			}

			for (Class<?> checkClass : checkList) {
				if (isArray) {
					// Convert back to array type.
					checkClass = Array.newInstance(checkClass, 0).getClass();

					// The above is more efficient than Class.forName("[L" + checkClass + ";")
					// ...since it doesn't need to allocate a `StringBuilder` and a new `String`.
				}
				if (containsKey(checkClass))
					return cacheMapping(checkClass, cls);
			}

			ArrayList<Class<?>> inputClasses = checkList;
			checkList = new ArrayList<>();

			// Performs an optimization since we know that the first item in the list is the only
			// item that can possibly have a super class.
			fillInClassCheckList(checkList, inputClasses.get(0));

			// Then we skip the first item.
			for (int i = 1, len = inputClasses.size(); i < len; i++)
				addInterfacesIfNotRedundant(checkList, inputClasses.get(i));
		}

		// `Object.class` is never added in `checkList`. So we must perform a manual check here.

		Class<?> checkClass;

		if (isArray) {
			// Arrays are different, and must be resolved against 3 more classes.

			// `Object[].class` has higher priority here, since it is an array type.
			checkClass = Object[].class;
			if (containsKey(checkClass))
				return cacheMapping(checkClass, cls);

			checkClass = Cloneable.class;
			if (containsKey(checkClass))
				return cacheMapping(checkClass, cls);

			checkClass = Serializable.class;
			if (containsKey(checkClass))
				return cacheMapping(checkClass, cls);
		}

		checkClass = Object.class;
		if (containsKey(checkClass))
			return cacheMapping(checkClass, cls);

		cacheFailedResolve(cls);
		return valueIfFailed;
	}

	private boolean cacheContainsKey(Class<?> cls) {
		return cache != null && cache.containsKey(cls);
	}

	private V cacheMapping(Class<?> src, Class<?> dst) {
		V value = get(src);
		if (cache == null) {
			cache = new IdentityHashMap<>();
		}
		cache.put(dst, value);
		return value;
	}

	private void cacheFailedResolve(Class<?> cls) {
		if (cache == null) {
			cache = new IdentityHashMap<>();
		}
		cache.put(cls, null);
	}

	private static void fillInClassCheckList(ArrayList<Class<?>> checkList, Class<?> cls) {
		final Class<?> superclass = cls.getSuperclass();

		if (superclass != null && superclass != Object.class) {
			// About `superclass != Object.class`:
			//
			// No point of adding `Object.class` since it might be removed anyway if interface
			// classes exists in `checkList`. Instead, we should check for `Object.class` in the end.

			checkList.add(superclass); // We assume that: `checkList` is always empty if there is a superclass.
			// If not, then replace the above with the following commented code:
			//addIfNotRedundant(checkList, superclass);
		}
		addInterfacesIfNotRedundant(checkList, cls);
	}

	private static void addInterfacesIfNotRedundant(ArrayList<Class<?>> checkList, Class<?> cls) {
		for (Class<?> ifClass : cls.getInterfaces()) {
			addIfNotRedundant(checkList, ifClass);
		}
	}

	private static void addIfNotRedundant(ArrayList<Class<?>> checkList, Class<?> cls) {
		final int size = checkList.size();

		for (int i = 0; i < size; i++) {
			Class<?> checkClass = checkList.get(i);

			if (checkClass == cls) {
				return; // Redundant. Already in the list.
			}
			if (cls.isAssignableFrom(checkClass)) {
				return; // Redundant. Already found somewhere in the hierarchy of `checkClass`
			}
			if (checkClass.isAssignableFrom(cls)) {
				// `checkClass` will be redundant if `cls` is added, so remove it.
				checkList.remove(i);

				// If `cls` is not added, `checkClass` is still redundant and shouldn't be in the
				// list in the first place. If every element in `checkList` is added via this
				// method, then the scenario where both `checkClass` is removed and `cls` not
				// added will never happen.

				// The rule is that, if `cls` is found to be redundant (either because it is
				// already in the list or it is a super class/interface of any class in the list)
				// then the removed `checkClass` was already redundant, since it might be existing
				// as well as the super class/interface of `cls` (that is, somewhere in the class
				// hierarchy of `cls`).
			}
		}

		// All checks passed, proceed with adding.
		checkList.add(cls);
	}

	@SuppressWarnings("unchecked")
	private static <V> V dummy() {
		return (V) DUMMY;
	}
}

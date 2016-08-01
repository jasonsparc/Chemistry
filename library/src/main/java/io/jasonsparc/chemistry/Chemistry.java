package io.jasonsparc.chemistry;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ViewHolder;

import io.jasonsparc.chemistry.internal.BindFlaskOpChemistry;
import io.jasonsparc.chemistry.internal.BindOpChemistry;
import io.jasonsparc.chemistry.internal.ChemistryDefaults;
import io.jasonsparc.chemistry.internal.FlaskOpChemistry;
import io.jasonsparc.chemistry.internal.IdentifyOpChemistry;
import io.jasonsparc.chemistry.internal.SingletonFlaskOpChemistry;
import io.jasonsparc.chemistry.util.functions.Function;

/**
 * TODO Docs
 *
 * Created by jason on 07/07/2016.
 */
public abstract class Chemistry {

	public static final Class<?> NULL_ITEM_CLASS = void.class;

	Chemistry() {
		this.baseFlaskSelectorFinder = this;
		this.baseItemBinderFinder = this;
		this.baseIdSelectorFinder = this;
	}

	protected Chemistry(@NonNull Chemistry base) {
		this.baseFlaskSelectorFinder = base.getFlaskSelectorFinder();
		this.baseItemBinderFinder = base.getItemBinderFinder();
		this.baseIdSelectorFinder = base.getIdSelectorFinder();
	}

	protected Chemistry(@NonNull Chemistry baseFlaskSelectorFinder, @NonNull Chemistry baseItemBinderFinder, @NonNull Chemistry baseIdSelectorFinder) {
		this.baseFlaskSelectorFinder = baseFlaskSelectorFinder;
		this.baseItemBinderFinder = baseItemBinderFinder;
		this.baseIdSelectorFinder = baseIdSelectorFinder;
	}

	@SuppressWarnings("unchecked")
	@NonNull
	public static <Item> Class<? extends Item> getItemClass(Item item) {
		return item == null ? (Class) NULL_ITEM_CLASS : item.getClass();
	}

	@SuppressWarnings("TypeParameterHidesVisibleType")
	public <R extends Chemistry> Chemistry compose(@NonNull Transformer<R> transformer) {
		return transformer.apply(this);
	}

	/**
	 * Transformer function used by {@link #compose}.
	 */
	public interface Transformer<R extends Chemistry> extends Function<Chemistry, R> {

		@Override
		R apply(Chemistry chemistry);
	}

	// Chaining Bases

	@CheckResult
	public static Chemistry base() {
		return ChemistryDefaults.DEFAULT_BASE;
	}

	@CheckResult
	public static Chemistry empty() {
		return NullChemistry.INSTANCE;
	}

	// Adapter Composition

	/**
	 * TODO Improve Docs
	 * <br>- associates a flask to the specified item class.
	 * <br>- a null flask selector removes any associated flask selectors for the specified item
	 * class.
	 * <br>- use `{@link #NULL_ITEM_CLASS}` as the item class to associate a null item to a flask
	 * selector.
	 *
	 * @param itemClass
	 * @param flaskSelector
	 * @param <Item>
	 * @return
	 */
	@CheckResult
	public <Item> Chemistry flask(@NonNull Class<? extends Item> itemClass, @Nullable FlaskSelector<? super Item> flaskSelector) {
		return new FlaskOpChemistry(this, itemClass, flaskSelector);
	}

	@CheckResult
	public <Item> Chemistry flask(@NonNull Class<? extends Item> itemClass, @Nullable Flask<?> flask) {
		return flask == null
				? new FlaskOpChemistry(this, itemClass, null)
				: new SingletonFlaskOpChemistry(this, itemClass, flask);
	}

	/**
	 * TODO More Docs
	 * <br>- a null item binder removes any item binders for the specified item class.
	 * <br>- use `{@link #NULL_ITEM_CLASS}` as the item class to provide bindings to a null item.
	 *
	 * @param itemClass
	 * @param bindPredicate
	 * @param itemBinder
	 * @param <Item>
	 * @param <VH>
	 * @return
	 */
	@CheckResult
	public <Item, VH extends ViewHolder> Chemistry bind(@NonNull Class<? extends Item> itemClass, @NonNull BindPredicate<? extends VH> bindPredicate, @Nullable ItemBinder<? super Item, ? super VH> itemBinder) {
		return new BindOpChemistry(this, itemClass, bindPredicate, itemBinder);
	}

	@CheckResult
	public <Item, VH extends ViewHolder> Chemistry bind(@NonNull Class<? extends Item> itemClass, Flask<? extends VH> flask, @Nullable ItemBinder<? super Item, ? super VH> itemBinder) {
		return new BindFlaskOpChemistry(this, itemClass, flask, itemBinder);
	}

	@CheckResult
	public <Item> Chemistry identify(@NonNull Class<? extends Item> itemClass, @Nullable IdSelector<? super Item> idSelector) {
		return new IdentifyOpChemistry(this, itemClass, idSelector);
	}

	// Find Operations

	@Nullable
	public <Item> FlaskSelector<? super Item> findFlaskSelector(@NonNull Class<? extends Item> itemClass) {
		return baseFlaskSelectorFinder.findFlaskSelector(itemClass);
	}

	@Nullable
	public <Item, VH extends ViewHolder> ItemBinder<? super Item, ? super VH> findItemBinder(@NonNull Class<? extends Item> itemClass, @NonNull Class<? extends VH> vhClass, Flask<? extends VH> flask) {
		return baseItemBinderFinder.findItemBinder(itemClass, vhClass, flask);
	}

	@Nullable
	public <Item> IdSelector<? super Item> findIdSelector(@NonNull Class<? extends Item> itemClass) {
		return baseIdSelectorFinder.findIdSelector(itemClass);
	}

	// Internals for Skips

	/*
	 * Invoking "find***(...)" methods on a Chemistry are optimized by skipping over irrelevant
	 * Chemistry objects in the chain that are not related to the current find operation.
	 *
	 * ------------------------------------------------
	 *
	 *     C C C C C C C
	 * f- -f---------f-x
	 * b- ---b---b-b---x
	 * i- -----i-----i-x
	 *
	 * Legend:
	 * C `Chemistry` object
	 * f `findFlaskSelector` operation
	 * b `findItemBinder` operation
	 * i `findIdSelector` operation
	 *
	 * ------------------------------------------------
	 *
	 * If a Chemistry implementation overrides one of the above "find*" methods, then it should
	 * also override a corresponding "get*Finder" method below with an implementation returning
	 * itself. Otherwise, the implementation is lost when chaining.
	 *
	 * Example:

			@Nullable
			@Override
			public <Item> IdSelector<? super Item> findIdSelector(@NonNull Class<? extends Item> itemClass) {
				...
			}

			@NonNull
			@Override
			protected Chemistry getIdSelectorFinder() {
				return this; // Prevents us from being skipped.
			}

	 */

	@NonNull
	protected Chemistry getFlaskSelectorFinder() {
		return baseFlaskSelectorFinder;
	}

	@NonNull
	protected Chemistry getItemBinderFinder() {
		return baseItemBinderFinder;
	}

	@NonNull
	protected Chemistry getIdSelectorFinder() {
		return baseIdSelectorFinder;
	}

	@NonNull protected final Chemistry baseFlaskSelectorFinder;
	@NonNull protected final Chemistry baseItemBinderFinder;
	@NonNull protected final Chemistry baseIdSelectorFinder;
}

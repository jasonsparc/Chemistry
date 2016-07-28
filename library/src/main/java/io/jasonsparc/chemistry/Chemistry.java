package io.jasonsparc.chemistry;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ViewHolder;

import io.jasonsparc.chemistry.internal.impl.BindFlaskOpChemistry;
import io.jasonsparc.chemistry.internal.impl.BindOpChemistry;
import io.jasonsparc.chemistry.internal.impl.ChemistryDefaults;
import io.jasonsparc.chemistry.internal.impl.ConcatChemistry;
import io.jasonsparc.chemistry.internal.impl.FallbackChemistry;
import io.jasonsparc.chemistry.internal.impl.FlaskOpChemistry;
import io.jasonsparc.chemistry.internal.impl.IdentifyOpChemistry;
import io.jasonsparc.chemistry.internal.impl.TranscendentFallbackChemistry;
import io.jasonsparc.chemistry.internal.impl.TranscendentSignal;
import io.jasonsparc.chemistry.internal.impl.WrapOpChemistry;
import io.jasonsparc.chemistry.util.Function;

/**
 * TODO Docs
 *
 * Created by jason on 07/07/2016.
 */
public abstract class Chemistry {

	public static final Class<?> NULL_ITEM_CLASS = void.class;

	/**
	 * TODO Improve Docs
	 * If this flag was set, the following should happen:
	 * <p>
	 * <br>- the root of the chain must throw {@link TranscendentSignal}.
	 * <br>- the base must throw {@link TranscendentSignal} and pass any fallbacks upwards.
	 * <br>- the catcher of the {@link TranscendentSignal} (who set this flag) must clear
	 * the currently set fallback.
	 */
	public static final int SIGNAL_TRANSCENDENT = 1;

	public Chemistry() { }

	@NonNull
	public static Class<?> getItemClass(Object item) {
		return item == null ? NULL_ITEM_CLASS : item.getClass();
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

	/**
	 * TODO Improve Docs
	 * <br>- wraps this chain inside a new Chemistry instance, often used to hide any subclass
	 * implementations.
	 * <br>- if the existing chain is already a wrapped Chemistry instance, the implementation can
	 * first unwrap the chain and then wrap the result inside a new wrapper instance.
	 *
	 * @return
	 */
	@CheckResult
	public Chemistry wrap() {
		return new WrapOpChemistry(this);
	}

	// Chaining Bases

	@CheckResult
	public static Chemistry base() {
		return ChemistryDefaults.DEFAULT_BASE;
	}

	@CheckResult
	public static Chemistry defaultFallback() {
		return ChemistryDefaults.DEFAULT_FALLBACK;
	}

	@CheckResult
	public static Chemistry emptyBase() {
		return ChemistryDefaults.EMPTY_BASE;
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
		return new FlaskOpChemistry(this, itemClass, flask == null ? null : Flasks.select(flask));
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

	public static <Item, VH extends ViewHolder & ItemBindable<? super Item>> ItemBinder<Item, VH> binderForBindableVh() {
		// TODO Put in a constant
		return new ItemBinder<Item, VH>() {
			@Override
			public void bindViewHolder(VH holder, Item item) {
				holder.bindItem(item);
			}
		};
	}

	@CheckResult
	public <Item, VH extends ViewHolder & ItemBindable<? super Item>> Chemistry bind(@NonNull Class<? extends Item> itemClass, @NonNull BindPredicate<? extends VH> bindPredicate) {
		return new BindOpChemistry(this, itemClass, bindPredicate, Chemistry.<Item, VH>binderForBindableVh());
	}

	@CheckResult
	public <Item, VH extends ViewHolder & ItemBindable<? super Item>> Chemistry bind(@NonNull Class<? extends Item> itemClass, Flask<? extends VH> flask) {
		return bind(itemClass, flask, Chemistry.<Item, VH>binderForBindableVh());
	}

	@CheckResult
	public <Item> Chemistry identify(@NonNull Class<? extends Item> itemClass, @Nullable IdSelector<? super Item> idSelector) {
		return new IdentifyOpChemistry(this, itemClass, idSelector);
	}

	// Concatenation Operators

	@CheckResult
	public Chemistry prepend(@NonNull Chemistry chemistry) {
		return new ConcatChemistry(chemistry, this);
	}

	@CheckResult
	public Chemistry append(@NonNull Chemistry chemistry) {
		return new ConcatChemistry(this, chemistry);
	}

	@CheckResult
	public Chemistry prepend(@NonNull Chemistry... chemistries) {
		Chemistry out = this;
		for (Chemistry chemistry : chemistries) {
			out = out.prepend(chemistry);
		}
		return out;
	}

	@CheckResult
	public Chemistry append(@NonNull Chemistry... chemistries) {
		Chemistry out = this;
		for (Chemistry chemistry : chemistries) {
			out = out.append(chemistry);
		}
		return out;
	}

	// Fallback Mechanism

	@CheckResult
	public Chemistry fallback(@Nullable Chemistry fallback) {
		return new FallbackChemistry(this, fallback);
	}

	@CheckResult
	public Chemistry asFallback() {
		return new TranscendentFallbackChemistry(this);
	}

	@CheckResult
	public Chemistry asFallbackFor(@NonNull Chemistry target) {
		return target.fallback(this);
	}

	// Find Operations

	@Nullable
	public abstract <Item> FlaskSelector<? super Item> findFlaskSelector(@NonNull Class<? extends Item> itemClass, int flags);

	@Nullable
	public abstract <Item, VH extends ViewHolder> ItemBinder<? super Item, ? super VH> findItemBinder(@NonNull Class<? extends Item> itemClass, @NonNull Class<? extends VH> vhClass, Flask<? extends VH> flask, int flags);

	@Nullable
	public abstract <Item> IdSelector<? super Item> findIdSelector(@NonNull Class<? extends Item> itemClass, int flags);
}

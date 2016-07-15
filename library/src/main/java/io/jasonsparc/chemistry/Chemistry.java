package io.jasonsparc.chemistry;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ViewHolder;

import io.jasonsparc.chemistry.internal.impl.ConcatChemistry;
import io.jasonsparc.chemistry.internal.impl.FallbackChemistry;
import io.jasonsparc.chemistry.internal.impl.TranscendentFallbackChemistry;
import io.jasonsparc.chemistry.internal.impl.TranscendentSignal;

/**
 * TODO Implement
 *
 * Created by jason on 07/07/2016.
 */
public abstract class Chemistry {
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

	// Adapter Composition

	/**
	 * TODO Improve Docs
	 * <br>- associates a flask to the specified item class.
	 * <br>- a null item class associates a null item to a flask selector.
	 * <br>- a null flask selector removes any associated flask selectors for the specified item
	 * class.
	 *
	 * @param itemClass
	 * @param flaskSelector
	 * @param <Item>
	 * @return
	 */
	public <Item> Chemistry flask(@Nullable Class<? extends Item> itemClass, @Nullable FlaskSelector<? super Item> flaskSelector) {
		return null;
	}

	/**
	 * TODO More Docs
	 * <br>- a null item class provides binding to a null item.
	 * <br>- a null item binder removes any item binders for the specified item class.
	 *
	 * @param itemClass
	 * @param bindPredicate
	 * @param itemBinder
	 * @param <Item>
	 * @param <VH>
	 * @return
	 */
	public <Item, VH extends ViewHolder> Chemistry bind(@Nullable Class<? extends Item> itemClass, @NonNull BindPredicate<? extends VH> bindPredicate, @Nullable ItemBinder<? super Item, ? super VH> itemBinder) {
		return null;
	}

	public <Item> Chemistry identify(@Nullable Class<? extends Item> itemClass, @Nullable IdSelector<? super Item> idSelector) {
		return null;
	}

	// Concatenation Operators

	public Chemistry prepend(@NonNull Chemistry chemistry) {
		return new ConcatChemistry(chemistry, this);
	}

	public Chemistry append(@NonNull Chemistry chemistry) {
		return new ConcatChemistry(this, chemistry);
	}

	public Chemistry prepend(@NonNull Chemistry... chemistries) {
		Chemistry out = this;
		for (Chemistry chemistry : chemistries) {
			out = out.prepend(chemistry);
		}
		return out;
	}

	public Chemistry append(@NonNull Chemistry... chemistries) {
		Chemistry out = this;
		for (Chemistry chemistry : chemistries) {
			out = out.append(chemistry);
		}
		return out;
	}

	// Fallback Mechanism

	public Chemistry fallback(@Nullable Chemistry fallback) {
		return new FallbackChemistry(this, fallback);
	}

	public Chemistry asFallback() {
		return new TranscendentFallbackChemistry(this);
	}

	// Find Operations

	@Nullable
	public abstract <Item> FlaskSelector<? super Item> findFlaskSelector(@Nullable Class<? extends Item> itemClass, int flags);

	@Nullable
	public abstract <Item, VH extends ViewHolder> ItemBinder<? super Item, ? super VH> findItemBinder(@Nullable Class<? extends Item> itemClass, @NonNull Class<? extends VH> vhClass, Flask<? extends VH> flask, int flags);

	@Nullable
	public abstract <Item> IdSelector<? super Item> findIdSelector(@Nullable Class<? extends Item> itemClass, int flags);
}

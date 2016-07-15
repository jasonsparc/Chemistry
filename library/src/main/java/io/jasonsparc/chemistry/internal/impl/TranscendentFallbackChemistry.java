package io.jasonsparc.chemistry.internal.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ViewHolder;

import io.jasonsparc.chemistry.Chemistry;
import io.jasonsparc.chemistry.Flask;
import io.jasonsparc.chemistry.FlaskSelector;
import io.jasonsparc.chemistry.IdSelector;
import io.jasonsparc.chemistry.ItemBinder;

/**
 * Created by jason on 15/07/2016.
 */
public final class TranscendentFallbackChemistry extends IdentityChemistry {
	@NonNull final Chemistry fallback;

	public TranscendentFallbackChemistry(@Nullable Chemistry fallback) {
		this.fallback = fallback != null ? fallback : NullChemistry.INSTANCE;
	}

	@Nullable
	@Override
	public <Item> FlaskSelector<? super Item> findFlaskSelector(@NonNull Class<? extends Item> itemClass, int flags) {
		if ((flags & SIGNAL_TRANSCENDENT) != 0)
			throw TranscendentSignal.getWith(fallback);
		return fallback.findFlaskSelector(itemClass, flags);
	}

	@Nullable
	@Override
	public <Item, VH extends ViewHolder> ItemBinder<? super Item, ? super VH> findItemBinder(@NonNull Class<? extends Item> itemClass, @NonNull Class<? extends VH> vhClass, Flask<? extends VH> flask, int flags) {
		if ((flags & SIGNAL_TRANSCENDENT) != 0)
			throw TranscendentSignal.getWith(fallback);
		return fallback.findItemBinder(itemClass, vhClass, flask, flags);
	}

	@Nullable
	@Override
	public <Item> IdSelector<? super Item> findIdSelector(@NonNull Class<? extends Item> itemClass, int flags) {
		if ((flags & SIGNAL_TRANSCENDENT) != 0)
			throw TranscendentSignal.getWith(fallback);
		return fallback.findIdSelector(itemClass, flags);
	}

	// Special Identity-like Overrides

	@Override
	public Chemistry prepend(@NonNull Chemistry chemistry) {
		return new FallbackChemistry(chemistry, fallback);
	}

	@Override
	public Chemistry append(@NonNull Chemistry chemistry) {
		return new FallbackChemistry(chemistry, fallback);
	}
}

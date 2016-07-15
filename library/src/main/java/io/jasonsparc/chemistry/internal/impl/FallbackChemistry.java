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
public final class FallbackChemistry extends Chemistry {
	@NonNull final Chemistry base;
	@NonNull final Chemistry fallback;

	public FallbackChemistry(@NonNull Chemistry base, @Nullable Chemistry fallback) {
		this.base = base;
		this.fallback = fallback != null ? fallback : NullChemistry.INSTANCE;
	}

	@Nullable
	@Override
	public <Item> FlaskSelector<? super Item> findFlaskSelector(@Nullable Class<? extends Item> itemClass, int flags) {
		try {
			return base.findFlaskSelector(itemClass, flags | SIGNAL_TRANSCENDENT);
		} catch (TranscendentSignal ts) {
			if ((flags & SIGNAL_TRANSCENDENT) != 0) {
				throw ts.with(fallback);
			}
			ts.clear();

			return fallback.findFlaskSelector(itemClass, flags);
		}
	}

	@Nullable
	@Override
	public <Item, VH extends ViewHolder> ItemBinder<? super Item, ? super VH> findItemBinder(@Nullable Class<? extends Item> itemClass, @NonNull Class<? extends VH> vhClass, Flask<? extends VH> flask, int flags) {
		try {
			return base.findItemBinder(itemClass, vhClass, flask, flags | SIGNAL_TRANSCENDENT);
		} catch (TranscendentSignal ts) {
			if ((flags & SIGNAL_TRANSCENDENT) != 0) {
				throw ts.with(fallback);
			}
			ts.clear();

			return fallback.findItemBinder(itemClass, vhClass, flask, flags);
		}
	}

	@Nullable
	@Override
	public <Item> IdSelector<? super Item> findIdSelector(@Nullable Class<? extends Item> itemClass, int flags) {
		try {
			return base.findIdSelector(itemClass, flags | SIGNAL_TRANSCENDENT);
		} catch (TranscendentSignal ts) {
			if ((flags & SIGNAL_TRANSCENDENT) != 0) {
				throw ts.with(fallback);
			}
			ts.clear();

			return fallback.findIdSelector(itemClass, flags);
		}
	}
}

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
public final class ConcatChemistry extends Chemistry {
	@NonNull final Chemistry base;
	@NonNull final Chemistry other;

	public ConcatChemistry(@NonNull Chemistry base, @NonNull Chemistry other) {
		this.base = base;
		this.other = other;
	}

	@Nullable
	@Override
	public <Item> FlaskSelector<? super Item> findFlaskSelector(@NonNull Class<? extends Item> itemClass, int flags) {
		Chemistry backup;

		try {
			return other.findFlaskSelector(itemClass, flags | SIGNAL_TRANSCENDENT);
		} catch (TranscendentSignal ts) {
			backup = ts.fallbackRequest;
		}

		try {
			return base.findFlaskSelector(itemClass, flags | SIGNAL_TRANSCENDENT);
		} catch (TranscendentSignal ts) {
			if ((flags & SIGNAL_TRANSCENDENT) != 0) {
				throw ts.with(backup); // Rethrow!
			}
			ts.clear();

			return backup.findFlaskSelector(itemClass, flags);
		}
	}

	@Nullable
	@Override
	public <Item, VH extends ViewHolder> ItemBinder<? super Item, ? super VH> findItemBinder(@NonNull Class<? extends Item> itemClass, @NonNull Class<? extends VH> vhClass, Flask<? extends VH> flask, int flags) {
		Chemistry backup;

		try {
			return other.findItemBinder(itemClass, vhClass, flask, flags | SIGNAL_TRANSCENDENT);
		} catch (TranscendentSignal ts) {
			backup = ts.fallbackRequest;
		}

		try {
			return base.findItemBinder(itemClass, vhClass, flask, flags | SIGNAL_TRANSCENDENT);
		} catch (TranscendentSignal ts) {
			if ((flags & SIGNAL_TRANSCENDENT) != 0) {
				throw ts.with(backup); // Rethrow!
			}
			ts.clear();

			return backup.findItemBinder(itemClass, vhClass, flask, flags);
		}
	}

	@Nullable
	@Override
	public <Item> IdSelector<? super Item> findIdSelector(@NonNull Class<? extends Item> itemClass, int flags) {
		Chemistry backup;

		try {
			return other.findIdSelector(itemClass, flags | SIGNAL_TRANSCENDENT);
		} catch (TranscendentSignal ts) {
			backup = ts.fallbackRequest;
		}

		try {
			return base.findIdSelector(itemClass, flags | SIGNAL_TRANSCENDENT);
		} catch (TranscendentSignal ts) {
			if ((flags & SIGNAL_TRANSCENDENT) != 0) {
				throw ts.with(backup); // Rethrow!
			}
			ts.clear();

			return backup.findIdSelector(itemClass, flags);
		}
	}
}

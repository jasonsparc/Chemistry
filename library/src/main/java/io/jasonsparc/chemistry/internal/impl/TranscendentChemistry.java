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
public final class TranscendentChemistry extends Chemistry {
	public static final TranscendentChemistry INSTANCE = new TranscendentChemistry();

	private TranscendentChemistry() { }

	@Nullable
	@Override
	public <Item> FlaskSelector<? super Item> findFlaskSelector(@Nullable Class<? extends Item> itemClass, int flags) {
		if ((flags & SIGNAL_TRANSCENDENT) != 0)
			throw TranscendentSignal.get();
		return null;
	}

	@Nullable
	@Override
	public <Item, VH extends ViewHolder> ItemBinder<? super Item, ? super VH> findItemBinder(@Nullable Class<? extends Item> itemClass, @NonNull Class<? extends VH> vhClass, Flask<? extends VH> flask, int flags) {
		if ((flags & SIGNAL_TRANSCENDENT) != 0)
			throw TranscendentSignal.get();
		return null;
	}

	@Nullable
	@Override
	public <Item> IdSelector<? super Item> findIdSelector(@Nullable Class<? extends Item> itemClass, int flags) {
		if ((flags & SIGNAL_TRANSCENDENT) != 0)
			throw TranscendentSignal.get();
		return null;
	}
}

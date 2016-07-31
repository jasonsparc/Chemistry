package io.jasonsparc.chemistry.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ViewHolder;

import io.jasonsparc.chemistry.Chemistry;
import io.jasonsparc.chemistry.Flask;
import io.jasonsparc.chemistry.ItemBinder;

/**
 * Created by Jason on 31/07/2016.
 */
public abstract class BaseBindOpChemistry extends Chemistry {

	protected BaseBindOpChemistry(@NonNull Chemistry base) {
		super(base);
	}

	protected BaseBindOpChemistry(@NonNull Chemistry baseFlaskSelectorFinder, @NonNull Chemistry baseItemBinderFinder, @NonNull Chemistry baseIdSelectorFinder) {
		super(baseFlaskSelectorFinder, baseItemBinderFinder, baseIdSelectorFinder);
	}

	@Nullable
	@Override
	public abstract <Item, VH extends ViewHolder> ItemBinder<? super Item, ? super VH> findItemBinder(@NonNull Class<? extends Item> itemClass, @NonNull Class<? extends VH> vhClass, Flask<? extends VH> flask);

	@NonNull
	@Override
	protected final Chemistry getItemBinderFinder() {
		return this;
	}
}

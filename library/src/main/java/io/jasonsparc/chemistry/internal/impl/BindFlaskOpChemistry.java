package io.jasonsparc.chemistry.internal.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ViewHolder;

import io.jasonsparc.chemistry.Chemistry;
import io.jasonsparc.chemistry.Flask;
import io.jasonsparc.chemistry.ItemBinder;

/**
 * Created by jason on 15/07/2016.
 */
public final class BindFlaskOpChemistry extends BaseChemistry {
	@NonNull final Class<?> itemClass;
	final Flask<?> checkFlask;
	@Nullable final ItemBinder itemBinder;

	public <Item, VH extends ViewHolder> BindFlaskOpChemistry(@NonNull Chemistry base, @NonNull Class<? extends Item> itemClass, Flask<? extends VH> checkFlask, @Nullable ItemBinder<? super Item, ? super VH> itemBinder) {
		super(base);
		this.itemClass = itemClass;
		this.checkFlask = checkFlask;
		this.itemBinder = itemBinder;
	}

	@SuppressWarnings("unchecked")
	@Nullable
	@Override
	public <Item, VH extends ViewHolder> ItemBinder<? super Item, ? super VH> findItemBinder(@NonNull Class<? extends Item> itemClass, @NonNull Class<? extends VH> vhClass, Flask<? extends VH> flask, int flags) {
		if (this.itemClass.isAssignableFrom(itemClass) && flask.equals(checkFlask)) {
			return itemBinder;
		}
		return base.findItemBinder(itemClass, vhClass, flask, flags);
	}
}

package io.jasonsparc.chemistry.internal.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ViewHolder;

import io.jasonsparc.chemistry.BindPredicate;
import io.jasonsparc.chemistry.Chemistry;
import io.jasonsparc.chemistry.Flask;
import io.jasonsparc.chemistry.ItemBinder;

/**
 * Created by jason on 15/07/2016.
 */
public final class BindOpChemistry extends BaseChemistry {
	@NonNull final Class<?> itemClass;
	@NonNull final BindPredicate bindPredicate;
	@Nullable final ItemBinder itemBinder;

	public <Item, VH extends ViewHolder> BindOpChemistry(@NonNull Chemistry base, @NonNull Class<? extends Item> itemClass, @NonNull BindPredicate<? extends VH> bindPredicate, @Nullable ItemBinder<? super Item, ? super VH> itemBinder) {
		super(base);
		this.itemClass = itemClass;
		this.bindPredicate = bindPredicate;
		this.itemBinder = itemBinder;
	}

	@SuppressWarnings("unchecked")
	@Nullable
	@Override
	public <Item, VH extends ViewHolder> ItemBinder<? super Item, ? super VH> findItemBinder(@NonNull Class<? extends Item> itemClass, @NonNull Class<? extends VH> vhClass, Flask<? extends VH> flask, int flags) {
		if (this.itemClass.isAssignableFrom(itemClass) && bindPredicate.checkBind(flask, vhClass)) {
			return itemBinder;
		}
		return base.findItemBinder(itemClass, vhClass, flask, flags);
	}
}

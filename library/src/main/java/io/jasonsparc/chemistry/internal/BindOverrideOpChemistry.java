package io.jasonsparc.chemistry.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ViewHolder;

import io.jasonsparc.chemistry.BindPredicate;
import io.jasonsparc.chemistry.Chemistry;
import io.jasonsparc.chemistry.Flask;
import io.jasonsparc.chemistry.ItemBinder;
import io.jasonsparc.chemistry.ItemBinderOverride;

/**
 * Created by jason on 15/07/2016.
 */
public final class BindOverrideOpChemistry extends BaseBindOpChemistry {
	@NonNull final Class<?> itemClass;
	@NonNull final BindPredicate bindPredicate;
	@Nullable final ItemBinderOverride itemBinderOverride;

	public <Item, VH extends ViewHolder> BindOverrideOpChemistry(@NonNull Chemistry base, @NonNull Class<? extends Item> itemClass, @NonNull BindPredicate<? extends VH> bindPredicate, @Nullable ItemBinderOverride<? super Item, ? super VH> itemBinderOverride) {
		super(base);
		this.itemClass = itemClass;
		this.bindPredicate = bindPredicate;
		this.itemBinderOverride = itemBinderOverride;
	}

	@SuppressWarnings("unchecked")
	@Nullable
	@Override
	public <Item, VH extends ViewHolder> ItemBinder<? super Item, ? super VH> findItemBinder(@NonNull Class<? extends Item> itemClass, @NonNull Class<? extends VH> vhClass, Flask<? extends VH> flask) {
		ItemBinder<? super Item, ? super VH> base = baseItemBinderFinder.findItemBinder(itemClass, vhClass, flask);
		if (base != null && this.itemClass.isAssignableFrom(itemClass) && bindPredicate.checkBind(vhClass, flask)) {
			return new ItemBinder<Item, VH>() {
				@Override
				public void bindViewHolder(VH holder, Item item) {

				}
			};
		}
		return base;
	}
}

package io.jasonsparc.chemistry.internal.item_binders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;

import io.jasonsparc.chemistry.ItemBinder;
import io.jasonsparc.chemistry.ItemBinderOverride;

/**
 * Created by jason on 10/08/2016.
 */
public final class ItemBinderOverrideWrapper<Item, VH extends ViewHolder> implements ItemBinder<Item, VH> {
	@NonNull final ItemBinder<? super Item, ? super VH> base;
	@NonNull final ItemBinderOverride<Item, VH> override;

	ItemBinderOverrideWrapper(@NonNull ItemBinder<? super Item, ? super VH> base, @NonNull ItemBinderOverride<Item, VH> override) {
		this.base = base;
		this.override = override;
	}

	@Override
	public void bindViewHolder(VH holder, Item item) {
		override.bindViewHolder(base, holder, item);
	}
}

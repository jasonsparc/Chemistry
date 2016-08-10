package io.jasonsparc.chemistry.internal.item_binders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;

import io.jasonsparc.chemistry.ItemBinder;
import io.jasonsparc.chemistry.ItemBinder2;
import io.jasonsparc.chemistry.util.VhInitializer;
import io.jasonsparc.chemistry.util.VhInitializers;

/**
 * Created by jason on 05/08/2016.
 */
public final class CompositeItemBinder2<Item, VH extends ViewHolder> implements ItemBinder2<Item, VH> {
	@NonNull final VhInitializer<? super VH> vhInitializer;
	@NonNull final ItemBinder<? super Item, ? super VH> itemBinder;

	@SuppressWarnings("unchecked")
	public static <Item, VH extends ViewHolder> CompositeItemBinder2<Item, VH> make(@NonNull VhInitializer<? super VH> vhInitializer, @NonNull ItemBinder<? super Item, ? super VH> itemBinder) {
		if (itemBinder instanceof ItemBinder2<?, ?>) {
			final VhInitializer<? super VH> postVhInitializer;

			if (itemBinder instanceof CompositeItemBinder2<?, ?>) {
				CompositeItemBinder2<? super Item, ? super VH> compositeItemBinder2 = (CompositeItemBinder2) itemBinder;

				postVhInitializer = compositeItemBinder2.vhInitializer;
				itemBinder = compositeItemBinder2.itemBinder;
			} else {
				postVhInitializer = (ItemBinder2) itemBinder;
			}

			if (vhInitializer instanceof CompositeItemBinder2<?, ?>) {
				vhInitializer = ((CompositeItemBinder2) vhInitializer).vhInitializer;
			}
			vhInitializer = VhInitializers.make(vhInitializer, postVhInitializer);
		}
		return new CompositeItemBinder2(vhInitializer, itemBinder);
	}

	public static <Item, VH extends ViewHolder> CompositeItemBinder2<Item, VH> makeDirect(@NonNull VhInitializer<? super VH> vhInitializer, @NonNull ItemBinder<? super Item, ? super VH> itemBinder) {
		return new CompositeItemBinder2<>(vhInitializer, itemBinder);
	}

	CompositeItemBinder2(@NonNull VhInitializer<? super VH> vhInitializer, @NonNull ItemBinder<? super Item, ? super VH> itemBinder) {
		this.vhInitializer = vhInitializer;
		this.itemBinder = itemBinder;
	}

	@Override
	public void initViewHolder(VH holder) {
		vhInitializer.initViewHolder(holder);
	}

	@Override
	public void bindViewHolder(VH holder, Item item) {
		itemBinder.bindViewHolder(holder, item);
	}
}

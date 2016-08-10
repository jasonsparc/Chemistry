package io.jasonsparc.chemistry.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;
import java.util.Collection;

import io.jasonsparc.chemistry.ItemBinder;
import io.jasonsparc.chemistry.ItemBinder2;
import io.jasonsparc.chemistry.internal.item_binders.ArrayItemBinder;
import io.jasonsparc.chemistry.internal.item_binders.CompositeItemBinder2;
import io.jasonsparc.chemistry.internal.item_binders.PairItemBinder;

/**
 * Created by jason on 05/08/2016.
 */
@UtilityClass
public final class ItemBinders {

	@SuppressWarnings("unchecked")
	public static <Item, VH extends ViewHolder> ItemBinder<Item, VH> make(@NonNull ItemBinder<? super Item, ? super VH> itemBinder) {
		return (ItemBinder<Item, VH>) itemBinder;
	}

	@SuppressWarnings("unchecked")
	public static <Item, VH extends ViewHolder> ItemBinder2<Item, VH> make(@NonNull ItemBinder2<? super Item, ? super VH> itemBinder2) {
		return (ItemBinder2<Item, VH>) itemBinder2;
	}

	public static <Item, VH extends ViewHolder> ItemBinder2<Item, VH> make(@NonNull VhInitializer<? super VH> vhInitializer, @NonNull ItemBinder<? super Item, ? super VH> itemBinder) {
		return CompositeItemBinder2.make(vhInitializer, itemBinder);
	}

	@SuppressWarnings("unchecked")
	public static <Item, VH extends ViewHolder> ItemBinder<Item, VH> make(@NonNull ItemBinder<? super Item, ? super VH> first, @NonNull ItemBinder<? super Item, ? super VH> second) {
		ItemBinder<Item, VH> pairItemBinder = new PairItemBinder<>(first, second);
		VhInitializer<? super VH> vhInitializer = null;

		if (second instanceof ItemBinder2<?, ?>) {
			vhInitializer = (ItemBinder2) second;
		}
		if (first instanceof ItemBinder2<?, ?>) {
			VhInitializer<? super VH> prepend = (ItemBinder2) first;
			if (vhInitializer != null) {
				vhInitializer = VhInitializers.make(prepend, vhInitializer);
			} else {
				vhInitializer = prepend;
			}
		}
		if (vhInitializer != null) {
			return CompositeItemBinder2.makeDirect(vhInitializer, pairItemBinder);
		}
		return pairItemBinder;
	}

	@SuppressWarnings("unchecked")
	@SafeVarargs
	public static <Item, VH extends ViewHolder> ItemBinder<Item, VH> make(@NonNull ItemBinder<? super Item, ? super VH>... itemBinders) {
		final int length = itemBinders.length;
		if (length == 2) {
			return make(itemBinders[0], itemBinders[1]);
		}

		ArrayList<VhInitializer<? super VH>> vhInitializers = null;

		for (int i = 0; i < length; i++) {
			final ItemBinder<? super Item, ? super VH> itemBinder = itemBinders[i];

			if (itemBinder instanceof ItemBinder2<?, ?>) {
				if (vhInitializers == null) {
					vhInitializers = new ArrayList<>(length - i);
				}
				vhInitializers.add((ItemBinder2) itemBinder);
			}
		}

		ItemBinder<Item, VH> arrayItemBinder = new ArrayItemBinder<>(itemBinders);

		if (vhInitializers != null) {
			VhInitializer<? super VH> vhInitializer = VhInitializers.make(vhInitializers);
			return CompositeItemBinder2.makeDirect(vhInitializer, arrayItemBinder);
		}
		return arrayItemBinder;
	}

	public static <Item, VH extends ViewHolder> ItemBinder<Item, VH> make(@NonNull Collection<? extends ItemBinder<? super Item, ? super VH>> itemBinders) {
		return make(array(itemBinders));
	}

	// Utilities

	@SafeVarargs
	public static <B extends ItemBinder<?, ? extends ViewHolder>> B[] array(@NonNull B... itemBinders) {
		return itemBinders;
	}

	@SuppressWarnings("unchecked")
	public static <Item, VH extends ViewHolder> ItemBinder<? super Item, ? super VH>[] array(@NonNull Collection<? extends ItemBinder<? super Item, ? super VH>> itemBinders) {
		return itemBinders.toArray(new ItemBinder[itemBinders.size()]);
	}
}

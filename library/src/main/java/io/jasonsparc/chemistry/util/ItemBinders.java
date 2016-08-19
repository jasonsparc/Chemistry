package io.jasonsparc.chemistry.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;

import java.util.Collection;
import java.util.List;

import io.jasonsparc.chemistry.ItemBinder;
import io.jasonsparc.chemistry.internal.NullChemistry;
import io.jasonsparc.chemistry.internal.item_binders.ArrayItemBinder;
import io.jasonsparc.chemistry.internal.item_binders.PairItemBinder;

/**
 * Created by jason on 05/08/2016.
 */
@UtilityClass
public final class ItemBinders {

	// Factories

	@SuppressWarnings("unchecked")
	public static <Item, VH extends ViewHolder> ItemBinder<Item, VH> make(@NonNull ItemBinder<? super Item, ? super VH> itemBinder) {
		return (ItemBinder<Item, VH>) itemBinder;
	}

	public static <Item, VH extends ViewHolder> ItemBinder<Item, VH> make(@NonNull ItemBinder<? super Item, ? super VH> first, @NonNull ItemBinder<? super Item, ? super VH> second) {
		return new PairItemBinder<>(first, second);
	}

	@SafeVarargs
	public static <Item, VH extends ViewHolder> ItemBinder<Item, VH> make(@NonNull ItemBinder<? super Item, ? super VH>... itemBinders) {
		switch (itemBinders.length) {
			case 0:
				return empty();
			case 1:
				return make(itemBinders[0]);
			case 2:
				return make(itemBinders[0], itemBinders[1]);
			default:
				return new ArrayItemBinder<>(itemBinders);
		}
	}

	public static <Item, VH extends ViewHolder> ItemBinder<Item, VH> make(@NonNull List<? extends ItemBinder<? super Item, ? super VH>> itemBinders) {
		switch (itemBinders.size()) {
			case 0:
				return empty();
			case 1:
				return make(itemBinders.get(0));
			case 2:
				return make(itemBinders.get(0), itemBinders.get(1));
			default:
				return new ArrayItemBinder<>(array(itemBinders));
		}
	}

	@SuppressWarnings("unchecked")
	public static <Item, VH extends ViewHolder> ItemBinder<Item, VH> make(@NonNull Collection<? extends ItemBinder<? super Item, ? super VH>> itemBinders) {
		return itemBinders instanceof List<?> ? make((List) itemBinders) : make(array(itemBinders));
	}

	// Utilities

	public static <Item, VH extends ViewHolder> ItemBinder<Item, VH> empty() {
		return NullChemistry.get();
	}

	@SafeVarargs
	public static <B extends ItemBinder<?, ? extends ViewHolder>> B[] array(@NonNull B... itemBinders) {
		return itemBinders;
	}

	@SuppressWarnings("unchecked")
	public static <Item, VH extends ViewHolder> ItemBinder<? super Item, ? super VH>[] array(@NonNull Collection<? extends ItemBinder<? super Item, ? super VH>> itemBinders) {
		return itemBinders.toArray(new ItemBinder[itemBinders.size()]);
	}
}

package io.jasonsparc.chemistry.internal;

import io.jasonsparc.chemistry.Chemistry;
import io.jasonsparc.chemistry.ItemBinder;
import io.jasonsparc.chemistry.VhFactory;
import io.jasonsparc.chemistry.ViewType;

/**
 * Created by Jason on 19/08/2016.
 */
public final class NullChemistry<Item> extends Chemistry<Item> {
	public static final NullChemistry INSTANCE = new NullChemistry<>();

	private NullChemistry() { }

	@SuppressWarnings("unchecked")
	public static <Item> NullChemistry<Item> get() {
		return INSTANCE;
	}

	@Override
	public int getItemViewType(Item item) {
		return ViewType.INVALID;
	}

	@Override
	public VhFactory<?> getVhFactory(Item item) {
		return null;
	}

	@Override
	public ItemBinder<? super Item, ?> getItemBinder(Item item) {
		return null;
	}

	@Override
	public String toString() {
		return "io.chemistry.NullChemistry";
	}
}

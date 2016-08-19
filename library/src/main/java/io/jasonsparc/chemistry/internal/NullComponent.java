package io.jasonsparc.chemistry.internal;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;

import io.jasonsparc.chemistry.Chemistry;
import io.jasonsparc.chemistry.IdSelector;
import io.jasonsparc.chemistry.ItemBinder;
import io.jasonsparc.chemistry.ItemVhFactory;
import io.jasonsparc.chemistry.TypeSelector;
import io.jasonsparc.chemistry.VhFactory;
import io.jasonsparc.chemistry.VhInitializer;
import io.jasonsparc.chemistry.ViewFactory;
import io.jasonsparc.chemistry.ViewType;

/**
 * Created by Jason on 20/08/2016.
 */
public final class NullComponent<Item, VH extends ViewHolder> extends Chemistry<Item> implements IdSelector<Item>, TypeSelector<Item>, ViewFactory, ItemVhFactory<VH>, VhFactory<VH>, VhInitializer<VH>, ItemBinder<Item, VH> {
	public static final NullComponent INSTANCE = new NullComponent<>();

	private NullComponent() { }

	@SuppressWarnings("unchecked")
	public static <Item, VH extends ViewHolder> NullComponent<Item, VH> get() {
		return INSTANCE;
	}

	@Override
	public long getItemId(Item item) {
		return NO_ID;
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
	public View createView(ViewGroup parent) {
		return null;
	}

	@Override
	public VH createViewHolder(View itemView) {
		return null;
	}

	@Override
	public VH createViewHolder(ViewGroup parent) {
		return null;
	}

	@Override
	public void initViewHolder(VH holder) {
		// NOP
	}

	@Override
	public void bindViewHolder(VH holder, Item item) {
		// NOP
	}

	@Override
	public String toString() {
		return "io.chemistry.NullComponent";
	}
}

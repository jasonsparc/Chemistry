package io.jasonsparc.chemistry.internal;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

import io.jasonsparc.chemistry.BasicChemistry;
import io.jasonsparc.chemistry.VhInitializer;

/**
 * Created by Jason on 19/08/2016.
 */
public final class NullChemistry<Item, VH extends ViewHolder> extends BasicChemistry<Item, VH> implements VhInitializer<VH> {
	public static final NullChemistry INSTANCE = new NullChemistry<>();

	private NullChemistry() { }

	@SuppressWarnings("unchecked")
	public static <Item, VH extends ViewHolder> NullChemistry<Item, VH> get() {
		return INSTANCE;
	}

	@Override
	public int getViewType() {
		throw new UnsupportedOperationException();
	}

	@Override
	public VH createViewHolder(ViewGroup parent) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void initViewHolder(VH holder) {
		// Do nothing.
	}

	@Override
	public void bindViewHolder(ViewHolder holder, Object o) {
		// Do nothing.
	}
}

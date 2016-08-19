package io.jasonsparc.chemistry.internal;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

import io.jasonsparc.chemistry.BasicChemistry;
import io.jasonsparc.chemistry.ViewType;

/**
 * Created by Jason on 19/08/2016.
 */
public final class NullChemistry<Item, VH extends ViewHolder> extends BasicChemistry<Item, VH> {
	public static final NullChemistry INSTANCE = new NullChemistry<>();

	private NullChemistry() { }

	@SuppressWarnings("unchecked")
	public static <Item, VH extends ViewHolder> NullChemistry<Item, VH> get() {
		return INSTANCE;
	}

	@Override
	public int getViewType() {
		return ViewType.INVALID;
	}

	@Override
	public VH createViewHolder(ViewGroup parent) {
		return null;
	}

	@Override
	public void bindViewHolder(ViewHolder holder, Object o) {
		// Do nothing.
	}
}

package io.jasonsparc.chemistry.internal.vh_factories;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

import io.jasonsparc.chemistry.ItemVhFactory;
import io.jasonsparc.chemistry.VhFactory;
import io.jasonsparc.chemistry.ViewFactory;

/**
 * Created by Jason on 19/08/2016.
 */
public class CompositeVhFactory<VH extends ViewHolder> implements VhFactory<VH> {
	@NonNull final ViewFactory viewFactory;
	@NonNull final ItemVhFactory<? extends VH> itemVhFactory;

	public CompositeVhFactory(@NonNull ViewFactory viewFactory, @NonNull ItemVhFactory<? extends VH> itemVhFactory) {
		this.viewFactory = viewFactory;
		this.itemVhFactory = itemVhFactory;
	}

	@Override
	public VH createViewHolder(ViewGroup parent) {
		return itemVhFactory.createViewHolder(viewFactory.createView(parent));
	}
}

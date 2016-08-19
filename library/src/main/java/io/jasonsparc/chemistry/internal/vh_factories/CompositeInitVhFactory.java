package io.jasonsparc.chemistry.internal.vh_factories;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

import io.jasonsparc.chemistry.ItemVhFactory;
import io.jasonsparc.chemistry.VhInitializer;
import io.jasonsparc.chemistry.ViewFactory;

/**
 * Created by Jason on 19/08/2016.
 */
public class CompositeInitVhFactory<VH extends ViewHolder> extends CompositeVhFactory<VH> {
	@NonNull final VhInitializer<? super VH> vhInitializer;

	public CompositeInitVhFactory(@NonNull ViewFactory viewFactory, @NonNull ItemVhFactory<? extends VH> itemVhFactory, @NonNull VhInitializer<? super VH> vhInitializer) {
		super(viewFactory, itemVhFactory);
		this.vhInitializer = vhInitializer;
	}

	@Override
	public VH createViewHolder(ViewGroup parent) {
		VH vh = itemVhFactory.createViewHolder(viewFactory.createView(parent));
		vhInitializer.initViewHolder(vh);
		return vh;
	}
}

package io.jasonsparc.chemistry.internal.vh_factories;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

import io.jasonsparc.chemistry.ItemVhFactory;
import io.jasonsparc.chemistry.VhFactory;
import io.jasonsparc.chemistry.VhInitializer;
import io.jasonsparc.chemistry.ViewFactory;
import io.jasonsparc.chemistry.util.VhInitializers;

/**
 * Created by Jason on 19/08/2016.
 */
public final class CompositeInitVhFactory<VH extends ViewHolder> implements VhFactory<VH> {
	@NonNull final ViewFactory viewFactory;
	@NonNull final ItemVhFactory<? extends VH> itemVhFactory;
	@NonNull final VhInitializer<? super VH> vhInitializer;

	public CompositeInitVhFactory(@NonNull ViewFactory viewFactory, @NonNull ItemVhFactory<? extends VH> itemVhFactory, @NonNull VhInitializer<? super VH> vhInitializer) {
		this.viewFactory = viewFactory;
		this.itemVhFactory = itemVhFactory;
		this.vhInitializer = vhInitializer;
	}

	public CompositeInitVhFactory(@NonNull CompositeVhFactory<? extends VH> compositeVhFactory, @NonNull VhInitializer<? super VH> vhInitializer) {
		this.viewFactory = compositeVhFactory.viewFactory;
		this.itemVhFactory = compositeVhFactory.itemVhFactory;
		this.vhInitializer = vhInitializer;
	}

	@SuppressWarnings("unchecked")
	public CompositeInitVhFactory(@NonNull CompositeInitVhFactory<? extends VH> compositeVhFactory, @NonNull VhInitializer<? super VH> vhInitializer) {
		this.viewFactory = compositeVhFactory.viewFactory;
		this.itemVhFactory = compositeVhFactory.itemVhFactory;
		this.vhInitializer = VhInitializers.make((VhInitializer) compositeVhFactory.vhInitializer, vhInitializer);
	}

	public VH createViewHolder(ViewGroup parent) {
		VH vh = itemVhFactory.createViewHolder(viewFactory.createView(parent));
		vhInitializer.initViewHolder(vh);
		return vh;
	}
}

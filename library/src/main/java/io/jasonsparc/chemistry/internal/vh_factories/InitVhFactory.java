package io.jasonsparc.chemistry.internal.vh_factories;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

import io.jasonsparc.chemistry.VhFactory;
import io.jasonsparc.chemistry.VhInitializer;
import io.jasonsparc.chemistry.util.VhInitializers;

/**
 * Created by Jason on 31/07/2016.
 */
public class InitVhFactory<VH extends ViewHolder> implements VhFactory<VH> {
	@NonNull final VhFactory<? extends VH> vhFactory;
	@NonNull final VhInitializer<? super VH> vhInitializer;

	@SuppressWarnings("unchecked")
	public InitVhFactory(@NonNull VhFactory<? extends VH> vhFactory, @NonNull VhInitializer<? super VH> vhInitializer) {
		if (vhFactory instanceof InitVhFactory<?>) {
			InitVhFactory initVhFactory = (InitVhFactory) vhFactory;
			vhFactory = initVhFactory.vhFactory;
			vhInitializer = VhInitializers.make(initVhFactory.vhInitializer, vhInitializer);
		}
		this.vhFactory = vhFactory;
		this.vhInitializer = vhInitializer;
	}

	@Override
	public VH createViewHolder(ViewGroup parent) {
		VH vh = vhFactory.createViewHolder(parent);
		vhInitializer.initViewHolder(vh);
		return vh;
	}
}

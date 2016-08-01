package io.jasonsparc.chemistry.internal.vh_factories;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

import io.jasonsparc.chemistry.util.VhFactory;
import io.jasonsparc.chemistry.util.VhInitializer;

/**
 * Created by Jason on 31/07/2016.
 */
public class InitArrayVhFactory<VH extends ViewHolder> implements VhFactory<VH> {
	@NonNull final VhFactory<? extends VH> vhFactory;
	@NonNull final VhInitializer<? super VH>[] vhInitializers;

	public InitArrayVhFactory(@NonNull VhFactory<? extends VH> vhFactory, @NonNull VhInitializer<? super VH>[] vhInitializers) {
		this.vhFactory = vhFactory;
		this.vhInitializers = vhInitializers;
	}

	@Override
	public VH createViewHolder(View itemView) {
		VH vh = vhFactory.createViewHolder(itemView);
		for (VhInitializer<? super VH> vhInitializer : vhInitializers) {
			vhInitializer.initViewHolder(vh);
		}
		return vh;
	}
}

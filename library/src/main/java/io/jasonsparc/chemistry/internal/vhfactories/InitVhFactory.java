package io.jasonsparc.chemistry.internal.vhfactories;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

import io.jasonsparc.chemistry.util.VhFactory;
import io.jasonsparc.chemistry.util.VhInitializer;

/**
 * Created by Jason on 31/07/2016.
 */
public class InitVhFactory<VH extends ViewHolder> implements VhFactory<VH> {
	@NonNull final VhFactory<? extends VH> vhFactory;
	@NonNull final VhInitializer<? super VH> vhInitializer;

	public InitVhFactory(@NonNull VhFactory<? extends VH> vhFactory, @NonNull VhInitializer<? super VH> vhInitializer) {
		this.vhFactory = vhFactory;
		this.vhInitializer = vhInitializer;
	}

	@Override
	public VH createViewHolder(View itemView) {
		VH vh = vhFactory.createViewHolder(itemView);
		vhInitializer.initViewHolder(vh);
		return vh;
	}
}

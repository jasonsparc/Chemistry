package io.jasonsparc.chemistry.internal.vh_initializers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;

import io.jasonsparc.chemistry.util.VhInitializer;

/**
 * Created by jason on 01/08/2016.
 */
public final class ArrayVhInitializer<VH extends ViewHolder> implements VhInitializer<VH> {
	@NonNull final VhInitializer<? super VH>[] vhInitializers;

	public ArrayVhInitializer(@NonNull VhInitializer<? super VH>[] vhInitializers) {
		this.vhInitializers = vhInitializers;
	}

	@Override
	public void initViewHolder(VH holder) {
		for (VhInitializer<? super VH> vhInitializer : vhInitializers) {
			vhInitializer.initViewHolder(holder);
		}
	}
}

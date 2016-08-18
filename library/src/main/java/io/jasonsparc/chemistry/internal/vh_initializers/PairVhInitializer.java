package io.jasonsparc.chemistry.internal.vh_initializers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;

import io.jasonsparc.chemistry.VhInitializer;

/**
 * Created by jason on 01/08/2016.
 */
public class PairVhInitializer<VH extends ViewHolder> implements VhInitializer<VH> {
	@NonNull final VhInitializer<? super VH> first;
	@NonNull final VhInitializer<? super VH> second;

	public PairVhInitializer(@NonNull VhInitializer<? super VH> first, @NonNull VhInitializer<? super VH> second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public void initViewHolder(VH holder) {
		first.initViewHolder(holder);
		second.initViewHolder(holder);
	}
}

package io.jasonsparc.chemistry.internal.vh_factories;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

import io.jasonsparc.chemistry.util.VhInitializer;

/**
 * Created by Jason on 31/07/2016.
 */
public class InitReflectiveVhFactory<VH extends ViewHolder> extends ReflectiveVhFactory<VH> {
	@NonNull final VhInitializer<? super VH> vhInitializer;

	public InitReflectiveVhFactory(@NonNull Class<? extends VH> vhClass, @NonNull VhInitializer<? super VH> vhInitializer) {
		super(vhClass);
		this.vhInitializer = vhInitializer;
	}

	@Override
	public VH createViewHolder(View itemView) {
		VH vh = super.createViewHolder(itemView);
		vhInitializer.initViewHolder(vh);
		return vh;
	}
}

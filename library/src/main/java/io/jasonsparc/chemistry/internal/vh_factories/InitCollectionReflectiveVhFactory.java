package io.jasonsparc.chemistry.internal.vh_factories;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

import java.util.Collection;

import io.jasonsparc.chemistry.util.VhInitializer;

/**
 * Created by Jason on 31/07/2016.
 */
public class InitCollectionReflectiveVhFactory<VH extends ViewHolder> extends ReflectiveVhFactory<VH> {
	@NonNull final Collection<? extends VhInitializer<? super VH>> vhInitializers;

	public InitCollectionReflectiveVhFactory(@NonNull Class<? extends VH> vhClass, @NonNull Collection<? extends VhInitializer<? super VH>> vhInitializers) {
		super(vhClass);
		this.vhInitializers = vhInitializers;
	}

	@Override
	public VH createViewHolder(View itemView) {
		VH vh = super.createViewHolder(itemView);
		for (VhInitializer<? super VH> vhInitializer : vhInitializers) {
			vhInitializer.initViewHolder(vh);
		}
		return vh;
	}
}

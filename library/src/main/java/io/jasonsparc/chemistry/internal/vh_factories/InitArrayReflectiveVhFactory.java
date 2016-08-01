package io.jasonsparc.chemistry.internal.vh_factories;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

import io.jasonsparc.chemistry.util.VhInitializer;

/**
 * Created by Jason on 31/07/2016.
 */
public class InitArrayReflectiveVhFactory<VH extends ViewHolder> extends ReflectiveVhFactory<VH> {
	@NonNull final VhInitializer<? super VH>[] vhInitializers;

	public InitArrayReflectiveVhFactory(@NonNull Class<? extends VH> vhClass, @NonNull VhInitializer<? super VH>[] vhInitializers) {
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

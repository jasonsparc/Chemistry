package io.jasonsparc.chemistry.internal.predicates;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;

import io.jasonsparc.chemistry.BindPredicate;
import io.jasonsparc.chemistry.Flask;

/**
 * Created by jason on 11/07/2016.
 */
public class VhClassArrayBindPredicate<VH extends ViewHolder> implements BindPredicate<VH> {
	@NonNull final Class<? extends VH>[] vhClsz;

	public VhClassArrayBindPredicate(@NonNull Class<? extends VH>[] vhClsz) {
		this.vhClsz = vhClsz;
	}

	@Override
	public boolean checkBind(@NonNull Flask<?> flask, @NonNull Class<? extends ViewHolder> vhCls) {
		for (Class<?> chkCls : vhClsz) {
			if (chkCls.isAssignableFrom(vhCls)) {
				return true;
			}
		}
		return false;
	}
}

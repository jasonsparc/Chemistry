package io.jasonsparc.chemistry.util;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Jason on 31/07/2016.
 */
public interface VhInitializer<VH extends RecyclerView.ViewHolder> {

	void initViewHolder(VH holder);
}

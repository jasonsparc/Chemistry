package io.jasonsparc.chemistry;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by jason on 07/07/2016.
 */
public interface VhFactory<VH extends RecyclerView.ViewHolder> {

	VH createViewHolder(ViewGroup parent);
}

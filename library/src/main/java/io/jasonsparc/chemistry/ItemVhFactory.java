package io.jasonsparc.chemistry;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by jason on 07/07/2016.
 */
public interface ItemVhFactory<VH extends RecyclerView.ViewHolder> {

	VH createViewHolder(View itemView);
}

package io.jasonsparc.chemistry;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by jason on 28/06/2016.
 */
public interface ViewHolderFactory<VH extends RecyclerView.ViewHolder> {

	VH createViewHolder(View itemView);
}

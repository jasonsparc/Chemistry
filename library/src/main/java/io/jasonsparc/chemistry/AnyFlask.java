package io.jasonsparc.chemistry;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by jason on 07/07/2016.
 */
public final class AnyFlask implements Flask<RecyclerView.ViewHolder> {
	public static final AnyFlask INSTANCE = new AnyFlask();

	private AnyFlask() { }

	@Override
	public final int getViewType() {
		throw new UnsupportedOperationException();
	}

	@NonNull
	@Override
	public final RecyclerView.ViewHolder createViewHolder(ViewGroup parent) {
		throw new UnsupportedOperationException();
	}
}

package io.jasonsparc.chemistry;

import android.support.annotation.AnyRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Defines the mapping between an item view type and a view holder, as well as the factory for that
 * specific view holder.
 * <p>
 * The item view type must be a unique resource id. {@link Chemistry} and {@link ChemistryAdapter}
 * are designed to throw {@link InvalidViewType} if the item view type is not a valid resource
 * identifier.
 * <p>
 * Created by jason on 07/07/2016.
 */
public interface Flask<VH extends RecyclerView.ViewHolder> {
	AnyFlask ANY = AnyFlask.INSTANCE;

	int MIN_RES_ID = 0x0100_0000;

	@IntRange(from = MIN_RES_ID)
	@AnyRes
	int getViewType();

	@NonNull
	VH createViewHolder(ViewGroup parent);
}

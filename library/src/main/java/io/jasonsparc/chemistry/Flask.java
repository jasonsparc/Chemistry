package io.jasonsparc.chemistry;

import android.support.annotation.AnyRes;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Defines the mapping between an item view type and a view holder. Acts as an object
 * representation of a view type as well as the factory for its view holder.
 * <p>
 * Created by jason on 07/07/2016.
 *
 * @see RecyclerView.Adapter#getItemViewType(int)
 * @see RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)
 */
public interface Flask<VH extends RecyclerView.ViewHolder> {
	/**
	 * Same as {@link AnyFlask#INSTANCE}.
	 */
	AnyFlask ANY = AnyFlask.INSTANCE;

	/**
	 * Provides the view type of the items associated with this {@link Flask}. The value returned
	 * here must be a resource identifier (i.e. a layout resource, a string resource, an id
	 * resource, etc.) to ensure its uniqueness. The returned resource identifier can either belong
	 * to the application or a framework-specific resource identifier.
	 * <p>
	 * Both {@link Chemistry} and {@link ChemistryAdapter} are designed to throw
	 * {@link InvalidViewType} if the item view type is not a valid resource identifier.
	 *
	 * @see RecyclerView.Adapter#getItemViewType(int)
	 */
	@ViewTypeId
	@AnyRes
	int getViewType();

	VH createViewHolder(ViewGroup parent);
}

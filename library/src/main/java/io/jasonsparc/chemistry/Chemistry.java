package io.jasonsparc.chemistry;

import android.support.annotation.AnyRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * TODO Docs
 *
 * Created by jason on 07/07/2016.
 */
public abstract class Chemistry<Item, VH extends ViewHolder> implements IdSelector<Item>, TypeSelector<Item> {

	public long getItemId(Item item) {
		return RecyclerView.NO_ID;
	}

	@ViewType
	@AnyRes
	public abstract int getItemViewType(Item item);

	public abstract VhFactory<? extends VH> getVhFactory(Item item);

	public abstract ItemBinder<? super Item, ? super VH> getItemBinder(Item item);
}

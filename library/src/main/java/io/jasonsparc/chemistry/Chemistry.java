package io.jasonsparc.chemistry;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * TODO Implement
 *
 * Created by jason on 07/07/2016.
 */
public abstract class Chemistry {

	public abstract <Item, VH extends ViewHolder> Chemistry bind(@Nullable Class<? extends Item> itemClass, @NonNull BindPredicate<? extends VH> bindPredicate, @Nullable ItemBinder<? super Item, ? super VH> itemBinder);
}

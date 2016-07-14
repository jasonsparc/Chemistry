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

	public <Item> Chemistry identify(@Nullable Class<? extends Item> itemClass, @Nullable IdSelector<? super Item> idSelector) {
		return null;
	}

	/**
	 * TODO Improve Docs
	 * <br>- associates a flask to the specified item class.
	 * <br>- a null item class associates a null item to a flask selector.
	 * <br>- a null flask selector removes any associated flask selectors for the specified item
	 * class.
	 *
	 * @param itemClass
	 * @param flaskSelector
	 * @param <Item>
	 * @return
	 */
	public <Item> Chemistry flask(@Nullable Class<? extends Item> itemClass, @Nullable FlaskSelector<? super Item> flaskSelector) {
		return null;
	}

	/**
	 * TODO More Docs
	 * <br>- a null item class provides binding to a null item.
	 * <br>- a null item binder removes any item binders for the specified item class.
	 *
	 * @param itemClass
	 * @param bindPredicate
	 * @param itemBinder
	 * @param <Item>
	 * @param <VH>
	 * @return
	 */
	public <Item, VH extends ViewHolder> Chemistry bind(@Nullable Class<? extends Item> itemClass, @NonNull BindPredicate<? extends VH> bindPredicate, @Nullable ItemBinder<? super Item, ? super VH> itemBinder) {
		return null;
	}
}

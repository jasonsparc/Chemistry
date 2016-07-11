package io.jasonsparc.chemistry;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * TODO Docs
 *
 * @param <VH>
 */
public interface BindPredicate<VH extends ViewHolder> {

	/**
	 * TODO Improve Docs
	 * <p>
	 * CONTRACT: this method should return {@code false} if the given {@link ViewHolder ViewHolder}
	 * class does not match the type parameter specified by this {@link BindPredicate} instance.
	 *
	 * @param flask
	 * @param vhCls
	 * @return
	 */
	boolean checkBind(@NonNull Flask<?> flask, @NonNull Class<? extends ViewHolder> vhCls);
}

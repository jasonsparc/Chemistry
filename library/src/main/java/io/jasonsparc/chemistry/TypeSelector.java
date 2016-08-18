package io.jasonsparc.chemistry;

import android.support.annotation.AnyRes;

/**
 * Created by jason on 12/07/2016.
 */
public interface TypeSelector<Item> {

	@ViewType
	@AnyRes
	int getItemViewType(Item item);
}

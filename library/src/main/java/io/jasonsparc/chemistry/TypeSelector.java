package io.jasonsparc.chemistry;

import android.support.annotation.AnyRes;

/**
 * Created by jason on 08/07/2016.
 */
public interface TypeSelector<Item> {

	@ViewTypeId
	@AnyRes
	int getViewType(Item item);
}

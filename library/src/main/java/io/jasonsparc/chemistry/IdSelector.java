package io.jasonsparc.chemistry;

import android.support.v7.widget.RecyclerView;

/**
 * Created by jason on 08/07/2016.
 */
public interface IdSelector<Item> {
	long NO_ID = RecyclerView.NO_ID;

	long getItemId(Item item);
}

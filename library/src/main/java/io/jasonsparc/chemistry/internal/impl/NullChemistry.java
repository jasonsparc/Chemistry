package io.jasonsparc.chemistry.internal.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ViewHolder;

import io.jasonsparc.chemistry.Flask;
import io.jasonsparc.chemistry.FlaskSelector;
import io.jasonsparc.chemistry.IdSelector;
import io.jasonsparc.chemistry.ItemBinder;

/**
 * Created by jason on 15/07/2016.
 */
public final class NullChemistry extends IdentityChemistry {
	public static final NullChemistry INSTANCE = new NullChemistry();

	private NullChemistry() { }

	@Nullable
	public <Item> FlaskSelector<? super Item> findFlaskSelector(@NonNull Class<? extends Item> itemClass, int flags) {
		return null;
	}

	@Nullable
	public <Item, VH extends ViewHolder> ItemBinder<? super Item, ? super VH> findItemBinder(@NonNull Class<? extends Item> itemClass, @NonNull Class<? extends VH> vhClass, Flask<? extends VH> flask, int flags) {
		return null;
	}

	@Nullable
	public <Item> IdSelector<? super Item> findIdSelector(@NonNull Class<? extends Item> itemClass, int flags) {
		return null;
	}
}

package io.jasonsparc.chemistry;

import android.support.annotation.AnyRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;

import io.jasonsparc.chemistry.util.InflateUtils;

/**
 * TODO Docs
 *
 * Created by jason on 07/07/2016.
 */
public abstract class Chemistry<Item> implements IdSelector<Item>, TypeSelector<Item> {

	public long getItemId(Item item) {
		return NO_ID;
	}

	@ViewType
	@AnyRes
	public abstract int getItemViewType(Item item);

	public abstract VhFactory<?> getVhFactory(Item item);

	public abstract ItemBinder<? super Item, ?> getItemBinder(Item item);


	// Utilities

	public static View inflate(@NonNull ViewGroup parent, @LayoutRes int layoutRes) {
		return InflateUtils.inflate(parent, layoutRes);
	}

	// Factories

	public static <Item> BasicChemistry.Preperator<Item> make() {
		return new BasicChemistry.Preperator<>();
	}

	public static <Item> BasicChemistry.Preperator<Item> make(@ViewType @AnyRes int viewType) {
		return new BasicChemistry.Preperator<Item>().useViewType(viewType);
	}

	public static <Item, VH extends ViewHolder> BasicChemistry.Boiler<Item, VH> make(@NonNull BasicChemistry<? super Item, VH> base) {
		return new BasicChemistry.Boiler<>(base);
	}

	public static <Item, VH extends ViewHolder> BasicChemistry.Boiler<Item, VH> make(@ViewType @AnyRes int viewType, @NonNull BasicChemistry<? super Item, VH> base) {
		return new BasicChemistry.Boiler<>(base).useViewType(viewType);
	}

	public static <Item, VH extends ViewHolder> BasicChemistry.Boiler<Item, VH> make(@NonNull BasicChemistry.Transformer<? super Item, ? super VH> transformer) {
		return new BasicChemistry.Boiler<Item, VH>().compose(transformer);
	}
}

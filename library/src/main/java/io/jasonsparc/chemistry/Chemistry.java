package io.jasonsparc.chemistry;

import android.support.annotation.AnyRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;

import io.jasonsparc.chemistry.BasicChemistry.Boiler;
import io.jasonsparc.chemistry.util.InflateUtils;
import io.jasonsparc.chemistry.util.VhFactories;

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

	public static <Item, VH extends ViewHolder> BasicChemistry.Boiler<Item, VH> make(@ViewType @AnyRes int viewType, @NonNull VhFactory<? extends VH> vhFactory) {
		return new BasicChemistry.Boiler<>(viewType, vhFactory);
	}

	public static <Item, VH extends ViewHolder> BasicChemistry.Boiler<Item, VH> make(@ViewType @AnyRes int viewType, @NonNull ViewFactory viewFactory, @NonNull ItemVhFactory<? extends VH> itemVhFactory) {
		return new BasicChemistry.Boiler<>(viewType, VhFactories.make(viewFactory, itemVhFactory));
	}

	public static <Item, VH extends ViewHolder> BasicChemistry.Boiler<Item, VH> make(@ViewType @AnyRes int viewType, @NonNull ViewFactory viewFactory, @NonNull Class<? extends VH> vhClass) {
		return new BasicChemistry.Boiler<>(viewType, VhFactories.make(viewFactory, vhClass));
	}

	// Auto-inflate Factories

	public static <Item, VH extends ViewHolder> BasicChemistry.Boiler<Item, VH> make(@ViewType @AnyRes int viewType, @LayoutRes int itemLayout, @NonNull ItemVhFactory<? extends VH> itemVhFactory) {
		return new BasicChemistry.Boiler<>(viewType, VhFactories.make(itemLayout, itemVhFactory));
	}

	public static <Item, VH extends ViewHolder> BasicChemistry.Boiler<Item, VH> make(@ViewType @AnyRes int viewType, @LayoutRes int itemLayout, @NonNull Class<? extends VH> vhClass) {
		return new BasicChemistry.Boiler<>(viewType, VhFactories.make(itemLayout, vhClass));
	}

	public static <Item, VH extends ViewHolder> BasicChemistry.Boiler<Item, VH> make(@LayoutRes int itemLayout, @NonNull ItemVhFactory<? extends VH> itemVhFactory) {
		return new BasicChemistry.Boiler<>(itemLayout, VhFactories.make(itemLayout, itemVhFactory));
	}

	public static <Item, VH extends ViewHolder> BasicChemistry.Boiler<Item, VH> make(@LayoutRes int itemLayout, @NonNull Class<? extends VH> vhClass) {
		return new BasicChemistry.Boiler<>(itemLayout, VhFactories.make(itemLayout, vhClass));
	}

	// Base-Extending Factories

	public static <Item, VH extends ViewHolder> BasicChemistry.Boiler<Item, VH> make(@NonNull BasicChemistry<? super Item, VH> base) {
		return new Boiler<>(base);
	}

	public static <Item, VH extends ViewHolder> BasicChemistry.Boiler<Item, VH> make(@ViewType @AnyRes int viewType, @NonNull BasicChemistry<? super Item, VH> base) {
		final Boiler<Item, VH> boiler = new Boiler<>(base);
		return boiler.setViewType(viewType);
	}


	// Factories - with item class specifiers

	public static <Item, VH extends ViewHolder> BasicChemistry.Boiler<Item, VH> make(@NonNull Class<? extends Item> itemClass, @ViewType @AnyRes int viewType, @NonNull VhFactory<? extends VH> vhFactory) {
		return make(viewType, vhFactory);
	}

	public static <Item, VH extends ViewHolder> BasicChemistry.Boiler<Item, VH> make(@NonNull Class<? extends Item> itemClass, @ViewType @AnyRes int viewType, @NonNull ViewFactory viewFactory, @NonNull ItemVhFactory<? extends VH> itemVhFactory) {
		return make(viewType, viewFactory, itemVhFactory);
	}

	public static <Item, VH extends ViewHolder> BasicChemistry.Boiler<Item, VH> make(@NonNull Class<? extends Item> itemClass, @ViewType @AnyRes int viewType, @NonNull ViewFactory viewFactory, @NonNull Class<? extends VH> vhClass) {
		return make(viewType, viewFactory, vhClass);
	}

	// Auto-inflate Factories - with item class specifiers

	public static <Item, VH extends ViewHolder> BasicChemistry.Boiler<Item, VH> make(@NonNull Class<? extends Item> itemClass, @ViewType @AnyRes int viewType, @LayoutRes int itemLayout, @NonNull ItemVhFactory<? extends VH> itemVhFactory) {
		return make(viewType, itemLayout, itemVhFactory);
	}

	public static <Item, VH extends ViewHolder> BasicChemistry.Boiler<Item, VH> make(@NonNull Class<? extends Item> itemClass, @ViewType @AnyRes int viewType, @LayoutRes int itemLayout, @NonNull Class<? extends VH> vhClass) {
		return make(viewType, itemLayout, vhClass);
	}

	public static <Item, VH extends ViewHolder> BasicChemistry.Boiler<Item, VH> make(@NonNull Class<? extends Item> itemClass, @LayoutRes int itemLayout, @NonNull ItemVhFactory<? extends VH> itemVhFactory) {
		return make(itemLayout, itemVhFactory);
	}

	public static <Item, VH extends ViewHolder> BasicChemistry.Boiler<Item, VH> make(@NonNull Class<? extends Item> itemClass, @LayoutRes int itemLayout, @NonNull Class<? extends VH> vhClass) {
		return make(itemLayout, vhClass);
	}

	// Base-Extending Factories - with item class specifiers

	public static <Item, VH extends ViewHolder> BasicChemistry.Boiler<Item, VH> make(@NonNull Class<? extends Item> itemClass, @NonNull BasicChemistry<? super Item, VH> base) {
		return make(base);
	}

	public static <Item, VH extends ViewHolder> BasicChemistry.Boiler<Item, VH> make(@NonNull Class<? extends Item> itemClass, @ViewType @AnyRes int viewType, @NonNull BasicChemistry<? super Item, VH> base) {
		return make(viewType, base);
	}
}

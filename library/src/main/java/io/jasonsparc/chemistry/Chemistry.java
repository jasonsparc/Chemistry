package io.jasonsparc.chemistry;

import android.support.annotation.AnyRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;

import io.jasonsparc.chemistry.ChemistrySet.Predicate;
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

	public static <Item> BasicChemistry.Preperator<Item> compose() {
		return new BasicChemistry.Preperator<>();
	}

	public static <Item> BasicChemistry.Preperator<Item> compose(@ViewType @AnyRes int viewType) {
		return new BasicChemistry.Preperator<Item>().useViewType(viewType);
	}

	public static <Item, VH extends ViewHolder> BasicChemistry.Boiler<Item, VH> compose(@NonNull BasicChemistry<? super Item, VH> base) {
		return new BasicChemistry.Boiler<>(base);
	}

	public static <Item, VH extends ViewHolder> BasicChemistry.Boiler<Item, VH> compose(@ViewType @AnyRes int viewType, @NonNull BasicChemistry<? super Item, VH> base) {
		return new BasicChemistry.Boiler<>(base).useViewType(viewType);
	}

	public static <Item, VH extends ViewHolder> BasicChemistry.Boiler<Item, VH> compose(@NonNull BasicChemistry.Transformer<? super Item, VH> transformer) {
		return new BasicChemistry.Boiler<Item, VH>().compose(transformer);
	}


	public static <Item> ChemistrySet.ClassBoiler<Item> select() {
		return new ChemistrySet.ClassBoiler<>();
	}

	public static <Item, K> ChemistrySet.Boiler<Item, K> select(@NonNull ChemistrySet.Selector<? super Item, ? extends K> caseSelector) {
		return new ChemistrySet.Boiler<>(caseSelector);
	}

	public static <Item> ChemistrySet.TypeBoiler<Item> typeSelect(@NonNull TypeSelector<? super Item> typeSelector) {
		return new ChemistrySet.TypeBoiler<>(typeSelector);
	}

	public static <Item> ChemistrySet<Item> select(@NonNull Predicate<? super Item> condition, @NonNull Chemistry<? super Item> consequent) {
		return new ChemistrySet.PredicateChemistrySet<>(condition, consequent, null);
	}

	public static <Item> ChemistrySet<Item> select(@NonNull Predicate<? super Item> condition, @Nullable Chemistry<? super Item> consequent, @Nullable Chemistry<? super Item> alternative) {
		return new ChemistrySet.PredicateChemistrySet<>(condition, consequent, alternative);
	}

	@SafeVarargs
	public static <Item> ChemistrySet<Item> select(@NonNull ChemistrySet<? super Item>... testCases) {
		return new ChemistrySet.ArrayChemistrySet<>(testCases);
	}

	public static <Item> ChemistrySet<Item> select(@NonNull Collection<? extends ChemistrySet<? super Item>> testCases) {
		return new ChemistrySet.ArrayChemistrySet<>(ChemistrySet.array(testCases));
	}
}

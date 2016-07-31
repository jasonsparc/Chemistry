package io.jasonsparc.chemistry.internal.flaskselectors;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collection;

import io.jasonsparc.chemistry.Flask;
import io.jasonsparc.chemistry.FlaskSelector;
import io.jasonsparc.chemistry.util.Flasks;
import io.jasonsparc.chemistry.util.TypeSelector;

/**
 * Created by jason on 13/07/2016.
 */
public class CompositeFlaskSelector<Item> extends AbsSparseFlaskSwitch<Item> implements FlaskSelector<Item> {
	@NonNull final TypeSelector<? super Item> typeSelector;

	public CompositeFlaskSelector(@NonNull TypeSelector<? super Item> typeSelector, @NonNull Flask<?>[] flaskSelections, @Nullable Flask<?> defaultCase) {
		super(Flasks.sparseArray(flaskSelections), defaultCase);
		this.typeSelector = typeSelector;
	}

	public CompositeFlaskSelector(@NonNull TypeSelector<? super Item> typeSelector, @NonNull Collection<? extends Flask<?>> flaskSelections, @Nullable Flask<?> defaultCase) {
		super(Flasks.sparseArray(flaskSelections), defaultCase);
		this.typeSelector = typeSelector;
	}

	@Override
	public int getIntCaseKey(Item item) {
		return typeSelector.getViewType(item);
	}

	@Override
	public Boiler<Item> newBoiler() {
		throw new UnsupportedOperationException();
	}
}

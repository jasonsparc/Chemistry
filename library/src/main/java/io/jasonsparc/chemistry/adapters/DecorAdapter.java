package io.jasonsparc.chemistry.adapters;

import android.support.annotation.NonNull;

import io.jasonsparc.chemistry.Chemistry;
import io.jasonsparc.chemistry.ChemistryAdapter;

/**
 * Created by jason on 26/07/2016.
 */
public abstract class DecorAdapter<DecorItem, RawItem> extends ChemistryAdapter<Object> {
	@NonNull final ChemistryAdapter<? extends RawItem> base;

	public DecorAdapter(@NonNull ChemistryAdapter<? extends RawItem> base) {
		super(base.getChemistry());
		this.base = base;
	}

	public DecorAdapter(@NonNull ChemistryAdapter<? extends RawItem> base, @NonNull Chemistry override) {
		super(override);
		this.base = base;
	}

	public DecorAdapter(@NonNull ChemistryAdapter<? extends RawItem> base, @NonNull Chemistry.Transformer<?> override) {
		super(base.getChemistry().compose(override));
		this.base = base;
	}

	public abstract DecorItem getDecorItem(int decorIndex);

	public abstract int binarySearchDecor(int globalPosition);

	public ChemistryAdapter<? extends RawItem> getRawAdapter() {
		return base;
	}

	public int getRawItemCount() {
		return base.getItemCount();
	}

	public RawItem getRawItem(int position) {
		return base.getItem(position);
	}

	@Override
	public int getItemCount() {
		final int baseItemCount = getRawItemCount();
		// Ensures that the last index equals `baseItemCount` is also included.
		return baseItemCount + Math.abs(binarySearchDecor(baseItemCount) + 1);
	}

	@Override
	public Object getItem(int position) {
		final int decorIndex = binarySearchDecor(position);

		return decorIndex < 0
				? getRawItem(position - ~decorIndex)
				: getDecorItem(decorIndex);
	}
}

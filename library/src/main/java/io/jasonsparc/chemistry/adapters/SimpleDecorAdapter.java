package io.jasonsparc.chemistry.adapters;

import android.support.annotation.NonNull;

import io.jasonsparc.chemistry.Chemistry;
import io.jasonsparc.chemistry.Chemistry.Transformer;
import io.jasonsparc.chemistry.ChemistryAdapter;

/**
 * Created by jason on 27/07/2016.
 */
public class SimpleDecorAdapter<DecorItem, RawItem> extends DecorAdapter<DecorItem, RawItem> {

	public static final int ANCHOR_START = 0b0000_0001;
	public static final int ANCHOR_END = 0b0000_0010;
	public static final int ANCHOR_POSITION = 0b0000_0011;

	public static final int MOVABLE = 0b0000_0100;
	public static final int REMOVEABLE = 0b0000_1000;
	public static final int GLOBAL_POSITION = 0b0001_0000;

	public SimpleDecorAdapter(@NonNull ChemistryAdapter<? extends RawItem> base) {
		super(base);
	}

	public SimpleDecorAdapter(@NonNull ChemistryAdapter<? extends RawItem> base, @NonNull Chemistry override) {
		super(base, override);
	}

	public SimpleDecorAdapter(@NonNull ChemistryAdapter<? extends RawItem> base, @NonNull Transformer<?> override) {
		super(base, override);
	}

	@Override
	public DecorItem getDecorItem(int decorIndex) {
		return null;
	}

	@Override
	public int binarySearchDecor(int globalPosition) {
		return 0;
	}
}

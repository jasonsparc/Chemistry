package io.jasonsparc.chemistry.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;

import java.util.Collection;
import java.util.List;

import io.jasonsparc.chemistry.VhInitializer;
import io.jasonsparc.chemistry.internal.NullChemistry;
import io.jasonsparc.chemistry.internal.vh_initializers.ArrayVhInitializer;
import io.jasonsparc.chemistry.internal.vh_initializers.PairVhInitializer;

/**
 * Created by jason on 01/08/2016.
 */
@UtilityClass
public final class VhInitializers {

	// Factories

	@SuppressWarnings("unchecked")
	public static <VH extends ViewHolder> VhInitializer<VH> make(@NonNull VhInitializer<? super VH> vhInitializer) {
		return (VhInitializer<VH>) vhInitializer;
	}

	public static <VH extends ViewHolder> VhInitializer<VH> make(@NonNull VhInitializer<? super VH> first, @NonNull VhInitializer<? super VH> second) {
		return new PairVhInitializer<>(first, second);
	}

	@SafeVarargs
	public static <VH extends ViewHolder> VhInitializer<VH> make(@NonNull VhInitializer<? super VH>... vhInitializers) {
		switch (vhInitializers.length) {
			case 1:
				return make(vhInitializers[0]);
			case 2:
				return new PairVhInitializer<>(vhInitializers[0], vhInitializers[1]);
			default:
				return new ArrayVhInitializer<>(vhInitializers);
		}
	}

	public static <VH extends ViewHolder> VhInitializer<VH> make(@NonNull List<? extends VhInitializer<? super VH>> vhInitializers) {
		switch (vhInitializers.size()) {
			case 1:
				return make(vhInitializers.get(0));
			case 2:
				return new PairVhInitializer<>(vhInitializers.get(0), vhInitializers.get(1));
			default:
				return new ArrayVhInitializer<>(array(vhInitializers));
		}
	}

	@SuppressWarnings("unchecked")
	public static <VH extends ViewHolder> VhInitializer<VH> make(@NonNull Collection<? extends VhInitializer<? super VH>> vhInitializers) {
		return vhInitializers instanceof List<?> ? make((List) vhInitializers) : make(array(vhInitializers));
	}

	// Utilities

	public static <VH extends ViewHolder> VhInitializer<VH> empty() {
		return NullChemistry.get();
	}

	@SafeVarargs
	public static <VHI extends VhInitializer<? extends ViewHolder>> VHI[] array(@NonNull VHI... vhInitializers) {
		return vhInitializers;
	}

	@SuppressWarnings("unchecked")
	public static <VH extends ViewHolder> VhInitializer<? super VH>[] array(@NonNull Collection<? extends VhInitializer<? super VH>> vhInitializers) {
		return vhInitializers.toArray(new VhInitializer[vhInitializers.size()]);
	}
}

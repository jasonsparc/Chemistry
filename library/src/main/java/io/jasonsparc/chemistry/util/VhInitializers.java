package io.jasonsparc.chemistry.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;

import java.util.Collection;

import io.jasonsparc.chemistry.internal.vh_initializers.ArrayVhInitializer;
import io.jasonsparc.chemistry.internal.vh_initializers.PairVhInitializer;

/**
 * Created by jason on 01/08/2016.
 */
@UtilityClass
public class VhInitializers {

	@SuppressWarnings("unchecked")
	public static <VH extends ViewHolder> VhInitializer<VH> make(@NonNull VhInitializer<? super VH> vhInitializer) {
		return (VhInitializer<VH>) vhInitializer;
	}

	public static <VH extends ViewHolder> VhInitializer<VH> make(@NonNull VhInitializer<? super VH> first, @NonNull VhInitializer<? super VH> second) {
		return new PairVhInitializer<>(first, second);
	}

	@SafeVarargs
	public static <VH extends ViewHolder> VhInitializer<VH> make(@NonNull VhInitializer<? super VH>... vhInitializers) {
		return vhInitializers.length == 2
				? new PairVhInitializer<>(vhInitializers[0], vhInitializers[1])
				: new ArrayVhInitializer<>(vhInitializers);
	}

	public static <VH extends ViewHolder> VhInitializer<VH> make(@NonNull Collection<? extends VhInitializer<? super VH>> vhInitializers) {
		return make(array(vhInitializers));
	}

	// Utilities

	@SafeVarargs
	public static <VHI extends VhInitializer<? extends ViewHolder>> VHI[] array(@NonNull VHI... vhInitializers) {
		return vhInitializers;
	}

	@SuppressWarnings("unchecked")
	public static <VH extends ViewHolder> VhInitializer<? super VH>[] array(@NonNull Collection<? extends VhInitializer<? super VH>> vhInitializers) {
		return vhInitializers.toArray(new VhInitializer[vhInitializers.size()]);
	}
}

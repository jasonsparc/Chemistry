package io.jasonsparc.chemistry.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;

import java.util.Collection;

import io.jasonsparc.chemistry.internal.vh_factories.InitVhFactory;
import io.jasonsparc.chemistry.internal.vh_factories.ReflectiveVhFactory;

/**
 * TODO Docs
 *
 * Created by Jason on 31/07/2016.
 */
@UtilityClass
public final class VhFactories {

	public static <VH extends ViewHolder> VhFactory<VH> make(@NonNull VhFactory<? extends VH> vhFactory, @NonNull VhInitializer<? super VH> vhInitializer) {
		return new InitVhFactory<>(vhFactory, vhInitializer);
	}

	public static <VH extends ViewHolder> VhFactory<VH> make(@NonNull VhFactory<? extends VH> vhFactory, @NonNull VhInitializer<? super VH> first, @NonNull VhInitializer<? super VH> second) {
		return new InitVhFactory<>(vhFactory, VhInitializers.make(first, second));
	}

	@SafeVarargs
	public static <VH extends ViewHolder> VhFactory<VH> make(@NonNull VhFactory<? extends VH> vhFactory, @NonNull VhInitializer<? super VH>... vhInitializers) {
		return new InitVhFactory<>(vhFactory, VhInitializers.make(vhInitializers));
	}

	public static <VH extends ViewHolder> VhFactory<VH> make(@NonNull VhFactory<? extends VH> vhFactory, @NonNull Collection<? extends VhInitializer<? super VH>> vhInitializers) {
		return new InitVhFactory<>(vhFactory, VhInitializers.make(vhInitializers));
	}

	public static <VH extends ViewHolder> VhFactory<VH> make(@NonNull Class<? extends VH> vhClass) {
		return new ReflectiveVhFactory<>(vhClass);
	}

	public static <VH extends ViewHolder> VhFactory<VH> make(@NonNull Class<? extends VH> vhClass, @NonNull VhInitializer<? super VH> vhInitializer) {
		return new InitVhFactory<>(make(vhClass), vhInitializer);
	}

	public static <VH extends ViewHolder> VhFactory<VH> make(@NonNull Class<? extends VH> vhClass, @NonNull VhInitializer<? super VH> first, @NonNull VhInitializer<? super VH> second) {
		return new InitVhFactory<>(make(vhClass), VhInitializers.make(first, second));
	}

	@SafeVarargs
	public static <VH extends ViewHolder> VhFactory<VH> make(@NonNull Class<? extends VH> vhClass, @NonNull VhInitializer<? super VH>... vhInitializers) {
		return new InitVhFactory<>(make(vhClass), VhInitializers.make(vhInitializers));
	}

	public static <VH extends ViewHolder> VhFactory<VH> make(@NonNull Class<? extends VH> vhClass, @NonNull Collection<? extends VhInitializer<? super VH>> vhInitializers) {
		return new InitVhFactory<>(make(vhClass), VhInitializers.make(vhInitializers));
	}
}

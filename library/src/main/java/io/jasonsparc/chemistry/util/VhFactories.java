package io.jasonsparc.chemistry.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;

import java.util.Collection;

import io.jasonsparc.chemistry.internal.vhfactories.InitArrayReflectiveVhFactory;
import io.jasonsparc.chemistry.internal.vhfactories.InitArrayVhFactory;
import io.jasonsparc.chemistry.internal.vhfactories.InitCollectionReflectiveVhFactory;
import io.jasonsparc.chemistry.internal.vhfactories.InitCollectionVhFactory;
import io.jasonsparc.chemistry.internal.vhfactories.InitReflectiveVhFactory;
import io.jasonsparc.chemistry.internal.vhfactories.InitVhFactory;
import io.jasonsparc.chemistry.internal.vhfactories.ReflectiveVhFactory;

/**
 * TODO Docs
 *
 * Created by Jason on 31/07/2016.
 */
public class VhFactories {

	public static <VH extends ViewHolder> VhFactory<VH> make(@NonNull VhFactory<? extends VH> vhFactory, VhInitializer<? super VH> vhInitializer) {
		return new InitVhFactory<>(vhFactory, vhInitializer);
	}

	@SafeVarargs
	public static <VH extends ViewHolder> VhFactory<VH> make(@NonNull VhFactory<? extends VH> vhFactory, VhInitializer<? super VH>... vhInitializers) {
		return new InitArrayVhFactory<>(vhFactory, vhInitializers);
	}

	public static <VH extends ViewHolder> VhFactory<VH> make(@NonNull VhFactory<? extends VH> vhFactory, Collection<? extends VhInitializer<? super VH>> vhInitializers) {
		return new InitCollectionVhFactory<>(vhFactory, vhInitializers);
	}

	public static <VH extends ViewHolder> VhFactory<VH> make(@NonNull Class<? extends VH> vhClass) {
		return new ReflectiveVhFactory<>(vhClass);
	}

	public static <VH extends ViewHolder> VhFactory<VH> make(@NonNull Class<? extends VH> vhClass, VhInitializer<? super VH> vhInitializer) {
		return new InitReflectiveVhFactory<>(vhClass, vhInitializer);
	}

	@SafeVarargs
	public static <VH extends ViewHolder> VhFactory<VH> make(@NonNull Class<? extends VH> vhClass, VhInitializer<? super VH>... vhInitializers) {
		return new InitArrayReflectiveVhFactory<>(vhClass, vhInitializers);
	}

	public static <VH extends ViewHolder> VhFactory<VH> make(@NonNull Class<? extends VH> vhClass, Collection<? extends VhInitializer<? super VH>> vhInitializers) {
		return new InitCollectionReflectiveVhFactory<>(vhClass, vhInitializers);
	}
}

package io.jasonsparc.chemistry.util;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;

import io.jasonsparc.chemistry.ItemVhFactory;
import io.jasonsparc.chemistry.VhFactory;
import io.jasonsparc.chemistry.VhInitializer;
import io.jasonsparc.chemistry.ViewFactory;
import io.jasonsparc.chemistry.internal.item_vh_factories.ReflectiveItemVhFactory;
import io.jasonsparc.chemistry.internal.vh_factories.CompositeVhFactory;
import io.jasonsparc.chemistry.internal.vh_factories.InitVhFactory;
import io.jasonsparc.chemistry.internal.view_factories.InflateViewFactory;

/**
 * Created by Jason on 31/07/2016.
 */
@UtilityClass
public final class VhFactories {

	// Factories

	@SuppressWarnings("unchecked")
	public static <VH extends ViewHolder> VhFactory<VH> make(@NonNull VhFactory<? extends VH> vhFactory) {
		return (VhFactory<VH>) vhFactory;
	}

	public static <VH extends ViewHolder> VhFactory<VH> make(@NonNull VhFactory<? extends VH> vhFactory, @NonNull VhInitializer<? super VH> vhInitializer) {
		return vhInitializer == VhInitializers.empty() ? make(vhFactory) : new InitVhFactory<>(vhFactory, vhInitializer);
	}

	// Composite Factories

	public static <VH extends ViewHolder> VhFactory<VH> make(@NonNull ViewFactory viewFactory, @NonNull ItemVhFactory<? extends VH> itemVhFactory) {
		return new CompositeVhFactory<>(viewFactory, itemVhFactory);
	}

	public static <VH extends ViewHolder> VhFactory<VH> make(@NonNull ViewFactory viewFactory, @NonNull Class<? extends VH> vhClass) {
		return new CompositeVhFactory<>(viewFactory, new ReflectiveItemVhFactory<>(vhClass));
	}

	public static <VH extends ViewHolder> VhFactory<VH> make(@LayoutRes int itemLayout, @NonNull ItemVhFactory<? extends VH> itemVhFactory) {
		return new CompositeVhFactory<>(new InflateViewFactory(itemLayout), itemVhFactory);
	}

	public static <VH extends ViewHolder> VhFactory<VH> make(@LayoutRes int itemLayout, @NonNull Class<? extends VH> vhClass) {
		return new CompositeVhFactory<>(new InflateViewFactory(itemLayout), new ReflectiveItemVhFactory<>(vhClass));
	}
}

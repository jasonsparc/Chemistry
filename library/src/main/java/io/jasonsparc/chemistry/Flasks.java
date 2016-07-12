package io.jasonsparc.chemistry;

import android.support.annotation.AnyRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

import java.util.Collection;

import io.jasonsparc.chemistry.internal.flasks.CompositeReflectiveTypedFlask;
import io.jasonsparc.chemistry.internal.flasks.CompositeTypedFlask;
import io.jasonsparc.chemistry.internal.flasks.InflateReflectiveTypedFlask;
import io.jasonsparc.chemistry.internal.flasks.InflateTypedFlask;
import io.jasonsparc.chemistry.internal.predicates.FlaskArrayBindPredicate;
import io.jasonsparc.chemistry.internal.predicates.FlaskBindPredicate;
import io.jasonsparc.chemistry.internal.predicates.FlaskCollectionBindPredicate;

/**
 * TODO Docs
 *
 * Created by jason on 11/07/2016.
 */
public class Flasks {

	// Composite `Flask` factories

	public static <VH extends ViewHolder> Flask<VH> make(@ViewType @AnyRes final int viewType, @NonNull ViewFactory viewFactory, @NonNull VhFactory<? extends VH> vhFactory) {
		return new CompositeTypedFlask<>(viewType, viewFactory, vhFactory);
	}

	public static <VH extends ViewHolder> Flask<VH> make(@ViewType @AnyRes int viewType, @NonNull ViewFactory viewFactory, @NonNull Class<? extends VH> vhCls) {
		return new CompositeReflectiveTypedFlask<>(viewType, viewFactory, vhCls);
	}

	// Auto-inflate `Flask` factories

	public static <VH extends ViewHolder> Flask<VH> make(@ViewType @AnyRes int viewType, @LayoutRes int itemLayout, @NonNull VhFactory<? extends VH> vhFactory) {
		return new InflateTypedFlask<>(viewType, itemLayout, vhFactory);
	}

	public static <VH extends ViewHolder> Flask<VH> make(@LayoutRes int itemLayout, @NonNull VhFactory<? extends VH> vhFactory) {
		return new InflateTypedFlask<>(itemLayout, itemLayout, vhFactory);
	}

	public static <VH extends ViewHolder> Flask<VH> make(@ViewType @AnyRes int viewType, @LayoutRes int itemLayout, @NonNull Class<? extends VH> vhCls) {
		return new InflateReflectiveTypedFlask<>(viewType, itemLayout, vhCls);
	}

	public static <VH extends ViewHolder> Flask<VH> make(@LayoutRes int itemLayout, @NonNull Class<? extends VH> vhCls) {
		return new InflateReflectiveTypedFlask<>(itemLayout, itemLayout, vhCls);
	}

	static {
		class VHS extends ViewHolder {
			public VHS(View itemView) { super(itemView); }
		}
		class VH2 extends VHS {
			public VH2(View itemView) { super(itemView); }
		}
		class VH3 extends VHS {
			public VH3(View itemView) { super(itemView); }
		}

		abstract class Flask1 implements Flask<VH2> { }

		//noinspection ConstantConditions,UnnecessaryLocalVariable
		Flask<?> f = null;
		//noinspection UnnecessaryLocalVariable,ConstantConditions
		Flask<? extends ViewHolder> f2 = f;

		chem().bind(Number.class, BindPredicates.of(VH2.class, VH3.class, VHS.class), new ItemBinder<Number, ViewHolder>() {
			@Override
			public void bindViewHolder(ViewHolder holder, Number number) {

			}
		});
	}

	static Chemistry chem() {
		Chemistry chem = null;
		assert chem != null;
		return chem;
	}

	// Utilities

	@SafeVarargs
	public static <FL extends Flask<? extends ViewHolder>> FL[] array(@NonNull FL... flasks) {
		return flasks;
	}

	@SuppressWarnings("unchecked")
	public static <VH extends ViewHolder> Flask<? extends VH>[] array(@NonNull Collection<? extends Flask<? extends VH>> flasks) {
		return flasks.toArray(new Flask[flasks.size()]);
	}

	// Bind Predicates

	public static <VH extends ViewHolder> BindPredicate<VH> matches(Flask<? extends VH> flask) {
		return new FlaskBindPredicate<>(flask);
	}

	@SafeVarargs
	public static <VH extends ViewHolder> BindPredicate<VH> matches(@NonNull Flask<? extends VH>... flasks) {
		return new FlaskArrayBindPredicate<>(flasks);
	}

	public static <VH extends ViewHolder> BindPredicate<VH> matches(@NonNull Collection<? extends Flask<? extends VH>> flasks) {
		return new FlaskCollectionBindPredicate<>(flasks);
	}
}

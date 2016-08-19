package io.jasonsparc.chemistry;

import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.jasonsparc.chemistry.util.IdSelectors;
import io.jasonsparc.chemistry.util.ItemBinders;
import io.jasonsparc.chemistry.util.VhFactories;
import io.jasonsparc.chemistry.util.VhInitializers;
import io.jasonsparc.chemistry.util.ViewTypes;

/**
 * Created by Jason on 19/08/2016.
 */
public abstract class BasicChemistry<Item, VH extends ViewHolder> extends Chemistry<Item> implements VhFactory<VH>, ItemBinder<Item, VH> {

	@Override
	public final int getItemViewType(Item item) {
		return getViewType();
	}

	@Override
	public final VhFactory<? extends VH> getVhFactory(Item item) {
		return this;
	}

	@Override
	public final ItemBinder<? super Item, ? super VH> getItemBinder(Item item) {
		return this;
	}

	@ViewType
	@AnyRes
	public abstract int getViewType();

	@Override
	public abstract VH createViewHolder(ViewGroup parent);

	@Override
	public abstract void bindViewHolder(VH holder, Item item);


	// Utilities

	public <RI extends Item> Boiler<RI, VH> compose() {
		return make(this);
	}

	public <RI extends Item> Boiler<RI, VH> compose(@ViewType @AnyRes int viewType) {
		return make(viewType, this);
	}


	public interface Transformer<Item, VH extends ViewHolder> {

		void apply(Boiler<? extends Item, ? extends VH> boiler);
	}

	public <RI extends Item> Boiler<RI, VH> compose(@NonNull Transformer<? super RI, ? super VH> transformer) {
		return this.<RI>compose().compose(transformer);
	}


	// Boiler implementation

	public static class Boiler<Item, VH extends ViewHolder> {


		public BasicChemistry<Item, VH> boil() {
			return new CompositeImpl<>(this);
		}

		public Boiler<Item, VH> compose(@NonNull Transformer<? super Item, ? super VH> transformer) {
			transformer.apply(this);
			return this;
		}


		public Boiler<Item, VH> setItemIds(@NonNull IdSelector<? super Item> idSelector) {
			this.idSelector = idSelector;
			return this;
		}

		public Boiler<Item, VH> setViewType(@ViewType @AnyRes int viewType) {
			ViewTypes.validateArgument(viewType);
			this.viewType = viewType;
			return this;
		}


		public Boiler<Item, VH> addInit(@NonNull VhInitializer<? super VH> vhInitializer) {
			return add(vhInitializer);
		}

		public Boiler<Item, VH> addBinder(@NonNull ItemBinder<? super Item, ? super VH> itemBinder) {
			return add(itemBinder);
		}


		public Boiler<Item, VH> add(@NonNull VhInitializer<? super VH> vhInitializer) {
			vhInitializers.add(vhInitializer);
			return this;
		}

		public Boiler<Item, VH> add(@NonNull ItemBinder<? super Item, ? super VH> itemBinder) {
			itemBinders.add(itemBinder);
			return this;
		}


		// Internals

		@NonNull final VhFactory<? extends VH> vhFactory;
		final ArrayList<VhInitializer<? super VH>> vhInitializers;
		final ArrayList<ItemBinder<? super Item, ? super VH>> itemBinders;

		@NonNull IdSelector<? super Item> idSelector = IdSelectors.empty();
		@ViewType @AnyRes int viewType;

		Boiler(@ViewType @AnyRes int viewType, @NonNull VhFactory<? extends VH> vhFactory) {
			ViewTypes.validateArgument(viewType);
			this.vhFactory = vhFactory;
			this.vhInitializers = new ArrayList<>(4);
			this.itemBinders = new ArrayList<>(4);
			this.viewType = viewType;
		}

		@SuppressWarnings("unchecked")
		Boiler(@NonNull BasicChemistry<? super Item, VH> base) {
			vhInitializers = new ArrayList<>(4);
			itemBinders = new ArrayList<>(4);

			if (base instanceof CompositeImpl<?, ?>) {
				CompositeImpl<? super Item, VH> composite = (CompositeImpl) base;
				vhFactory = composite.vhFactory;
				itemBinders.add(base);
				viewType = composite.viewType;
			} else {
				vhFactory = base;
				itemBinders.add(base);
				viewType = base.getViewType();
			}
		}
	}

	static final class CompositeImpl<Item, VH extends ViewHolder> extends BasicChemistry<Item, VH> {
		@NonNull final VhFactory<? extends VH> vhFactory;
		@NonNull final ItemBinder<? super Item, ? super VH> itemBinder;

		@NonNull final IdSelector<? super Item> idSelector;
		@ViewType @AnyRes final int viewType;

		CompositeImpl(@NonNull BasicChemistry.Boiler<Item, VH> boiler) {
			this.vhFactory = VhFactories.make(boiler.vhFactory, VhInitializers.make(boiler.vhInitializers));
			this.itemBinder = ItemBinders.make(boiler.itemBinders);
			this.idSelector = boiler.idSelector;
			this.viewType = boiler.viewType;
		}

		@Override
		public int getViewType() {
			return viewType;
		}

		@Override
		public VH createViewHolder(ViewGroup parent) {
			return vhFactory.createViewHolder(parent);
		}

		@Override
		public void bindViewHolder(VH holder, Item item) {
			itemBinder.bindViewHolder(holder, item);
		}
	}
}

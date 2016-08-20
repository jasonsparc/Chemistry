package io.jasonsparc.chemistry;

import android.support.annotation.AnyRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

	public <RI extends Item> Boiler<RI, VH> boiler() {
		return make(this);
	}

	public <RI extends Item> Boiler<RI, VH> boiler(@ViewType @AnyRes int viewType) {
		return this.<RI>boiler().useViewType(viewType);
	}


	// Boiler-delegating methods

	public interface Transformer<Item, VH extends ViewHolder> {

		void apply(Boiler<Item, VH> boiler);
	}

	public final Boiler<Item, VH> compose(@NonNull Transformer<? super Item, VH> transformer) {
		return boiler().compose(transformer);
	}

	public final Boiler<Item, VH> useItemIds(@NonNull IdSelector<? super Item> idSelector) {
		return boiler().useItemIds(idSelector);
	}

	public final Boiler<Item, VH> useViewType(@ViewType @AnyRes int viewType) {
		return boiler().useViewType(viewType);
	}

	public final Boiler<Item, VH> useUniqueViewType() {
		return boiler().useUniqueViewType();
	}


	public final Boiler<Item, VH> useVhFactory(@NonNull VhFactory<? extends VH> vhFactory) {
		return boiler().useVhFactory(vhFactory);
	}

	public final Boiler<Item, VH> useVhFactory(@NonNull ViewFactory viewFactory, @NonNull ItemVhFactory<? extends VH> itemVhFactory) {
		return boiler().useVhFactory(viewFactory, itemVhFactory);
	}

	public final Boiler<Item, VH> useVhFactory(@NonNull ViewFactory viewFactory, @NonNull Class<? extends VH> vhClass) {
		return boiler().useVhFactory(viewFactory, vhClass);
	}

	public final Boiler<Item, VH> useVhFactory(@LayoutRes int itemLayout, @NonNull ItemVhFactory<? extends VH> itemVhFactory) {
		return boiler().useVhFactory(itemLayout, itemVhFactory);
	}

	public final Boiler<Item, VH> useVhFactory(@LayoutRes int itemLayout, @NonNull Class<? extends VH> vhClass) {
		return boiler().useVhFactory(itemLayout, vhClass);
	}


	public final Boiler<Item, VH> addInit(@NonNull VhInitializer<? super VH> vhInitializer) {
		return boiler().addInit(vhInitializer);
	}

	public final Boiler<Item, VH> addBinder(@NonNull ItemBinder<? super Item, ? super VH> itemBinder) {
		return boiler().addBinder(itemBinder);
	}

	public final Boiler<Item, VH> add(@NonNull VhInitializer<? super VH> vhInitializer) {
		return boiler().add(vhInitializer);
	}

	public final Boiler<Item, VH> add(@NonNull ItemBinder<? super Item, ? super VH> itemBinder) {
		return boiler().add(itemBinder);
	}


	// Boiler implementation

	public static class Boiler<Item, VH extends ViewHolder> {
		@NonNull IdSelector<? super Item> idSelector;
		@ViewType @AnyRes int viewType;

		@Nullable VhFactory<? extends VH> vhFactory;
		final ArrayList<VhInitializer<? super VH>> vhInitializers = new ArrayList<>(4);
		final ArrayList<ItemBinder<? super Item, ? super VH>> itemBinders = new ArrayList<>(4);

		protected Boiler() {
			idSelector = IdSelectors.empty();
		}

		protected Boiler(Preperator<? super Item> preperator) {
			idSelector = preperator.idSelector;
			viewType = preperator.viewType;
		}

		protected Boiler(@NonNull BasicChemistry<? super Item, VH> base) {
			if (base instanceof CompositeImpl<?, ?>) {
				@SuppressWarnings("unchecked")
				CompositeImpl<? super Item, VH> composite = (CompositeImpl) base;
				idSelector = composite.idSelector;
				viewType = composite.viewType;
				vhFactory = composite.vhFactory;
				itemBinders.add(composite.itemBinder);
			} else {
				idSelector = base;
				viewType = base.getViewType();
				vhFactory = base;
				itemBinders.add(base);
			}
		}


		public BasicChemistry<Item, VH> boil() {
			return new CompositeImpl<>(this);
		}

		@SuppressWarnings("unchecked")
		public Boiler<Item, VH> compose(@NonNull Transformer<? super Item, VH> transformer) {
			((Transformer<Item, VH>) transformer).apply(this);
			return this;
		}

		public Boiler<Item, VH> useItemIds(@NonNull IdSelector<? super Item> idSelector) {
			this.idSelector = idSelector;
			return this;
		}

		public Boiler<Item, VH> useViewType(@ViewType @AnyRes int viewType) {
			ViewTypes.validateArgument(viewType);
			this.viewType = viewType;
			return this;
		}

		public Boiler<Item, VH> useUniqueViewType() {
			//noinspection Range
			this.viewType = 0; // Defer id generation. See CompositeImpl below...
			return this;
		}


		public Boiler<Item, VH> useVhFactory(@NonNull VhFactory<? extends VH> vhFactory) {
			this.vhFactory = vhFactory;
			return this;
		}

		public Boiler<Item, VH> useVhFactory(@NonNull ViewFactory viewFactory, @NonNull ItemVhFactory<? extends VH> itemVhFactory) {
			return useVhFactory(VhFactories.make(viewFactory, itemVhFactory));
		}

		public Boiler<Item, VH> useVhFactory(@NonNull ViewFactory viewFactory, @NonNull Class<? extends VH> vhClass) {
			return useVhFactory(VhFactories.make(viewFactory, vhClass));
		}

		public Boiler<Item, VH> useVhFactory(@LayoutRes int itemLayout, @NonNull ItemVhFactory<? extends VH> itemVhFactory) {
			return useVhFactory(VhFactories.make(itemLayout, itemVhFactory));
		}

		public Boiler<Item, VH> useVhFactory(@LayoutRes int itemLayout, @NonNull Class<? extends VH> vhClass) {
			return useVhFactory(VhFactories.make(itemLayout, vhClass));
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


		public Boiler<Item, VH> removeInit(@NonNull VhInitializer<? super VH> vhInitializer) {
			return remove(vhInitializer);
		}

		public Boiler<Item, VH> removeBinder(@NonNull ItemBinder<? super Item, ? super VH> itemBinder) {
			return remove(itemBinder);
		}

		public Boiler<Item, VH> remove(@NonNull VhInitializer<? super VH> vhInitializer) {
			vhInitializers.remove(vhInitializer);
			return this;
		}

		public Boiler<Item, VH> remove(@NonNull ItemBinder<? super Item, ? super VH> itemBinder) {
			itemBinders.remove(itemBinder);
			return this;
		}
	}


	// Preperator implementation

	public static class Preperator<Item> {
		@NonNull IdSelector<? super Item> idSelector = IdSelectors.empty();
		@ViewType @AnyRes int viewType;

		protected Preperator() { }

		public Preperator<Item> useItemIds(@NonNull IdSelector<? super Item> idSelector) {
			this.idSelector = idSelector;
			return this;
		}

		public Preperator<Item> useViewType(@ViewType @AnyRes int viewType) {
			ViewTypes.validateArgument(viewType);
			this.viewType = viewType;
			return this;
		}

		public Preperator<Item> useUniqueViewType() {
			//noinspection Range
			this.viewType = 0; // Defer id generation. See CompositeImpl below...
			return this;
		}


		public <VH extends ViewHolder> Boiler<Item, VH> useVhFactory(@NonNull VhFactory<? extends VH> vhFactory) {
			return this.<VH>makeBoiler().useVhFactory(vhFactory);
		}

		public <VH extends ViewHolder> Boiler<Item, VH> useVhFactory(@NonNull ViewFactory viewFactory, @NonNull ItemVhFactory<? extends VH> itemVhFactory) {
			return this.<VH>makeBoiler().useVhFactory(VhFactories.make(viewFactory, itemVhFactory));
		}

		public <VH extends ViewHolder> Boiler<Item, VH> useVhFactory(@NonNull ViewFactory viewFactory, @NonNull Class<? extends VH> vhClass) {
			return this.<VH>makeBoiler().useVhFactory(VhFactories.make(viewFactory, vhClass));
		}

		public <VH extends ViewHolder> Boiler<Item, VH> useVhFactory(@LayoutRes int itemLayout, @NonNull ItemVhFactory<? extends VH> itemVhFactory) {
			return this.<VH>makeBoiler().useVhFactory(VhFactories.make(itemLayout, itemVhFactory));
		}

		public <VH extends ViewHolder> Boiler<Item, VH> useVhFactory(@LayoutRes int itemLayout, @NonNull Class<? extends VH> vhClass) {
			return this.<VH>makeBoiler().useVhFactory(VhFactories.make(itemLayout, vhClass));
		}


		public <VH extends ViewHolder> Boiler<Item, VH> compose(@NonNull Transformer<? super Item, VH> transformer) {
			return this.<VH>makeBoiler().compose(transformer);
		}

		public <VH extends ViewHolder> Boiler<Item, VH> makeBoiler() {
			return new Boiler<>(this);
		}
	}


	// Internals

	static final class CompositeImpl<Item, VH extends ViewHolder> extends BasicChemistry<Item, VH> {
		@NonNull final IdSelector<? super Item> idSelector;
		@ViewType @AnyRes final int viewType;

		@NonNull final VhFactory<? extends VH> vhFactory;
		@NonNull final ItemBinder<? super Item, ? super VH> itemBinder;

		CompositeImpl(@NonNull BasicChemistry.Boiler<Item, VH> boiler) {
			// Must perform a special null-check for vhFactory, in case the user forgot to set it.
			if (boiler.vhFactory == null)
				throw new NullPointerException("vhFactory == null; forgot to set it?");

			vhFactory = VhFactories.make(boiler.vhFactory, VhInitializers.make(boiler.vhInitializers));
			itemBinder = ItemBinders.make(boiler.itemBinders);
			idSelector = boiler.idSelector;

			//noinspection Range
			viewType = boiler.viewType != 0 ? boiler.viewType : ViewTypes.generate();
		}

		@Override
		public long getItemId(Item item) {
			return idSelector.getItemId(item);
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

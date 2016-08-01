package io.jasonsparc.chemistry.adapters;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.jasonsparc.chemistry.Chemistry;
import io.jasonsparc.chemistry.ChemistryAdapter;
import io.jasonsparc.chemistry.util.functions.Consumer;
import io.jasonsparc.chemistry.util.functions.Function;

/**
 * Created by jason on 21/07/2016.
 */
public class ListChemistryAdapter<Item> extends ChemistryAdapter<Item> {
	@NonNull List<Item> itemList;

	public ListChemistryAdapter(@NonNull Chemistry chemistry) {
		super(chemistry);
		this.itemList = new ArrayList<>();
	}

	public ListChemistryAdapter(@NonNull Chemistry chemistry, int capacity) {
		super(chemistry);
		this.itemList = new ArrayList<>(capacity);
	}

	public ListChemistryAdapter(@NonNull Chemistry chemistry, @NonNull List<Item> itemList) {
		super(chemistry);
		this.itemList = itemList;
	}

	public List<Item> getList() {
		return itemList;
	}

	public ListChemistryAdapter<Item> setList(@NonNull List<Item> itemList) {
		this.itemList = itemList;
		return this;
	}

	@Override
	public int getItemCount() {
		return size();
	}

	@Override
	public Item getItem(int position) {
		return get(position);
	}

	// Add

	public ListChemistryAdapter<Item> add(Item item) {
		int loc = itemList.size();
		itemList.add(item);
		notifyItemInserted(loc);
		return this;
	}

	public ListChemistryAdapter<Item> add(int location, Item item) {
		itemList.add(location, item);
		notifyItemInserted(location);
		return this;
	}

	// Add All

	public ListChemistryAdapter<Item> addAll(Collection<Item> items) {
		int loc = itemList.size();
		if (itemList.addAll(items))
			notifyItemRangeInserted(loc, items.size());
		return this;
	}

	public ListChemistryAdapter<Item> addAll(int location, Collection<Item> items) {
		if (itemList.addAll(location, items))
			notifyItemRangeInserted(location, items.size());
		return this;
	}

	// Update

	public Item set(int location, Item item) {
		Item prev = itemList.set(location, item);
		notifyItemChanged(location);
		return prev;
	}

	public ListChemistryAdapter<Item> update(int location, Item item) {
		itemList.set(location, item);
		notifyItemChanged(location);
		return this;
	}

	public ListChemistryAdapter<Item> update(int location, Item item, @NonNull Consumer<? super Item> oldValueConsumer) {
		Item prev = itemList.set(location, item);
		notifyItemChanged(location);
		oldValueConsumer.accept(prev);
		return this;
	}

	public ListChemistryAdapter<Item> update(int location, @NonNull Function<? super Item, ? extends Item> itemMutator) {
		itemList.set(location, itemMutator.apply(itemList.get(location)));
		notifyItemChanged(location);
		return this;
	}

	// Remove

	public ListChemistryAdapter<Item> remove(int location, @NonNull Consumer<? super Item> oldValueConsumer) {
		oldValueConsumer.accept(itemList.remove(location));
		notifyItemRemoved(location);
		return this;
	}

	public Item remove(int location) {
		Item prev = itemList.remove(location);
		notifyItemRemoved(location);
		return prev;
	}

	public boolean remove(Item item) {
		int loc = itemList.indexOf(item);
		if (loc < 0) return false;
		itemList.remove(loc);
		notifyItemRemoved(loc);
		return true;
	}

	// Inspection

	public int indexOf(Item item) {
		return itemList.indexOf(item);
	}

	public Item get(int location) {
		return itemList.get(location);
	}

	public int size() {
		return itemList.size();
	}
}

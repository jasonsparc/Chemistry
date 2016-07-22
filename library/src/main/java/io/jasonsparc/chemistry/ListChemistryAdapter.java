package io.jasonsparc.chemistry;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.jasonsparc.chemistry.util.Function;
import io.jasonsparc.chemistry.util.Receiver;

/**
 * Created by jason on 21/07/2016.
 */
public class ListChemistryAdapter<Item> extends ChemistryAdapter<Item> {
	@NonNull List<Item> itemList;

	public ListChemistryAdapter(@NonNull Chemistry chemistry) {
		super(chemistry);
		this.itemList = new ArrayList<>();
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
		return itemList.size();
	}

	@Override
	public Item getItem(int position) {
		return itemList.get(position);
	}

	// Add

	public ListChemistryAdapter<Item> add(Item item) {
		itemList.add(item);
		return this;
	}

	public ListChemistryAdapter<Item> add(int location, Item item) {
		itemList.add(location, item);
		return this;
	}

	// Add All

	public ListChemistryAdapter<Item> addAll(Collection<Item> items) {
		itemList.addAll(items);
		return this;
	}

	public ListChemistryAdapter<Item> addAll(int location, Collection<Item> items) {
		itemList.addAll(location, items);
		return this;
	}

	// Update

	public Item set(int location, Item item) {
		return itemList.set(location, item);
	}

	public ListChemistryAdapter<Item> update(int location, Item item) {
		itemList.set(location, item);
		return this;
	}

	public ListChemistryAdapter<Item> update(int location, Item item, @NonNull Receiver<? super Item> oldValueReceiver) {
		oldValueReceiver.onReceive(itemList.set(location, item));
		return this;
	}

	public ListChemistryAdapter<Item> update(int location, @NonNull Function<? super Item, ? extends Item> itemMutator) {
		itemList.set(location, itemMutator.applyOn(itemList.get(location)));
		return this;
	}

	// Remove

	public ListChemistryAdapter<Item> remove(int location, @NonNull Receiver<? super Item> oldValueReceiver) {
		oldValueReceiver.onReceive(itemList.remove(location));
		return this;
	}

	public Item remove(int location) {
		return itemList.remove(location);
	}

	public boolean remove(Item item) {
		return itemList.remove(item);
	}

	// Inspection

	public int indexOf(Item item) {
		return itemList.indexOf(item);
	}

	public Item get(int location) {
		return getItem(location);
	}

	public int size() {
		return getItemCount();
	}
}

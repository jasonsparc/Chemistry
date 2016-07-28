package io.jasonsparc.chemistry.adapters;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;

import java.util.Collection;
import java.util.List;

import io.jasonsparc.chemistry.Chemistry;
import io.jasonsparc.chemistry.ChemistryAdapter;
import io.jasonsparc.chemistry.util.Function;
import io.jasonsparc.chemistry.util.Receiver;

/**
 * Created by jason on 25/07/2016.
 */
public class CompoundAdapter extends ListChemistryAdapter<Object> {
	// TODO Use `Collections.binarySearch()` on `itemList` instead

	final SparseArrayCompat<Object> itemIndices;
	int totalSize = -1; // -1 if indices needs to be rebuild

	boolean shouldDelegateEvents; // TODO Use bit flags instead

	public CompoundAdapter(@NonNull Chemistry chemistry) {
		super(chemistry);
		itemIndices = new SparseArrayCompat<>();
	}

	public CompoundAdapter(@NonNull Chemistry chemistry, int capacity) {
		super(chemistry, capacity);
		itemIndices = new SparseArrayCompat<>(capacity);
	}

	private void invalidateItemIndices() {
		totalSize = -1;
	}

	private void ensureItemIndices() {
		if (totalSize >= 0) {
			return;
		}

		SparseArrayCompat<Object> indices = this.itemIndices;
		indices.clear();

		int nextIndex = 0;
		for (Object item : getList()) {
			final int adjustments;
			if (item instanceof Adapter<?>) {
				adjustments = ((Adapter) item).getItemCount();

				if (adjustments <= 0)
					continue;
			} else {
				adjustments = 1;
			}

			indices.append(nextIndex, item);
			nextIndex += adjustments;
		}
		totalSize = nextIndex;
	}

	// Event Delegation

	// FIXME Err... event delegation is broken if view was recycled because the adapter where it came from has been removed.

	// TODO-FIXME There should be no event delegation!!! Or piggy-hack the ViewHolder...
	// ...by temporarily replacing the ViewHolder position with the sub-adapter position.

	@Override
	public void onViewRecycled(ViewHolder holder) {
		if (!shouldDelegateEvents) {
			return;
		}

		int position = holder.getAdapterPosition();

		// TODO Send event to a valid adapter...
	}

	@Override
	public boolean onFailedToRecycleView(ViewHolder holder) {
		if (!shouldDelegateEvents) {
			return super.onFailedToRecycleView(holder);
		}

		int position = holder.getAdapterPosition();

		// TODO Send event to a valid adapter...

		return false;
	}

	@Override
	public void onViewAttachedToWindow(ViewHolder holder) {
		super.onViewAttachedToWindow(holder);
	}

	@Override
	public void onViewDetachedFromWindow(ViewHolder holder) {
		super.onViewDetachedFromWindow(holder);
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
	}

	@Override
	public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
		super.onDetachedFromRecyclerView(recyclerView);
	}


	//


	//


	//


	//


	//


	//


	// Overrides

	@Override
	public int getItemCount() {
		ensureItemIndices();
		return totalSize;
	}

	@Override
	public Object getItem(int position) {
		ensureItemIndices();

		int i = itemIndices.indexOfKey(position);
		if (i < 0) i = ~i;

		Object dataSource = itemIndices.valueAt(i);

		if (dataSource instanceof ChemistryAdapter<?>) {
			return ((ChemistryAdapter<?>) dataSource).getItem(position - itemIndices.keyAt(i));
		}

		return dataSource; // Returns either a single item piece or an Adapter (not ChemistryAdapter)
	}

	@Override
	public List<Object> getList() {
		return super.getList();
	}

	@Override
	public CompoundAdapter setList(@NonNull List<Object> objects) {
		invalidateItemIndices();
		super.setList(objects);
		return this;
	}


	//


	//


	//


	//


	//


	//


	//


	//


	// Add

	@Override
	public CompoundAdapter add(Object piece) {
		invalidateItemIndices();
		super.add(piece);
		return this;
	}

	@Override
	public CompoundAdapter add(int location, Object piece) {
		invalidateItemIndices();
		super.add(location, piece);
		return this;
	}

	// Add All
	@Override
	public CompoundAdapter addAll(Collection<Object> objects) {
		invalidateItemIndices();
		super.addAll(objects);
		return this;
	}

	@Override
	public CompoundAdapter addAll(int location, Collection<Object> objects) {
		invalidateItemIndices();
		super.addAll(location, objects);
		return this;
	}

	// Update

	@Override
	public Object set(int location, Object piece) {
		invalidateItemIndices();
		return super.set(location, piece);
	}

	@Override
	public CompoundAdapter update(int location, Object piece) {
		invalidateItemIndices();
		super.update(location, piece);
		return this;
	}

	@Override
	public CompoundAdapter update(int location, Object piece, @NonNull Receiver<? super Object> oldValueReceiver) {
		invalidateItemIndices();
		super.update(location, piece, oldValueReceiver);
		return this;
	}

	@Override
	public CompoundAdapter update(int location, @NonNull Function<? super Object, ?> itemMutator) {
		invalidateItemIndices();
		super.update(location, itemMutator);
		return this;
	}

	// Remove

	@Override
	public CompoundAdapter remove(int location, @NonNull Receiver<? super Object> oldValueReceiver) {
		invalidateItemIndices();
		super.remove(location, oldValueReceiver);
		return this;
	}

	@Override
	public Object remove(int location) {
		invalidateItemIndices();
		return super.remove(location);
	}

	@Override
	public boolean remove(Object piece) {
		invalidateItemIndices();
		return super.remove(piece);
	}

	// Inspection

	@Override
	public int indexOf(Object piece) {
		return super.indexOf(piece);
	}

	@Override
	public Object get(int location) {
		return super.get(location);
	}

	@Override
	public int size() {
		return super.size();
	}
}

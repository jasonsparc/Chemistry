package io.jasonsparc.chemistry;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;

/**
 * Created by jason on 28/06/2016.
 */
public abstract class Chemistry {

	public abstract <T> Chemistry selectType(Class<T> cls, TypeSelector<T> typeSelector);

	public abstract <T> Chemistry selectId(Class<T> cls, TypeSelector<T> typeSelector);

	public abstract <T> Chemistry curate(Class<T> cls, ItemCurator<T, ?> itemCurator);


	public abstract <T> Boiler<T> alter(Class<T> cls);

	public abstract Chemistry prepend(Chemistry chemistry);

	public abstract Chemistry append(Chemistry chemistry);

	public static abstract class Boiler<T> {

		public abstract Boiler<T> selectType(TypeSelector<T> typeSelector);

		public abstract Boiler<T> selectId(IdSelector<T> idSelector);

		public abstract <VH extends RecyclerView.ViewHolder> Boiler<T> adapt(int type, ItemAdapter<VH, T> adapter);

		public abstract <VH extends RecyclerView.ViewHolder> Boiler<T> adapt(int type, ViewFactory viewFactory, ViewHolderFactory<VH> vhFactory, ItemBinder<VH, T> itemBinder);

		public abstract <VH extends RecyclerView.ViewHolder> Boiler<T> adapt(int type, @LayoutRes int layoutRes, ViewHolderFactory<VH> vhFactory, ItemBinder<VH, T> itemBinder);

		public abstract <VH extends RecyclerView.ViewHolder> Boiler<T> adapt(@LayoutRes int layoutRes, ViewHolderFactory<VH> vhFactory, ItemBinder<VH, T> itemBinder);

		public abstract Chemistry boil();
	}

	// Querying `Chemistry`

	public abstract <T> TypeSelector<? super T> findTypeSelector(Class<T> cls);

	public abstract <T> IdSelector<? super T> findIdSelector(Class<T> cls);

	public abstract <T, I> ItemCurator<? super T, ? extends I> findItemCurator(Class<T> cls);
}

package io.jasonsparc.chemistry;

import android.support.annotation.AnyRes;

/**
 * TODO Docs
 *
 * Created by jason on 07/07/2016.
 */
public abstract class Chemistry {

	// Type Selectors

	public abstract <Item> Chemistry selectType(Class<Item> itemCls, @ViewTypeId @AnyRes int viewType);

	public abstract <Item> Chemistry selectType(Class<Item> itemCls, Flask<?> typeFlask);

	public abstract <Item> Chemistry selectType(Class<Item> itemCls, TypeSelector<? super Item> typeSelector);

	public abstract <Item> Chemistry selectType(Class<Item> itemCls, FlaskSelector<? super Item> flaskSelector);

	public abstract Chemistry ignoreSelectType(Class<?> itemCls);

	public abstract Chemistry ignoreAllSelectType();

	// Id Selectors

	public abstract <Item> Chemistry selectId(Class<Item> itemCls, IdSelector<? super Item> idSelector);

	public abstract Chemistry ignoreSelectId(Class<?> itemCls);

	public abstract Chemistry ignoreAllSelectId();
}

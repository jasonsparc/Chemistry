package io.jasonsparc.chemistry.internal.view_factories;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import io.jasonsparc.chemistry.ViewFactory;
import io.jasonsparc.chemistry.util.InflateUtils;

/**
 * Created by Jason on 19/08/2016.
 */
public class InflateViewFactory implements ViewFactory {
	@LayoutRes final int layoutRes;

	public InflateViewFactory(@LayoutRes int layoutRes) {
		this.layoutRes = layoutRes;
	}

	@Override
	public View createView(ViewGroup parent) {
		return InflateUtils.inflate(parent, layoutRes);
	}
}

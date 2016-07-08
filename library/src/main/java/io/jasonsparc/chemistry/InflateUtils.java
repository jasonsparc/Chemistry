package io.jasonsparc.chemistry;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jason on 07/07/2016.
 */
public class InflateUtils {

	public static View inflate(@NonNull Context context, @LayoutRes int layoutRes, @NonNull ViewGroup parent) {
		return LayoutInflater.from(context).inflate(layoutRes, parent, false);
	}

	public static View inflate(@LayoutRes int layoutRes, @NonNull ViewGroup parent) {
		return inflate(parent.getContext(), layoutRes, parent);
	}
}

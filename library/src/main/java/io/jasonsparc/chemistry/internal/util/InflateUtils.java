package io.jasonsparc.chemistry.internal.util;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.jasonsparc.chemistry.util.UtilityClass;

/**
 * Created by jason on 07/07/2016.
 */
@UtilityClass
public class InflateUtils {

	public static View inflate(@NonNull Context context, @LayoutRes int layoutRes, @NonNull ViewGroup parent) {
		return LayoutInflater.from(context).inflate(layoutRes, parent, false);
	}

	public static View inflate(@LayoutRes int layoutRes, @NonNull ViewGroup parent) {
		return inflate(parent.getContext(), layoutRes, parent);
	}
}

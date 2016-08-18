package io.jasonsparc.chemistry.util;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jason on 07/07/2016.
 */
@UtilityClass
public final class InflateUtils {

	public static View inflate(@NonNull Context context, @NonNull ViewGroup parent, @LayoutRes int layoutRes) {
		return LayoutInflater.from(context).inflate(layoutRes, parent, false);
	}

	public static View inflate(@NonNull ViewGroup parent, @LayoutRes int layoutRes) {
		return inflate(parent.getContext(), parent, layoutRes);
	}
}

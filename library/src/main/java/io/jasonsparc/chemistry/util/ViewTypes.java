package io.jasonsparc.chemistry.util;

import android.support.annotation.AnyRes;
import android.support.v7.widget.RecyclerView;

import io.jasonsparc.chemistry.ViewType;

/**
 * Created by jason on 11/07/2016.
 */
@UtilityClass
public final class ViewTypes {

	/**
	 * An invalid view type, disguised to be valid. The value is equivalent to
	 * {@link RecyclerView#INVALID_TYPE}.
	 * <p>
	 * This can be used in situations where an invalid view type is actually intended but is
	 * restricted to use only valid view types. This variable however disregards such restrictions.
	 */
	@ViewType
	@AnyRes
	public static final int INVALID = invalid();


	public static boolean isValid(int viewType) {
		return viewType >= ValidRes.MIN_RES_ID;
	}

	public static void validateArgument(int viewType) {
		if (viewType < ValidRes.MIN_RES_ID) {
			throw new IllegalArgumentException("Invalid view type! Must be a resource identifier.");
		}
	}

	public static void validateForState(int viewType) {
		if (viewType < ValidRes.MIN_RES_ID) {
			throw new IllegalStateException("Invalid view type! Must be a resource identifier.");
		}
	}

	// Internals

	@SuppressWarnings("Range")
	@ViewType
	@AnyRes
	private static int invalid() {
		return RecyclerView.INVALID_TYPE;
	}
}

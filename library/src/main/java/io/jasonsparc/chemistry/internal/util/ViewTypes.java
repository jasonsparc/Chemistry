package io.jasonsparc.chemistry.internal.util;

import io.jasonsparc.chemistry.ValidRes;

/**
 * Created by jason on 11/07/2016.
 */
public class ViewTypes {

	static final int MIN_RES_ID = ValidRes.MIN_RES_ID;

	public static void validateArgument(int viewType) {
		if (viewType < MIN_RES_ID) {
			throw new IllegalArgumentException("Invalid view type! Must be a resource identifier.");
		}
	}

	public static void validateState(int viewType) {
		if (viewType < MIN_RES_ID) {
			throw new IllegalStateException("Invalid view type! Must be a resource identifier.");
		}
	}
}

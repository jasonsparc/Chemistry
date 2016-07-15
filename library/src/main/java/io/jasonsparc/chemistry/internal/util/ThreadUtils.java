package io.jasonsparc.chemistry.internal.util;

import android.os.Looper;

/**
 * Created by jason on 15/07/2016.
 */
public class ThreadUtils {
	public static final Thread MAIN = Looper.getMainLooper().getThread();
}

package io.jasonsparc.chemistry;

import android.support.annotation.IntRange;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static io.jasonsparc.chemistry.ViewTypeId.MIN_RES_ID;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Denotes that the annotated element of integer type, represents a unique view type and that its
 * value should be a resource identifier (e.g. {@code android.R.string.ok}) to ensure its
 * uniqueness.
 * <p>
 * Created by jason on 08/07/2016.
 */
@Documented
@Retention(CLASS)
@IntRange(from = MIN_RES_ID, to = Integer.MAX_VALUE)
public @interface ViewTypeId {

	/**
	 * The minimum int value that represents a valid resource identifier.
	 */
	int MIN_RES_ID = 0x01000000;
}

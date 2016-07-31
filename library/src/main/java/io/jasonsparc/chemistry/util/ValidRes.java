package io.jasonsparc.chemistry.util;

import android.support.annotation.AnyRes;
import android.support.annotation.IntRange;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static io.jasonsparc.chemistry.util.ValidRes.MIN_RES_ID;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Denotes that the annotated element of integer type, represents a valid resource identifier (e.g.
 * {@code android.R.string.ok}, {@code android.R.id.hint}, {@code android.R.layout.simple_list_item_1})
 * and must not be of an arbitrary int value.
 * <p>
 * This annotation is meant to be used along with {@link AnyRes} to further enforce that an element
 * should be a valid resource.
 * <p>
 * Created by jason on 08/07/2016.
 *
 * @see AnyRes
 */
@Documented
@Retention(CLASS)
@Target({METHOD, PARAMETER, FIELD, LOCAL_VARIABLE, ANNOTATION_TYPE})
@IntRange(from = MIN_RES_ID, to = Integer.MAX_VALUE)
public @interface ValidRes {

	/**
	 * The minimum int value that represents a valid resource identifier.
	 */
	int MIN_RES_ID = 0x01000000;
}

package io.jasonsparc.chemistry;

import android.support.v7.widget.RecyclerView;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import io.jasonsparc.chemistry.util.ValidRes;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Denotes that the annotated element of integer type, represents a unique view type and that its
 * value should be a resource identifier (e.g. {@code android.R.string.ok}, {@code
 * android.R.id.hint}, etc.) to ensure its uniqueness.
 * <p>
 * Created by jason on 08/07/2016.
 *
 * @see RecyclerView.Adapter#getItemViewType(int)
 */
@Documented
@Retention(CLASS)
@Target({METHOD, PARAMETER, FIELD, LOCAL_VARIABLE, ANNOTATION_TYPE})
@ValidRes
public @interface ViewType {
}

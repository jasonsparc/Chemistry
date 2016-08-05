package io.jasonsparc.chemistry.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Denotes that the annotated type is a utility class.
 * <p>
 * A utility class is a class that is just a namespace for functions. No instances of it can exist,
 * and all its members are static. For example, {@link java.lang.Math java.lang.Math} and
 * {@link java.util.Collections java.util.Collections} are well known utility classes.
 * <p>
 * Created by jason on 05/08/2016.
 */
@Documented
@Retention(SOURCE)
@Target(TYPE)
public @interface UtilityClass {
}

package xyz.avarel.aje.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * Represents a {@link xyz.avarel.aje.ast.Expr} that can be {@link xyz.avarel.aje.ast.flow.Statements}.
 */
@Retention(RetentionPolicy.SOURCE)
@Target({FIELD, METHOD})
@Documented
public @interface CanBeStatements {
}

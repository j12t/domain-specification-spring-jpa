package com.j12t.domain.specification.jpa.converter;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
@Inherited
public @interface SpringJpaMappingSpecification {

	/**
     * The function of the request parameter to bind to.
     */
    String name();

}

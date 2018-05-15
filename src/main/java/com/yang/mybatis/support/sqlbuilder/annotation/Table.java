/**
 * Baijiahulian.com Inc. Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.yang.mybatis.support.sqlbuilder.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Specifies the primary table for the annotated entity. Additional tables may be specified using {@link SecondaryTable}
 * or {@link SecondaryTables} annotation.
 *
 * <p>
 * If no <code>Table</code> annotation is specified for an entity class, the default values apply.
 *
 * <pre>
 *    Example:
 * 
 *    &#064;Entity
 *    &#064;Table(name="CUST", schema="RECORDS")
 *    public class Customer { ... }
 * </pre>
 *
 * @since Java Persistence 1.0
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Table {

    /**
     * (Optional) The name of the table.
     * <p>
     * Defaults to the entity name.
     */
    String name() default "";

    /**
     * (Optional) The catalog of the table.
     * <p>
     * Defaults to the default catalog.
     */
    String catalog() default "";

    /**
     * (Optional) The schema of the table.
     * <p>
     * Defaults to the default schema for user.
     */
    String schema() default "";

    /**
     * (Optional) The define of the table.
     * <p>
     * Defaults to the entity name.
     */
    String tableDefine() default "";

}

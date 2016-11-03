/*
 * Copyright 2015-2016 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.junit.jupiter.migrationsupport.rules;

import static org.junit.platform.commons.meta.API.Usage.Experimental;

import java.util.function.Function;

import org.junit.Rule;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.TestExtensionContext;
import org.junit.jupiter.migrationsupport.rules.adapter.AbstractTestRuleAdapter;
import org.junit.jupiter.migrationsupport.rules.adapter.ExternalResourceAdapter;
import org.junit.jupiter.migrationsupport.rules.member.RuleAnnotatedMember;
import org.junit.platform.commons.meta.API;
import org.junit.rules.TestRule;
import org.junit.rules.Verifier;

/**
 * This {@code Extension} provides native support for subclasses of
 * the {@code Verifier} rule from JUnit 4.
 * {@code Rule}-annotated fields as well as methods are supported.
 *
 * <p>By using this class-level extension on a test class such
 * {@code Verifier} implementations in legacy code bases
 * can be left unchanged including the JUnit 4 rule import statements.
 *
 * <p>However, if you intend to develop a <em>new</em> extension for
 * JUnit 5 please use the new extension model of JUnit Jupiter instead
 * of the rule-based model of JUnit 4.
 *
 *
 * @since 5.0
 * @see Verifier
 * @see TestRule
 * @see Rule
 */

@API(Experimental)
public class ExternalResourceSupport implements BeforeEachCallback, AfterEachCallback {

	private final Function<RuleAnnotatedMember, AbstractTestRuleAdapter> adapterGenerator = ExternalResourceAdapter::new;

	private final AbstractTestRuleSupport fieldSupport = new TestRuleFieldSupport(this.adapterGenerator);
	private final AbstractTestRuleSupport methodSupport = new TestRuleMethodSupport(this.adapterGenerator);

	@Override
	public void beforeEach(TestExtensionContext context) throws Exception {
		this.fieldSupport.beforeEach(context);
		this.methodSupport.beforeEach(context);
	}

	@Override
	public void afterEach(TestExtensionContext context) throws Exception {
		this.fieldSupport.afterEach(context);
		this.methodSupport.afterEach(context);
	}

}

package com.bright.appstarter.test;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@TestExecutionListeners(
				listeners = SeleniumTestExecutionListener.class,
				mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
@WebIntegrationTest(randomPort = true)
public @interface SeleniumTest
{
	Class<? extends WebDriver> driver() default HtmlUnitDriver.class;
}

package com.bright.appstarter.test;

import static org.springframework.core.annotation.AnnotationUtils.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Based on http://blog.codeleak.pl/2015/03/spring-boot-integration-testing-with.html
 */
public class SeleniumTestExecutionListener extends AbstractTestExecutionListener
{
	private Logger logger = LoggerFactory.getLogger(SeleniumTestExecutionListener.class);

	private WebDriver webDriver;

	@Override
	public int getOrder()
	{
		return Ordered.HIGHEST_PRECEDENCE;
	}

	@Override
	public void prepareTestInstance(TestContext testContext) throws Exception
	{
		if (webDriver != null)
		{
			return;
		}
		ApplicationContext context = testContext.getApplicationContext();
		if (context instanceof ConfigurableApplicationContext)
		{
			SeleniumTest annotation = findAnnotation(
				testContext.getTestClass(), SeleniumTest.class);
			webDriver = BeanUtils.instantiate(annotation.driver());

			@SuppressWarnings("resource")
			// Context lifecycle is handled by Spring
			ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) context;
			ConfigurableListableBeanFactory bf = configurableApplicationContext.getBeanFactory();
			bf.registerResolvableDependency(WebDriver.class, webDriver);

			addBaseUrlPropertyToApplicationContext(configurableApplicationContext);
		}
	}

	private static void addBaseUrlPropertyToApplicationContext(
		ConfigurableApplicationContext applicationContext)
	{
		String baseUrl = getBaseUrl(applicationContext);
		Map<String, Object> ourProperties = Collections.singletonMap("appstarter.selenium.baseUrl",
			baseUrl);

		MutablePropertySources propertySources = applicationContext.getEnvironment()
			.getPropertySources();
		MapPropertySource ourPropertySource = new MapPropertySource(
			"SeleniumTestExecutionListener", ourProperties);
		propertySources.addLast(ourPropertySource);
	}

	private static String getBaseUrl(ConfigurableApplicationContext applicationContext)
	{
		final String localServerPort = applicationContext.getBeanFactory().resolveEmbeddedValue(
			"${local.server.port}");
		return "http://localhost:" + localServerPort;
	}

	@Override
	public void afterTestClass(TestContext testContext) throws Exception
	{
		if (webDriver != null)
		{
			webDriver.quit();
		}
	}

	@Override
	public void afterTestMethod(TestContext testContext) throws Exception
	{
		if (testContext.getTestException() == null)
		{
			return;
		}

		if (webDriver instanceof TakesScreenshot)
		{
			File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
			String testName = toLowerUnderscore(testContext.getTestClass().getSimpleName());
			String methodName = toLowerUnderscore(testContext.getTestMethod().getName());

			Files.copy(
				screenshot.toPath(),
				Paths.get("screenshots", testName + "_" + methodName + "_" + screenshot.getName()));
		}
		else
		{
			logger.warn(
				"Could not take screenshot because webDriver is not an instance of TakesScreenshot"
					+
					" (it's a " + webDriver.getClass().getName() + ")");
		}
	}

	public static String toLowerUnderscore(String upperCamel)
	{
		return Stream
			.of(upperCamel.split("(?=[A-Z])"))
			.map(s -> s.toLowerCase())
			.collect(Collectors.joining("_"));
	}
}

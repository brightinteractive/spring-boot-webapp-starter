package com.bright.appstarter.web;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.net.URISyntaxException;

import javax.inject.Inject;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bright.appstarter.test.SeleniumTest;
import com.bright.appstarter.test.AppStarterIntegrationTest;
import com.bright.appstarter.test.AppStarterSeleniumTest;
import com.bright.appstarter.user.Role;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(SpringJUnit4ClassRunner.class)
@AppStarterIntegrationTest
@SeleniumTest
public class ErrorPageSeleniumTest extends AppStarterSeleniumTest
{
	@Inject
	private WebDriver driver;

	@Before
	public void setUp() throws Exception
	{
		login(Role.ADMIN);
	}

	@Test
	public void testErrorPageIncludesExceptionMessage() throws Exception
	{
		driver.get(throwExceptionUrl());
		assertThat(driver.getPageSource(), containsString(ThrowExceptionController.EXCEPTION_MESSAGE));
	}

	@Test
	public void testErrorPageDoesNotIncludeStackTrace() throws Exception
	{
		driver.get(throwExceptionUrl());
		assertThat(driver.getPageSource(), not(containsString(ThrowExceptionController.class.getSimpleName())));
	}

	private String throwExceptionUrl() throws URISyntaxException
	{
		return absoluteUrlFromRelativeUrl(ThrowExceptionController.URL);
	}
}

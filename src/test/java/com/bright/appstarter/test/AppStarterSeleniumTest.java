package com.bright.appstarter.test;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;

import org.apache.http.client.utils.URIBuilder;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.bright.appstarter.testsecurity.AuthenticationSetter;
import com.bright.appstarter.user.Role;
import com.bright.appstarter.user.User;
import com.bright.appstarter.user.UserService;
import com.bright.appstarter.userlogin.LoginPage;

public abstract class AppStarterSeleniumTest
{
	@Value("${appstarter.selenium.baseUrl}")
	private String baseUrl;

	@Inject
	private WebDriver driver;

	@Inject
	private UserService userService;

	private List<User> createdUsers = new ArrayList<>();

	private AuthenticationSetter authenticationSetter = new AuthenticationSetter();

	@Before
	public void goToStartPage() throws Exception
	{
		driver.manage().deleteAllCookies();
		driver.get(baseUrl);
	}

	@After
	public void removeTestUsers()
	{
		authenticationSetter.setFullyAuthenticatedUser();
		for (User user : createdUsers)
		{
			userService.deleteUser(user.getId());
		}
	}

	protected void login(Role role) throws LoggedInSeleniumTestException, Exception
	{
		if (!isOnLoginPage())
		{
			throw new LoggedInSeleniumTestException("Expecting to be on login page");
		}

		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);

		String email = "selenium_user_" +
			System.identityHashCode(this) + "_" +
			role +
			"@noreply.com";
		String password = "apassword";
		User user = getOrCreateTestUserWithRole(email, password, role);

		loginPage.emailAddress().sendKeys(user.getEmailAddress());
		loginPage.password().sendKeys(password);
		loginPage.submit().click();

		assertEquals("/", getDriverCurrentPath());
	}

	protected User getOrCreateTestUserWithRole(String emailAddress,
		String password,
		Role role) throws Exception
	{
		authenticationSetter.setFullyAuthenticatedUser();
		Optional<User> existingUser = userService.getUserByEmailAddress(emailAddress);

		if (existingUser.isPresent())
		{
			return existingUser.get();
		}

		Collection<Role> roles = new HashSet<>();
		roles.add(role);
		User user = userService.createUser(emailAddress, password, roles);
		createdUsers.add(user);
		return user;
	}

	private boolean isOnLoginPage()
	{
		return getDriverCurrentPath().equals("/login");
	}

	protected String getDriverCurrentPath()
	{
		URIBuilder uri;
		try
		{
			uri = new URIBuilder(driver.getCurrentUrl());
			return uri.getPath();
		}
		catch (URISyntaxException e)
		{
			throw new LoggedInSeleniumTestException("Syntax of driver.getCurrentUrl is invalid");
		}
	}

	protected String absoluteUrlFromRelativeUrl(
		String relativeUrl) throws URISyntaxException
	{
		URI baseURI = new URI(baseUrl);
		return baseURI.resolve(relativeUrl).toString();
	}

	public static class LoggedInSeleniumTestException extends RuntimeException
	{
		private static final long serialVersionUID = -9212257195428971551L;

		public LoggedInSeleniumTestException(String message)
		{
			super(message);
		}
	}
}

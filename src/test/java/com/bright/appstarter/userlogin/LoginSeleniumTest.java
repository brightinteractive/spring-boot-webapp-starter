package com.bright.appstarter.userlogin;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.bright.appstarter.test.SeleniumTest;
import com.bright.appstarter.test.AppStarterIntegrationTest;
import com.bright.appstarter.test.AppStarterSeleniumTest;
import com.bright.appstarter.user.Role;

@RunWith(SpringJUnit4ClassRunner.class)
@AppStarterIntegrationTest
@SeleniumTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginSeleniumTest extends AppStarterSeleniumTest
{
	@Inject
	private WebDriver driver;

	private LoginPage loginPage;
	private SignoutHeaderPage headerPage;

	@Before
	public void setup()
	{
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		headerPage = PageFactory.initElements(driver, SignoutHeaderPage.class);
	}

	@Test
	public void testAuthenticationFailureWhenProvidingBadCredentials()
	{
		loginPage.emailAddress().sendKeys("someuser@somewhere.com");
		loginPage.password().sendKeys("wrongpassword");
		loginPage.submit().click();

		assertTrue(driver.getCurrentUrl().contains("/login?error"));
	}

	@Test
	public void testAuthenticationSuccessWhenProvidingCorrectCredentials() throws Exception
	{
		String password = "mypassword";
		String emailAddress = "noreply@noreply.com";
		getOrCreateTestUserWithRole(emailAddress, password, Role.ADMIN);

		loginPage.emailAddress().sendKeys(emailAddress);
		loginPage.password().sendKeys(password);
		loginPage.submit().click();

		assertEquals("/", getDriverCurrentPath());
	}

	@Test
	public void testSignoutSignsOut() throws Exception
	{
		login(Role.ADMIN);
		headerPage.signoutButton().click();
		assertEquals("/login", getDriverCurrentPath());
	}

	@Test
	public void testAuthenticationFailureLeavesEmailPopulated()
	{
		String email = "someuser@somewhere.com";
		loginPage.emailAddress().sendKeys(email);
		loginPage.password().sendKeys("wrongpassword");
		loginPage.submit().click();

		assertEquals(email, loginPage.emailAddress().getAttribute("value"));
	}

	@Test
	public void testAuthenticationFailureLeavesPasswordUnpopulated()
	{
		String email = "someuser@somewhere.com";
		loginPage.emailAddress().sendKeys(email);
		loginPage.password().sendKeys("wrongpassword");
		loginPage.submit().click();

		assertEquals("", loginPage.password().getAttribute("value"));
	}
}

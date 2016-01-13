package com.bright.appstarter.userlogin;

import org.openqa.selenium.WebElement;

public class LoginPage
{
	private WebElement emailAddress;
	private WebElement password;
	private WebElement submit;

	public WebElement emailAddress()
	{
		return emailAddress;
	}

	public WebElement password()
	{
		return password;
	}

	public WebElement submit()
	{
		return submit;
	}
}

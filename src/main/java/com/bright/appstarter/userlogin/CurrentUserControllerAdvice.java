package com.bright.appstarter.userlogin;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


@ControllerAdvice
public class CurrentUserControllerAdvice
{
	@ModelAttribute(UserLoginConstants.CURRENT_USER_ATTRIBUTE_NAME)
	public CurrentUser getCurrentUser(Authentication authentication)
	{
		if (authentication == null)
		{
			return null;
		}

		return (CurrentUser) authentication.getPrincipal();
	}
}

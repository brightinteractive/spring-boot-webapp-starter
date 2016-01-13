package com.bright.appstarter.userlogin;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController
{
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView getLoginRetryPage(@RequestParam Optional<String> error,
		@RequestParam Optional<String> emailAddress,
		@RequestParam("remember-me") Optional<String> rememberMe,
		@RequestParam("logout") Optional<String> logout)
	{
		return new ModelAndView("content/user/login")
			.addObject("error", error)
			.addObject("emailAddress", emailAddress)
			.addObject("rememberMe", rememberMe)
			.addObject("logout", logout);
	}

	// Looking for POST? That's done by Spring Security!
}

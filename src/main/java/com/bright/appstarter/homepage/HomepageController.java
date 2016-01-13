package com.bright.appstarter.homepage;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomepageController
{

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String get(Model model)
	{
		return "content/home";
	}
}

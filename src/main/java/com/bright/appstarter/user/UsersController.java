package com.bright.appstarter.user;

import static com.bright.appstarter.user.UserUrlConstants.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(USERS_URL)
@Controller
public class UsersController
{
	private static final String TEMPLATE = "content/user/users";

	@Inject
	private UserService userService;

	@RequestMapping(method = GET)
	public String get(Model model)
	{
		model.addAttribute("users", userService.getAllUsers());
		return TEMPLATE;
	}
}

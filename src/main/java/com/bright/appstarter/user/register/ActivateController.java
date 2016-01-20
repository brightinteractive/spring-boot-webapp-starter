package com.bright.appstarter.user.register;

import static com.bright.appstarter.user.UserUrlConstants.*;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bright.appstarter.user.register.RegisterService.InvalidRegisterTokenException;

@Controller
public class ActivateController
{
	private static final String TEMPLATE = "content/user/activate-result";

	@Inject
	private RegisterService registerService;

	@RequestMapping(ACTIVATE_URL + "/{activationToken}")
	public String get(@PathVariable String activationToken,
		Model model)
	{
		boolean success;
		try
		{
			registerService.activate(activationToken);
			success = true;
		}
		catch (InvalidRegisterTokenException irte)
		{
			success = false;
		}

		model.addAttribute("success", success);
		return TEMPLATE;
	}
}

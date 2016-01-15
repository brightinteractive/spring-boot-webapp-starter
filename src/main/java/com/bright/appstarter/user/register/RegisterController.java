package com.bright.appstarter.user.register;

import static com.bright.appstarter.user.UserUrlConstants.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bright.appstarter.user.UserService.UserAlreadyExistsException;

@RequestMapping(REGISTER_URL)
@Controller
public class RegisterController
{
	private static final String TEMPLATE = "content/user/register";

	@Inject
	private RegisterService registerService;

	@Inject
	private RegisterFormValidator registerFormValidator;

	@InitBinder("form")
	public void initBinder(WebDataBinder binder)
	{
		binder.addValidators(registerFormValidator);
	}

	@RequestMapping(method = GET)
	public String get(Model model)
	{
		final RegisterForm form = new RegisterForm();
		model.addAttribute("form", form);
		return TEMPLATE;
	}

	@RequestMapping(method = POST)
	public String post(
		@Valid @ModelAttribute("form") RegisterForm form,
		BindingResult result,
		Model model) throws UserAlreadyExistsException
	{
		if (result.hasErrors())
		{
			return TEMPLATE;
		}

		registerService.register(form.getEmailAddress(), form.getPassword());
		return "redirect:/";
	}

}

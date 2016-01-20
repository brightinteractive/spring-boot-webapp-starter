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

@Controller
public class RegisterController
{
	private static final String TEMPLATE_REGISTER = "content/user/register";
	private static final String TEMPLATE_REGISTER_SUCCESS = "content/user/register-success";

	@Inject
	private RegisterService registerService;

	@Inject
	private RegisterFormValidator registerFormValidator;

	@InitBinder("form")
	public void initBinder(WebDataBinder binder)
	{
		binder.addValidators(registerFormValidator);
	}

	@RequestMapping(value = REGISTER_URL, method = GET)
	public String get(Model model)
	{
		final RegisterForm form = new RegisterForm();
		model.addAttribute("form", form);
		return TEMPLATE_REGISTER;
	}

	@RequestMapping(value = REGISTER_URL, method = POST)
	public String post(
		@Valid @ModelAttribute("form") RegisterForm form,
		BindingResult result,
		Model model) throws UserAlreadyExistsException
	{
		if (result.hasErrors())
		{
			return TEMPLATE_REGISTER;
		}

		registerService.register(form.getEmailAddress(), form.getPassword());
		return "redirect:" + REGISTER_SUCCESS_URL;
	}

	@RequestMapping(value = REGISTER_SUCCESS_URL, method = GET)
	public String getSuccess()
	{
		return TEMPLATE_REGISTER_SUCCESS;
	}
}

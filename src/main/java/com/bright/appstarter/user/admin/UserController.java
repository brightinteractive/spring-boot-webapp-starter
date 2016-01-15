package com.bright.appstarter.user.admin;

import static com.bright.appstarter.user.RoleCheckboxConstants.*;
import static com.bright.appstarter.user.UserUrlConstants.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.apache.commons.lang3.StringUtils;

import com.bright.appstarter.user.User;
import com.bright.appstarter.user.UserService;
import com.bright.appstarter.user.UserService.UserAlreadyExistsException;

@RequestMapping(USER_URL)
@Controller
public class UserController
{
	private static final String TEMPLATE = "content/user/user";

	@Inject
	private UserService userService;

	@Inject
	private UserFormValidator userCreateFormValidator;

	@InitBinder("form")
	public void initBinder(WebDataBinder binder)
	{
		binder.addValidators(userCreateFormValidator);
	}

	@RequestMapping(method = GET)
	public String get(
		@RequestParam Optional<Long> id,
		Model model)
	{
		final UserForm form = new UserForm();

		if (id.isPresent())
		{
			User user = userService.getUser(id.get());
			form.populateFromUser(user);
		}

		populateReferenceData(model);

		model.addAttribute("form", form);
		return TEMPLATE;
	}

	@RequestMapping(method = POST)
	public String post(
		@Valid @ModelAttribute("form") UserForm form,
		BindingResult result,
		Model model)
	{
		if (result.hasErrors())
		{
			populateReferenceData(model);
			return TEMPLATE;
		}

		User newUser;
		try
		{
			newUser = saveForm(form);
		}
		catch (UserAlreadyExistsException e)
		{
			result.reject("email.exists", "A user with this email address already exists");
			return TEMPLATE;
		}

		return "redirect:" + USERS_URL;
	}

	protected User saveForm(UserForm form) throws UserAlreadyExistsException
	{
		if (form.getId() == null)
		{
			return userService.createUser(form.getEmailAddress(),
				form.getPassword(),
				form.getRoles());
		}
		else
		{
			Optional<String> optionalChangedPassword;
			if (StringUtils.isNotEmpty(form.getPassword()))
			{
				optionalChangedPassword = Optional.of(form.getPassword());
			}
			else
			{
				optionalChangedPassword = Optional.empty();
			}

			return userService.updateUser(form.getId(), form.getEmailAddress(), form.getRoles(),
				optionalChangedPassword, Optional.empty());
		}
	}

	private void populateReferenceData(Model model)
	{
		model.addAttribute("roleOptions", ROLE_OPTIONS);
	}
}

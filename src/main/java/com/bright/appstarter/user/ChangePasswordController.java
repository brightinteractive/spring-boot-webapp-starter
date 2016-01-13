package com.bright.appstarter.user;

import static com.bright.appstarter.user.UserUrlConstants.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(CHANGE_PASSWORD_URL)
@Controller
public class ChangePasswordController
{
	private static final String TEMPLATE = "content/user/change-password";

	@Inject
	private UserService userService;

	@Inject
	private ChangePasswordFormValidator changePasswordFormValidator;

	@InitBinder("form")
	public void initBinder(WebDataBinder binder)
	{
		binder.addValidators(changePasswordFormValidator);
	}

	@RequestMapping(method = GET)
	@PreAuthorize("@userPermissionsService.canChangePassword(principal, #userId)")
	public String get(
		@RequestParam Long userId,
		Model model)
	{
		final ChangePasswordForm form = new ChangePasswordForm();
		form.setUserId(userId);

		model.addAttribute("form", form);
		return TEMPLATE;
	}

	@RequestMapping(method = POST)
	public String post(
		@Valid @ModelAttribute("form") ChangePasswordForm form,
		BindingResult result,
		Model model)
	{
		if (result.hasErrors())
		{
			return TEMPLATE;
		}

		saveForm(form);

		return "redirect:/";
	}

	protected void saveForm(ChangePasswordForm form)
	{
		userService.updatePassword(form.getUserId(),
			form.getNewPassword());
	}

}

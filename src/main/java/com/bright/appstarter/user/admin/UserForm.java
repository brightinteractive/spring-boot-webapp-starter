package com.bright.appstarter.user.admin;

import java.util.Collection;

import com.bright.appstarter.user.Role;
import com.bright.appstarter.user.User;
import com.bright.appstarter.user.UserBaseForm;

public class UserForm extends UserBaseForm
{
	private Long id;
	private Collection<Role> roles;

	public UserForm(Long id)
	{
		this.id = id;
	}

	public UserForm(String emailAddress,
		String password,
		Collection<Role> roles)
	{
		super(emailAddress, password);
		this.roles = roles;
	}

	public UserForm(Long id,
		String emailAddress,
		String password,
		Collection<Role> roles)
	{
		super(emailAddress, password);
		this.id = id;
		this.roles = roles;
	}

	public UserForm(Long id,
		String emailAddress,
		String password,
		String passwordRepeated)
	{
		super(emailAddress, password, passwordRepeated);
		this.id = id;
	}

	public UserForm()
	{
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Collection<Role> getRoles()
	{
		return roles;
	}

	public void setRoles(Collection<Role> roles)
	{
		this.roles = roles;
	}

	@Override
	public void populateFromUser(User user)
	{
		super.populateFromUser(user);
		this.id = user.getId();
		this.roles = user.getRoles();
	}
}

package com.bright.appstarter.user;

import java.util.Collection;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UserForm
{
	private Long id;

	@NotEmpty
	@Email
	private String emailAddress;

	private String password;
	private String passwordRepeated;
	private Collection<Role> roles;

	public UserForm(Long id)
	{
		this.id = id;
	}

	public UserForm(String emailAddress,
		String password,
		Collection<Role> roles)
	{
		this.emailAddress = emailAddress;
		this.password = password;
		this.roles = roles;
	}

	public UserForm(Long id,
		String emailAddress,
		String password,
		Collection<Role> roles)
	{
		this.id = id;
		this.emailAddress = emailAddress;
		this.password = password;
		this.roles = roles;
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

	public String getEmailAddress()
	{
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getPasswordRepeated()
	{
		return passwordRepeated;
	}

	public void setPasswordRepeated(String passwordRepeat)
	{
		this.passwordRepeated = passwordRepeat;
	}

	public Collection<Role> getRoles()
	{
		return roles;
	}

	public void setRoles(Collection<Role> roles)
	{
		this.roles = roles;
	}

	public void populateFromUser(User user)
	{
		this.id = user.getId();
		this.emailAddress = user.getEmailAddress();
		this.roles = user.getRoles();
	}
}

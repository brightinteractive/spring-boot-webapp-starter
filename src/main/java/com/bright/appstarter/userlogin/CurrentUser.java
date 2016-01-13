package com.bright.appstarter.userlogin;

import java.util.Collection;
import java.util.Collections;

import com.bright.appstarter.user.Role;
import com.bright.appstarter.user.RolesToAuthoritiesConverter;
import com.bright.appstarter.user.User;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CurrentUser extends org.springframework.security.core.userdetails.User
{
	private static final long serialVersionUID = 4746940462712620148L;
	private User user;
	private UIPermissions uiPermissions;

	public CurrentUser(User user,
		UIPermissions uiPermissions)
	{
		super(user.getEmailAddress(), user.getPasswordHash(),
			new RolesToAuthoritiesConverter(user.getRoles()).getAuthorities());
		this.user = user;
		this.uiPermissions = uiPermissions;
		this.uiPermissions.setCurrentUser(this);
	}

	public CurrentUser(User user)
	{
		this(user, new UIPermissionsNone());
	}

	public boolean hasRole(Role role)
	{
		return user.getRoles().contains(role);
	}

	public boolean hasAnyOfTheseRoles(Collection<Role> requiredRoles)
	{
		return !Collections.disjoint(requiredRoles, user.getRoles());
	}

	public User getUser()
	{
		return user;
	}

	public Long getId()
	{
		return user.getId();
	}

	public UIPermissions getUIPermissions()
	{
		return uiPermissions;
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder()
			.append(user)
			.toHashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!(obj instanceof CurrentUser))
		{
			return false;
		}
		CurrentUser that = (CurrentUser) obj;
		return new EqualsBuilder()
			.append(this.user, that.user)
			.isEquals();
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this)
			.append(user)
			.toString();
	}
}

package com.bright.appstarter.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;


public class RolesToAuthoritiesConverter
{
	private Collection<Role> roles;

	public RolesToAuthoritiesConverter(Collection<Role> roles)
	{
		this.roles = roles;
	}

	public List<GrantedAuthority> getAuthorities()
	{
		List<String> roleNames = new ArrayList<>();

		for (Role role : roles)
		{
			roleNames.add(role.getAuthorityName());
		}

		return AuthorityUtils.createAuthorityList(roleNames.toArray(new String[0]));
	}
}

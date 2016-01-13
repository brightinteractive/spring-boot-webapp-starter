package com.bright.appstarter.user;

import org.apache.commons.lang3.text.WordUtils;

public enum Role
{
	ADMIN(AuthorityNameConstants.ADMIN);

	private final String displayName;
	private final String authorityName;

	private Role(String authorityName)
	{
		this.authorityName = authorityName;
		this.displayName = WordUtils.capitalizeFully(this.toString());
	}

	private Role(String authorityName,
		String displayName)
	{
		this.authorityName = authorityName;
		this.displayName = displayName;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public String getAuthorityName()
	{
		return authorityName;
	}

	// Why do we need these constants?
	// They're used to avoid hardcoding role names when we secure our service
	// methods
	// using e.g. to avoid @PreAuthorize("hasRole('ROLE_ADMIN')")
	// Can't use enums directly - see:
	// http://docs.oracle.com/javase/specs/jls/se7/html/jls-9.html#jls-9.7.1
	// The role names have to be prefixed with _ROLE (by default) see
	// org.springframework.security.access.vote.RoleVoter
	public static class AuthorityNameConstants
	{
		private static final String ROLE_VOTER_PREFIX = "ROLE_";

		public static final String ADMIN = ROLE_VOTER_PREFIX + "ADMIN";
	}
}

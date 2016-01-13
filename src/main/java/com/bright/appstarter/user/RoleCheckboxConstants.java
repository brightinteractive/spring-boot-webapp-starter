package com.bright.appstarter.user;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class RoleCheckboxConstants
{
	public static final Map<String, String> ROLE_OPTIONS;

	static
	{
		final LinkedHashMap<String, String> roleOptions = new LinkedHashMap<>();

		for (Role role : Role.values())
		{
			roleOptions.put(role.toString(), role.getDisplayName());
		}

		ROLE_OPTIONS = Collections.unmodifiableMap(roleOptions);
	}
}

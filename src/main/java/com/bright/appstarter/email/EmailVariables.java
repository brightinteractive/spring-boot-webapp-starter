package com.bright.appstarter.email;

import java.util.Map;

public interface EmailVariables
{
	Map<String, Object> getSubjectMap();

	Map<String, Object> getBodyMap();
}

package com.bright.appstarter.email;

import java.util.Map;

public class ImmutableEmailVariablesImpl implements EmailVariables
{
	Map<String, Object> subjectMap;
	Map<String, Object> bodyMap;

	public ImmutableEmailVariablesImpl(Map<String, Object> subjectMap,
		Map<String, Object> bodyMap)
	{
		this.subjectMap = subjectMap;
		this.bodyMap = bodyMap;
	}

	@Override
	public Map<String, Object> getSubjectMap()
	{
		return subjectMap;
	}

	@Override
	public Map<String, Object> getBodyMap()
	{
		return bodyMap;
	}

}

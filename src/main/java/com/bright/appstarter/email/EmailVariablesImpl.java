package com.bright.appstarter.email;

import java.util.HashMap;
import java.util.Map;

public class EmailVariablesImpl implements EmailVariables
{
	Map<String, Object> subjectMap = new HashMap<String, Object>();
	Map<String, Object> bodyMap = new HashMap<String, Object>();

	public void addSubjectVar(String name, Object value)
	{
		subjectMap.put(name, value);
	}

	public void addBodyVar(String name, Object value)
	{
		bodyMap.put(name, value);
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

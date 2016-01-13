package com.bright.appstarter.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ThrowExceptionController
{
	public static final String URL = "/throw-exception";
	public static final String EXCEPTION_MESSAGE = "This is the exception message";

	@RequestMapping(path = URL, method = GET)
	public String get()
	{
		throw new RuntimeException(EXCEPTION_MESSAGE);
	}
}

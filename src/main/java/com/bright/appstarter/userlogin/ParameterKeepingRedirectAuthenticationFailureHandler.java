package com.bright.appstarter.userlogin;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import org.apache.http.client.utils.URIBuilder;

public class ParameterKeepingRedirectAuthenticationFailureHandler extends
	SimpleUrlAuthenticationFailureHandler
{
	private List<String> parametersToKeep = new ArrayList<>();
	private String defaultFailureUrl;

	public ParameterKeepingRedirectAuthenticationFailureHandler(String defaultFailureUrl,
		String... parametersToKeepOnRedirect)
	{
		super(defaultFailureUrl);
		this.defaultFailureUrl = defaultFailureUrl;

		for (String parameterToKeep : parametersToKeepOnRedirect)
		{
			this.parametersToKeep.add(parameterToKeep);
		}
	}

	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException
	{
		saveException(request, exception);
		getRedirectStrategy().sendRedirect(request, response, getRedirectUrlWithAddedParams(request));
	}

	public String getRedirectUrlWithAddedParams(HttpServletRequest request)
	{
		String url;
		try
		{
			URIBuilder uri = new URIBuilder(defaultFailureUrl);

			for (String parameterToKeep : parametersToKeep)
			{
				uri.addParameter(parameterToKeep, request.getParameter(parameterToKeep));
			}

			url = uri.build().toString();
		}
		catch (URISyntaxException e)
		{
			throw new ParameterKeepingUrlAuthenticationFailureHandlerException("The URI syntax is invalid");
		}

		return url;
	}

	public static class ParameterKeepingUrlAuthenticationFailureHandlerException extends RuntimeException
	{
		private static final long serialVersionUID = 40794123253832103L;

		public ParameterKeepingUrlAuthenticationFailureHandlerException(String message)
		{
			super(message);
		}
	}
}

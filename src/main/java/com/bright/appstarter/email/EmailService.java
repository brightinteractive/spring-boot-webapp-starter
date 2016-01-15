package com.bright.appstarter.email;


public interface EmailService
{
	static final String SUBJECT_TEMPLATE_PATH = "email/subject/";
	static final String BODY_TEMPLATE_PATH = "email/body/";

	void send(String to, String from, String subject, String text);

	void send(String to, String from, String templateName, EmailVariables variables);

	/**
	 * Send with from address loaded from config {@link #send(String, String,
	 * String, Map<String, Map<String, String>>) String to, String from, String
	 * templateName, Map<String, Map<String, String>> emailMap}
	 *
	 * @param to
	 * @param templateName
	 * @param emailMap
	 */
	void send(String to, String templateName, EmailVariables variables);

}

package com.bright.appstarter.email;

import static com.icegreen.greenmail.util.GreenMailUtil.*;
import static org.junit.Assert.*;

import javax.inject.Inject;
import javax.mail.Message.RecipientType;
import javax.mail.internet.MimeMessage;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.bright.appstarter.test.AppStarterIntegrationTest;

@RunWith(SpringJUnit4ClassRunner.class)
@AppStarterIntegrationTest
@WebAppConfiguration
@Transactional
public class FreeMarkerEmailServiceIntegrationTest
{
	@Rule
	public final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.SMTP);

	@Inject
	private FreeMarkerEmailService freeMarkerEmailService;

	@Test
	public void sendEmailSendsEmail() throws Exception
	{
		String to = "martin@bright-interactive.co.uk";
		String from = "noreply@noreply.com";
		String subject = "A subject";
		String text = "Hi Martin, here is your email";

		freeMarkerEmailService.send(to, from, subject, text);

		MimeMessage[] emails = greenMail.getReceivedMessages();
		assertEquals(1, emails.length);
		assertEquals(to, emails[0].getRecipients(RecipientType.TO)[0].toString());
		assertEquals(from, emails[0].getFrom()[0].toString());
		assertEquals(subject, emails[0].getSubject());
		assertEquals(text, getBody(emails[0]));
	}
}

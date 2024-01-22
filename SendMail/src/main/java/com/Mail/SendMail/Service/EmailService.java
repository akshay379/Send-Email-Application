package com.Mail.SendMail.Service;

import com.Mail.SendMail.Model.Email;

import jakarta.mail.MessagingException;

interface EmailService {

	public void sendEmail(Email email) throws MessagingException;
	          
	public void sendEmailAttachments(Email email) throws MessagingException;
}

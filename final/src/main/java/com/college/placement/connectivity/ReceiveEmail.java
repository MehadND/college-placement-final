package com.college.placement.connectivity;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import java.io.IOException;
import java.util.Properties;

public class ReceiveEmail
{
	//private boolean isLoggedIn = false;



	/*
	 * Method used to connect to email.
	 * Finds Attachments' Folder and loops through all its message(s).
	 * After each message is read it is checked for any attachments. Further actions are taken with the help from SavingAttachments
	 */
	public void receiveMail(String imapHost, String port, String sotreType, String user, String password)
	{

		//Get session object
		SavingAttachments savingAttach = new SavingAttachments();
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		Session sessEmail = Session.getInstance(props);
		//create imap store object
		Store store = null;
		try
		{
			//tries to connect store/email
			store = sessEmail.getStore("imaps");
			store.connect(imapHost, Integer.valueOf(port), user, password);

			//finds attachments' folder and open it
			Folder emailFolder = store.getFolder("Attachments");
			emailFolder.open(Folder.READ_ONLY);

			//Retrieve the messages in the folder and display it
			Message[] messages = emailFolder.getMessages();

			//loops through messages
			for (Message m : messages)
			{
				System.out.println("------------------------------------------------");
				System.out.println("Email Number : " + m.getMessageNumber());
				System.out.println("Subject : " + m.getSubject());
				System.out.println("From : " + m.getFrom());
				try
				{
					System.out.println("Subject : " + m.getContent().toString());
				} catch (IOException e)
				{
					System.out.println("No messages are available.............!");
					e.printStackTrace();
				}
				
				//checks for attachments, if any found then downloads it
				savingAttach.saveEmailAttachments(m.getContent());
			}

			//Closes the Folder and email session
			emailFolder.close(false);
			store.close();

		} catch (NoSuchProviderException e)
		{
			e.printStackTrace();

		} catch (MessagingException e)
		{
			System.out.println("Wrong Credentials");

		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}
	
	/*
	 * 
	 * public void processEmails(String email, String appPassword) { if (isLoggedIn
	 * == true) receiveMail(host, port, mailStoreType, email, appPassword); else
	 * System.out.println("Wrong Credentials"); }
	 * 
	 * public void startEmail(String email, String appPassword) { // Add Validator
	 * here to see if user credentials are correct. String Username =
	 * "forGroupTwo@outlook.com"; String Password = "dnoybmqrfsgvibll"; if
	 * (email.equals(Username) && appPassword.equals(Password)) isLoggedIn = true;
	 * else isLoggedIn = false;
	 * 
	 * System.out.println("Login Status = " + isLoggedIn); }
	 */
}

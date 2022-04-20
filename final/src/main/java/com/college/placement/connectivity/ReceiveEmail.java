//import com.sun.mail.pop3.POP3Store;
package com.college.placement.connectivity;

import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import java.io.IOException;
import java.util.Properties;

public class ReceiveEmail {

	public static boolean isLoggedIn = false;
	
	  String host="outlook.office365.com";
      int port=993;
      String mailStorType="imaps";
      
// method for Receive email.....!
  public void receiveMail(String pop3Host, int port, String sotreType,String user,String password)
  {


      ///   1) get session object
	  SavingAttachments savingAttach = new SavingAttachments();
      Properties props = new Properties();
      props.setProperty("mail.store.protocol", "imaps");
      Session sessEmail = Session.getInstance(props);
      Store store = null;
      // 2) create pop3 store object and connect with pop server
      try {
          store = sessEmail.getStore("imaps");
          store.connect(pop3Host, port, user, password);

          // 3) create Folder object and open it
          Folder emailFolder=store.getFolder("Attachments");
          emailFolder.open(Folder.READ_ONLY);

          //  4) Retrieve the messages in the folder and display it
          Message[] messages=emailFolder.getMessages();

          for(Message m : messages){
              System.out.println("------------------------------------------------");
              System.out.println("Email Number : "+m.getMessageNumber());
              System.out.println("Subject : "+m.getSubject());
              System.out.println("From : "+m.getFrom());
              try {
                  System.out.println("Subject : "+m.getContent().toString());
              } catch (IOException e) {
                  System.out.println("No messages are available.............!");
                  e.printStackTrace();
              }

          savingAttach.saveEmailAttachments(m.getContent());
          } // end for loop

          // 5) Close the Folder and email store
          emailFolder.close(false);
          store.close();

      } catch (NoSuchProviderException e) {
          e.printStackTrace();

      } catch (MessagingException e) {
    	  System.out.println("Wrong Credentials");

      } catch (IOException e) {
          e.printStackTrace();
      }


  }
  
  public void processEmails(String email,String appPassword)
  {
	  if(isLoggedIn == true)
    	  receiveMail(host, port, mailStorType, email, appPassword);
      else 
    	  System.out.println("Wrong Credentials");
  }
  
  
  public void startEmail(String email, String appPassword)
  {
	  //Add Validator here to see if user credentials are correct.
      String Username="forGroupTwo@outlook.com";
      String Password="dnoybmqrfsgvibll";
      if(email.equals(Username) && appPassword.equals(Password))
    	  isLoggedIn = true;
      else
    	  isLoggedIn = false;
      
      System.out.println("Login Status = "+isLoggedIn);
      
      //receiveMail(host, port, mailStorType, Username, Password);
      //guiView.getShowListOfEmail().setText(emailInfoText.get(0).toString());
      //System.out.println(emailInfoText.get(0).toString());
//      for(int e = 0; e<attachmentText.size();e++)
//      {
//      	//guiView.getShowListOfEmail().setText(attachmentText.get(e).toString()+"\n");
//      	System.out.println(attachmentText.get(e).toString());
//      }
  }
}

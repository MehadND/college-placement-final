package org.openjfx;

import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.college.placement.parsers.ParsePDFFiles;

//import dorkbox.notify.Notify;
//import dorkbox.notify.Pos;
//import dorkbox.util.ActionHandler;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controller 
{
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private String email = "forGroupTwo@outlook.com";
	private String password = "kvgrqaswszdsweve";
	
	private ParsePDFFiles pdfReader;

	public static boolean isLoggedIn = false;
	
	String host = "outlook.office365.com";
	String port = "993";
	String mailStorType = "imaps";
	
	private InputStream inputStream;
	private OutputStream outputStream;
	private String fileExtensionType;
	
	File attachmentsFolder = new File("Attachments");
	File savedFileLocation = new File("Exports");

	private JFileChooser fileChooser;

    Desktop d = null;

	@FXML
	private TextField emailAddressField;
	
	@FXML
	private PasswordField emailPasswordField;
	
	@FXML
	private Button loginButton;
	
	@FXML
	private Label statusLabel;
	
	@FXML
	private Label messageLabel;
	
	@FXML
	private Button browseFilesButton;
	
	@FXML
	private AnchorPane anchorPane;
	
	@FXML
	private TextArea showAttachmentsList;
	
	@FXML
	private Button processButton;
	
	@FXML
	private Button deleteAttachButton;

	@FXML
	private Button logoutButton;
	
	@FXML
	private Button openSavedButton;
	
	public TextArea getShowAttachmentsList() {
		return showAttachmentsList;
	}

	public void setShowAttachmentsList(TextArea showAttachmentsList) {
		this.showAttachmentsList = showAttachmentsList;
	}
	
	@FXML
	public void mouseEntering(){
//	   loginButton.setStyle("-fx-background-color:#ff7777; -fx-border-color: black; -fx-border-width:2; ");
//	   browseFilesButton.setStyle("-fx-background-color:#ff7777; -fx-border-color: black; -fx-border-width:2;");;
//	   openSavedButton.setStyle("-fx-background-color: #ff7777; -fx-border-color: black; -fx-border-width:2;");;
//	   

		
	   loginButton.setOnMouseEntered(new EventHandler<Event>() {
	        
			@Override
			public void handle(Event event) {
				// TODO Auto-generated method stub
		     	   loginButton.setStyle("-fx-background-color: #ff7777; -fx-text-fill: white; -fx-border-color: black; -fx-border-width:2;");;

			}
	        });
	   
	   browseFilesButton.setOnMouseEntered(new EventHandler<Event>() {
	        
			@Override
			public void handle(Event event) {
				// TODO Auto-generated method stub
		     	   browseFilesButton.setStyle("-fx-background-color: #ff7777; -fx-text-fill: white; -fx-border-color: black; -fx-border-width:2;");;

			}
	        });
	   
	   openSavedButton.setOnMouseEntered(new EventHandler<Event>() {
	        
			@Override
			public void handle(Event event) {
				// TODO Auto-generated method stub
		     	   openSavedButton.setStyle("-fx-background-color: #ff7777; -fx-text-fill: white; -fx-border-color: black; -fx-border-width:2;");;

			}
	        });
	}
	
	@FXML
	public void mouseExiting(){
	   loginButton.setStyle("-fx-background-color:null; -fx-border-color: black; -fx-border-width:2; ");
	   browseFilesButton.setStyle("-fx-background-color:null; -fx-border-color: black; -fx-border-width:2;");
	   openSavedButton.setStyle("-fx-background-color: null; -fx-border-color: black; -fx-border-width:2;");

	}
	
	

	public void switchToScene1(ActionEvent event) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("BeyondTravel Login");
		stage.show();
	}
	
	public void switchToScene2(ActionEvent event) throws IOException
	{
		
		if(emailAddressField.getText().equals(email) && emailPasswordField.getText().equals(password))
		{
			Parent root = FXMLLoader.load(getClass().getResource("/Application.fxml"));
			stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("BeyondTravel Application");
			stage.show();			
		}
		
		else 
		{
			statusLabel.setText("Login Unsuccessful");
		}
	}
	
	public void handleParsers()
	{
		System.out.println("Select a file to parse");
		try {
			pdfReader = new ParsePDFFiles();
			
//			if(Desktop.isDesktopSupported())
//			{
//				d = Desktop.getDesktop();
//			}
//        	try
//			{
//				d.open(attachmentsFolder);
//			} catch (IOException ioException) {
//				ioException.printStackTrace();
//			}
			
			pdfReader.readFile();
			boolean pdfFileSaved = pdfReader.writeChecker();
    		
    		
			if(pdfFileSaved == true)
				messageLabel.setText("File Saved");
    			//messageLabel.setText("File "+pdfReader.getFilesReadList().toString()+".xlsx saved");
			
			else 
				messageLabel.setText("File Not Saved");

			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void downloadAttachments() throws InvocationTargetException
	{
		System.out.println("Starting Processing...");
		try {
			 receiveMail(host, port, mailStorType, email, password);
		} catch (RuntimeException | MessagingException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
	}

	// method for Receive email.....!
	  public void receiveMail(String pop3Host, String port, String sotreType,String user,String password) throws MessagingException
	  {


	      ///   1) get session object
		  
	      Properties props = new Properties();
	      props.setProperty("mail.store.protocol", "imaps");
	      Session sessEmail = Session.getInstance(props);
	      Store store = null;
	      // 2) create pop3 store object and connect with pop server
	      try {
	          store = sessEmail.getStore("imaps");
	          store.connect(pop3Host, 993, user, password);

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

	          saveEmailAttachments(m.getContent());
	          } // end for loop

	          // 5) Close the Folder and email store
	          emailFolder.close(false);
	          store.close();

	      } catch (NoSuchProviderException e) {
	          e.printStackTrace();

	      }  catch (IOException e) {
	          e.printStackTrace();
	      }


	  }
	  
//	  public void processEmails(String email,String appPassword)
//	  {
//		  if(isLoggedIn == true)
//	    	  receiveMail(host, port, mailStorType, email, appPassword);
//	      else 
//	    	  System.out.println("Wrong Credentials");
//	  }
//
//	  public void startEmail(String email, String appPassword)
//	  {
//		  //Add Validator here to see if user credentials are correct.
//	      String Username="forGroupTwo@outlook.com";
//	      String Password="dnoybmqrfsgvibll";
//	      if(email.equals(Username) && appPassword.equals(Password))
//	      {
//	    	  isLoggedIn = true;
//	    	  processEmails(email, appPassword);
//	      }
//	      else
//	    	  isLoggedIn = false;
//	      
//	      System.out.println("Login Status = "+isLoggedIn);
//	      
//	      //receiveMail(host, port, mailStorType, Username, Password);
//	      //guiView.getShowListOfEmail().setText(emailInfoText.get(0).toString());
//	      //System.out.println(emailInfoText.get(0).toString());
////	      for(int e = 0; e<attachmentText.size();e++)
////	      {
////	      	//guiView.getShowListOfEmail().setText(attachmentText.get(e).toString()+"\n");
////	      	System.out.println(attachmentText.get(e).toString());
////	      }
//	  }
	  
	// method for saving attachents from emails into 'attachments' folder
		public void saveEmailAttachments(Object emailContent) throws MessagingException, IOException {
			try {
				pdfReader = new ParsePDFFiles();
				if (emailContent instanceof Multipart) {
					Multipart multipart = (Multipart) emailContent;
					int partsCounter = multipart.getCount();
					for (int i = 0; i < partsCounter; i++) {
						MimeBodyPart bodyPart = (MimeBodyPart) multipart.getBodyPart(i);
						// Does not process anything if email contains a part inside a part
						if (bodyPart.getContent() instanceof Multipart) {
							// saveEmailAttachments(bodyPart.getContent(), attachmentFileName);
						} else {
							/*
							 * if(bodyPart.isMimeType("text/html")) // { // fileExtensionType = "html"; // }
							 * // else // { // if(bodyPart.isMimeType("text/plain")) // { //
							 * fileExtensionType = "txt"; // } // else // { // fileExtensionType =
							 * bodyPart.getDataHandler().getName(); // }
							 */

							// NOTE: DO NOT REALLY NEED THESE FOLLOWING IF-ELSE
							// names file extension to pdf if the attachment type is PDF
							if (bodyPart.isMimeType("application/pdf")) {
								fileExtensionType = "pdf";
							} else {
								// if any other extension type then do not process it.
								// Ignore it
							}
							String attachmentFileName = "Attachments/" + bodyPart.getFileName();
							System.out.println("\nFilename.... " + attachmentFileName);
							showAttachmentsList.appendText("Filename: " + attachmentFileName + "\n");
							
							outputStream = new FileOutputStream(new File(attachmentFileName));
							inputStream = bodyPart.getInputStream();
							int k;
							while ((k = inputStream.read()) != -1) {
								outputStream.write(k);
							}
							pdfReader.readFiles(new File(attachmentFileName));
							// }
						}
					}
				}
			} finally {
				if (inputStream != null) {
					inputStream.close();
				}
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
			}
		}
		
		public void deleteFiles() throws MessagingException {
			System.out.println("Option Pane");

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete All Attachments");
			String s = "Are you sure!";
			alert.setContentText(s);
			 
			Optional<ButtonType> result = alert.showAndWait();
			 
			if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
			 
				delete(attachmentsFolder);
			}
			else 
			{
				showAttachmentsList.appendText("\n\n No File deleted.");
			}
		}
		
		public void delete(File folder)
		{
			File[] files = folder.listFiles();
    		System.out.println("\nStarting Files Deletion in Attachments Folder");
    		for(File file : files){
    			if(file.isFile()){
    				String fileName = file.getName();
    				boolean del= file.delete();
    				System.out.println(fileName + " : got deleted ? " + del);
    			}else if(file.isDirectory()) {
    				delete(file);
    			}
    		}
    		showAttachmentsList.appendText("\n\n All Attachments are deleted.");
		}
		
		public void openAttaches()
		{
			if(Desktop.isDesktopSupported())
			{
				d = Desktop.getDesktop();
			}
        	try
			{
				d.open(attachmentsFolder);
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}

		}
		
		public void openSaves()
		{
			if(Desktop.isDesktopSupported())
			{
				d = Desktop.getDesktop();
			}
        	try
			{
				d.open(savedFileLocation);
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}

		}


    public Controller()
    {
    	inputStream = null;
		outputStream = null;
		fileExtensionType = "";
    }
}

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

import com.college.placement.connectivity.ReceiveEmail;
import com.college.placement.connectivity.SavingAttachments;
import com.college.placement.connectivity.Utility;
import com.college.placement.parsers.PDF_Parser;
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

	private ParsePDFFiles pdfReader;
	private PDF_Parser parsingPDF;
	private ReceiveEmail receiveEmail;
	private SavingAttachments savingAttachments;

	private JFileChooser fileChooser;
	private Desktop d = null;

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
	@FXML
	private Button closeButton;

	/*
	 * Method that deals with mouse entering events for buttons
	 */
	@FXML
	public void mouseEntering()
	{
		loginButton.setOnMouseEntered(new EventHandler<Event>()
		{

			@Override
			public void handle(Event event)
			{
				// TODO Auto-generated method stub
				loginButton.setStyle(
						"-fx-background-color: #ff7777; -fx-text-fill: white; -fx-border-color: black; -fx-border-width:2;");
				;

			}
		});

		browseFilesButton.setOnMouseEntered(new EventHandler<Event>()
		{

			@Override
			public void handle(Event event)
			{
				// TODO Auto-generated method stub
				browseFilesButton.setStyle(
						"-fx-background-color: #ff7777; -fx-text-fill: white; -fx-border-color: black; -fx-border-width:2;");
				;

			}
		});

		openSavedButton.setOnMouseEntered(new EventHandler<Event>()
		{

			@Override
			public void handle(Event event)
			{
				// TODO Auto-generated method stub
				openSavedButton.setStyle(
						"-fx-background-color: #ff7777; -fx-text-fill: white; -fx-border-color: black; -fx-border-width:2;");
				;

			}
		});
	}

	/*
	 * Method that deals with mouse exiting events for buttons
	 */
	@FXML
	public void mouseExiting()
	{
		loginButton.setStyle("-fx-background-color:null; -fx-border-color: black; -fx-border-width:2; ");
		browseFilesButton.setStyle("-fx-background-color:null; -fx-border-color: black; -fx-border-width:2;");
		openSavedButton.setStyle("-fx-background-color: null; -fx-border-color: black; -fx-border-width:2;");

	}

	/*
	 * Method that switches scene to Login screen
	 */
	public void switchToLoginScreen(ActionEvent event) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("BeyondTravel Login");
		stage.show();
	}

	/*
	 * Method that switches scene to Application screen
	 */
	public void switchToApplicationScreen(ActionEvent event) throws IOException
	{

		if (emailAddressField.getText().equals(Utility.email) && emailPasswordField.getText().equals(Utility.password))
		{
			Parent root = FXMLLoader.load(getClass().getResource("/Application.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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

	/*
	 * Method used to handle 'Browse Files' button onClick After a file(s) is
	 * selected, then runs the parsers
	 */
	public void handleParsers()
	{
		System.out.println("Select a file to parse");
		try
		{
			pdfReader = new ParsePDFFiles();

			// modify my own pdf parsers so that it works with jack's
			pdfReader.readFile();
			boolean pdfFileSaved = pdfReader.writeChecker();

			if (pdfFileSaved == true)
				messageLabel.setText("File Saved");
			// messageLabel.setText("File "+pdfReader.getFilesReadList().toString()+".xlsx
			// saved");

			else
				messageLabel.setText("File Not Saved");

		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Method that reads emails. Also downloads attachments if any (attachments
	 * downloader is referenced in receiveEmail method)
	 */
	public void readEmailContent() throws InvocationTargetException
	{
		System.out.println("Starting Processing...");
		receiveEmail = new ReceiveEmail();
		receiveEmail.receiveMail(Utility.host, Utility.port, Utility.mailStoreType, Utility.email, Utility.password);

	}

	/*
	 * Method used to handle 'Delete Attachments' button onClick Shows an aleert box
	 * for confirmation
	 */
	public void deleteFiles() throws MessagingException
	{
		System.out.println("Option Pane");

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Attachment Deletion");
		String s = "Are you sure you want to delete all attachments?";
		alert.setContentText(s);

		Optional<ButtonType> result = alert.showAndWait();

		if ((result.isPresent()) && (result.get() == ButtonType.OK))
		{
			try
			{
				savingAttachments = new SavingAttachments();
				savingAttachments.deleteFiles(Utility.attachmentsFolder);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			showAttachmentsList.appendText("\n\n No File deleted.");
		}
	}

	/*
	 * Method that open locally created attachments folder on button click
	 */
	public void openAttaches()
	{
		if (Desktop.isDesktopSupported())
		{
			d = Desktop.getDesktop();
		}
		try
		{
			d.open(Utility.attachmentsFolder);
		} catch (IOException ioException)
		{
			ioException.printStackTrace();
		}

	}

	/*
	 * Method that open locally created exports/csv files folder on button click
	 */
	public void openSaves()
	{
		if (Desktop.isDesktopSupported())
		{
			d = Desktop.getDesktop();
		}
		try
		{
			d.open(Utility.savedFileLocation);
		} catch (IOException ioException)
		{
			ioException.printStackTrace();
		}

	}

	public void closeButton(ActionEvent event) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.close();
	}
	
	public void minimize(ActionEvent event) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setIconified(true);
	}
	
	public Controller()
	{
	}
}

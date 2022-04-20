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

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.college.placement.parsers.ParsePDFFiles;

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

public class Controller {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private final String email = "forGroupTwo@outlook.com";
	private final String password = "kvgrqaswszdsweve";
	private final File attachmentsFolder = new File("Attachments");
	private final File savedFileLocation = new File("Exports");
	
	private ParsePDFFiles pdfReader;
	
    Desktop d = null;
	
	@FXML
	private Button goToLoginButton;

	@FXML
	private Button goToAppButton;
	
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
	   
//	   browseFilesButton.setOnMouseEntered(new EventHandler<Event>() {
//	        
//			@Override
//			public void handle(Event event) {
//				// TODO Auto-generated method stub
//		     	   browseFilesButton.setStyle("-fx-background-color: #ff7777; -fx-text-fill: white; -fx-border-color: black; -fx-border-width:2;");;
//
//			}
//	        });
//	   
//	   openSavedButton.setOnMouseEntered(new EventHandler<Event>() {
//	        
//			@Override
//			public void handle(Event event) {
//				// TODO Auto-generated method stub
//		     	   openSavedButton.setStyle("-fx-background-color: #ff7777; -fx-text-fill: white; -fx-border-color: black; -fx-border-width:2;");;
//
//			}
//	        });
	}
	
	@FXML
	public void mouseExiting(){
	   loginButton.setStyle("-fx-background-color:null; -fx-border-color: black; -fx-border-width:2; ");
//	   browseFilesButton.setStyle("-fx-background-color:null; -fx-border-color: black; -fx-border-width:2;");
//	   openSavedButton.setStyle("-fx-background-color: null; -fx-border-color: black; -fx-border-width:2;");

	}
	
	public void switchToScene1(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("BeyondTravel Login");
		stage.show();
	}

	public void switchToScene2(ActionEvent event) throws IOException {
		/*
		 * if (emailAddressField.getText().equals(email) &&
		 * emailPasswordField.getText().equals(password)) { } else {
		 * statusLabel.setText("Login Unsuccessful"); }
		 */
		Parent root = FXMLLoader.load(getClass().getResource("/Application.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("BeyondTravel Application");
		stage.show();


	}
	
	/*
	 * public void manualParsers() { System.out.println("Select a file to parse");
	 * try { pdfReader = new ParsePDFFiles();
	 * 
	 * // if(Desktop.isDesktopSupported()) // { // d = Desktop.getDesktop(); // } //
	 * try // { // d.open(attachmentsFolder); // } catch (IOException ioException) {
	 * // ioException.printStackTrace(); // }
	 * 
	 * pdfReader.readFile(); boolean pdfFileSaved = pdfReader.writeChecker();
	 * 
	 * 
	 * if(pdfFileSaved == true) messageLabel.setText("File Saved");
	 * //messageLabel.setText("File "+pdfReader.getFilesReadList().toString()
	 * +".xlsx saved");
	 * 
	 * else messageLabel.setText("File Not Saved");
	 * 
	 * 
	 * 
	 * } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 * 
	 * public void openExports() { if(Desktop.isDesktopSupported()) { d =
	 * Desktop.getDesktop(); } try { d.open(savedFileLocation); } catch (IOException
	 * ioException) { ioException.printStackTrace(); } }
	 */
}

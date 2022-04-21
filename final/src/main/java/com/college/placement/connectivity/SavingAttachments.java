package com.college.placement.connectivity;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;

import org.openjfx.Controller;

import com.college.placement.parsers.ParsePDFFiles;

import java.io.*;

public class SavingAttachments {
	private InputStream inputStream;
	private OutputStream outputStream;
	private String fileExtensionType;
	
	ParsePDFFiles pdfFile;
	Controller controller;

	public SavingAttachments() {
		inputStream = null;
		outputStream = null;
		fileExtensionType = "";
	}

	// method for saving attachents from emails into 'attachments' folder
	public void saveEmailAttachments(Object emailContent) throws MessagingException, IOException {
		try {
			pdfFile = new ParsePDFFiles();
			controller = new Controller();
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
						controller.getShowAttachmentsList().appendText("Filename: " + attachmentFileName + "\n");
						outputStream = new FileOutputStream(new File(attachmentFileName));
						inputStream = bodyPart.getInputStream();
						int k;
						while ((k = inputStream.read()) != -1) {
							outputStream.write(k);
						}
						pdfFile.readFiles(new File(attachmentFileName));
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
	
	public void deleteFiles(File folder) throws IOException {
	    File[] files = folder.listFiles();
	    System.out.println("\nStarting Files Deletion in Attachments Folder");
	     for(File file: files){
	            if(file.isFile()){
	                String fileName = file.getName();
	                boolean del= file.delete();
	                System.out.println(fileName + " : got deleted ? " + del);
	            }else if(file.isDirectory()) {
	                deleteFiles(file);
	            }
	        }
	    }
}

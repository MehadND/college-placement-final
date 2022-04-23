package com.college.placement.connectivity;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;

import org.openjfx.Controller;

import com.college.placement.parsers.PDF_Parser;
import com.college.placement.parsers.ParsePDFFiles;

import java.io.*;

public class SavingAttachments
{
	private InputStream inputStream;
	private OutputStream outputStream;
	private String fileExtensionType;

	ParsePDFFiles pdfFile;
	Controller controller;
	private PDF_Parser parsingPDF;

	String user = System.getProperty("user.name");
	File attachmentsFolder = new File("C:/Users/" + user + "/Documents/attachmentsFolder");
	File savedFileLocation = new File("C:/Users/" + user + "/Documents/CSV_Files");

	public SavingAttachments()
	{
		inputStream = null;
		outputStream = null;
		fileExtensionType = "";
	}

	/*
	 * Method used to save attachments.
	 */
	public void saveEmailAttachments(Object emailContent) throws MessagingException, IOException
	{
		try
		{
			pdfFile = new ParsePDFFiles();
			// controller = new Controller();
			if (emailContent instanceof Multipart)
			{
				Multipart multipart = (Multipart) emailContent;
				int partsCounter = multipart.getCount();
				for (int i = 0; i < partsCounter; i++)
				{
					MimeBodyPart bodyPart = (MimeBodyPart) multipart.getBodyPart(i);
					// Does not process anything if email contains a part inside a part
					if (bodyPart.getContent() instanceof Multipart)
					{
						// saveEmailAttachments(bodyPart.getContent(), attachmentFileName);
					}
					else
					{
						// checks if attachments is pdf, if so then runs parse pdf method
						if (bodyPart.isMimeType("application/pdf"))
						{
							fileExtensionType = "pdf";
						}
						// maybe could process docx attachments
						else
						{
							// if any other extension type then do not process it.
							// Ignore it
						}
						String attachmentFileName = attachmentsFolder + "/" + bodyPart.getFileName();
						System.out.println("\nFilename.... " + attachmentFileName);
						// showAttachmentsList.appendText("\nFilename: " + attachmentFileName);
						outputStream = new FileOutputStream(new File(attachmentFileName));
						inputStream = bodyPart.getInputStream();
						int k;
						while ((k = inputStream.read()) != -1)
						{
							outputStream.write(k);
						}
						parsingPDF.parsePDF(attachmentFileName);
						// }
					}
				}
			}
		} finally
		{
			if (inputStream != null)
			{
				inputStream.close();
			}
			if (outputStream != null)
			{
				outputStream.flush();
				outputStream.close();
			}
		}
	}

	/*
	 * Method that deletes files in local attachments folders. Gets the Folder and
	 * loops through all the files in the folder. Then deletes file one by one.
	 */
	public void deleteFiles(File folder) throws IOException
	{
		File[] files = folder.listFiles();
		System.out.println("\nStarting Files Deletion in Attachments Folder");
		for (File file : files)
		{
			if (file.isFile())
			{
				String fileName = file.getName();
				boolean del = file.delete();
				System.out.println(fileName + " : got deleted ? " + del);
			}
			else if (file.isDirectory())
			{
				deleteFiles(file);
			}
		}
	}
}

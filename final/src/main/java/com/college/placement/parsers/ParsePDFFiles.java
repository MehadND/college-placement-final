package com.college.placement.parsers;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.college.placement.connectivity.Utility;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ParsePDFFiles extends PDFTextStripper
{
	static List<String> words = new ArrayList<String>();

//    private static String myCompanyName = "";
//    private static String customerCompanyName = "";
//    private static String myCompanyAddress = "";
//    private static String customerCompanyAddress = "";
//    private static String invoiceDate = "";
//    private static String carDescription = "";
//    private static String rentDate = "";
//    private static String returnDate = "";
//    private static String termsOfPayment = "";
//    private static String accountNumber = "";
//    private static long rentalAgreementNumber = 0;
//    private static String reservationID = "";
//    private static String renter = "";
//    private static double invoiceSubTotalAmount = 0;
//    private static String invoiceTotalPayAmount = "";
//    private static String rentingCompany = "";
//    private static String idAttribute = "";
//    private static String valueAttribute = "";

	private static PDDocument document = null;
	private String extensionRemoved = "";
	private JFileChooser fileChooser;
	private boolean isSaved = false;
	private List<String> filesReadList = new ArrayList<String>();



	/**
	 * Instantiate a new PDFTextStripper object.
	 *
	 * @throws IOException If there is an error loading the properties.
	 */
	public ParsePDFFiles() throws IOException
	{
	}

	public void readFile() throws IOException
	{
		filesReadList.clear();

		File[] fileName = {};

		// Using this process to invoke the constructor,
// JFileChooser points to user's default directory
		fileChooser = new JFileChooser(Utility.attachmentsFolder);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");
		fileChooser.setFileFilter(filter);
		fileChooser.setMultiSelectionEnabled(true);
// Open the save dialog
		int r = fileChooser.showOpenDialog(null);

		// if the user selects a file
		if (r == JFileChooser.APPROVE_OPTION)

		{
			// set the label to the path of the selected file
			fileName = (fileChooser.getSelectedFiles());
		}

		for (File f : fileName)
		{
			filesReadList.add(f.getName());
			// readFiles(f);
		}
	}

	/*
	 * public void readFiles(File f) throws IOException { extensionRemoved =
	 * f.getName().split("\\.")[0]; System.out.println("Processing file..." +
	 * extensionRemoved); words.clear(); try { document = PDDocument.load(f);
	 * PDFTextStripper stripper = new ParsePDFFiles();
	 * stripper.setSortByPosition(true); stripper.setStartPage(0);
	 * stripper.setEndPage(document.getNumberOfPages());
	 * 
	 * Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
	 * stripper.writeText(document, dummy); int index = 0;
	 * 

	/*
	 * 
	 * // loops through all the words in words (ArrayList) and then gets relevant
	 * info // based on words found (see switch statements) for (String word :
	 * words) {
	 * 
	 * switch (word) { // Not sure about this because this coul change and it means
	 * the following code // for payments date will also have to change case
	 * "www.hertzonlinepayments.com/gb/en": index = words.indexOf(word);
	 * valueAttribute = words.get(index + 1).toString().substring(19, 26); //
	 * System.out.println(); // System.out.println("<------------------------>"); //
	 * System.out.print("Terms of Payment: "); termsOfPayment = valueAttribute; //
	 * System.out.println(idAttribute + " " + valueAttribute); break; case
	 * "HERTZ UK LTD": index = words.indexOf(word); valueAttribute =
	 * words.get(index).toString(); // System.out.println(); //
	 * System.out.println("<------------------------>"); //
	 * System.out.print("My Company Name: "); myCompanyName = valueAttribute; //
	 * .out.println(idAttribute + " " + valueAttribute); break;
	 * 
	 * case "PLEASE PAY TO:": index = words.indexOf(word); valueAttribute =
	 * words.get(index + 6).toString() + ", " + words.get(index + 10).toString() +
	 * ", " + words.get(index + 12).toString().substring(0, 27) + ", " +
	 * words.get(index + 16).toString(); System.out.println();
	 * System.out.println("<------------------------>");
	 * System.out.print("My Company Address: "); myCompanyAddress = valueAttribute;
	 * System.out.println(idAttribute + " " + valueAttribute); break;
	 * 
	 * case "Invoice Date:": index = words.indexOf(word); valueAttribute =
	 * words.get(index + 1).toString(); // System.out.println(); //
	 * System.out.println("<------------------------>"); //
	 * System.out.print("Invoice Date: "); invoiceDate = valueAttribute; //
	 * System.out.println(idAttribute + " " + valueAttribute); break; case
	 * "Car Description:": index = words.indexOf(word); valueAttribute =
	 * words.get(index + 1).toString(); // System.out.println(); //
	 * System.out.println("<------------------------>"); //
	 * System.out.print("Car Description: "); carDescription = valueAttribute; //
	 * System.out.println(idAttribute + " " + valueAttribute); break; case
	 * "Rented On:": index = words.indexOf(word); valueAttribute = words.get(index +
	 * 1).toString(); // System.out.println(); //
	 * System.out.println("<------------------------>"); //
	 * System.out.print("Rented On: "); rentDate = valueAttribute; //
	 * System.out.println(idAttribute + " " + valueAttribute); break; case
	 * "Returned On:": index = words.indexOf(word); valueAttribute = words.get(index
	 * + 1).toString(); // System.out.println(); //
	 * System.out.println("<------------------------>"); //
	 * System.out.print("Returned On: "); returnDate = valueAttribute; //
	 * System.out.println(idAttribute + " " + valueAttribute); break; case
	 * "Account No.:": index = words.indexOf(word); valueAttribute = words.get(index
	 * + 1).toString(); // System.out.println(); //
	 * System.out.println("<------------------------>"); //
	 * System.out.print("Account No: "); accountNumber = valueAttribute; //
	 * System.out.println(idAttribute + " " + valueAttribute); break; case
	 * "Rental Agreement No:": index = words.indexOf(word); valueAttribute =
	 * words.get(index + 1).toString(); // System.out.println(); //
	 * System.out.println("<------------------------>"); //
	 * System.out.print("Rental Agreement No: "); rentalAgreementNumber =
	 * Integer.parseInt(valueAttribute); // System.out.println(idAttribute + " " +
	 * valueAttribute); break; case "Renting Company:": index = words.indexOf(word);
	 * valueAttribute = words.get(index + 4).toString(); // System.out.println(); //
	 * System.out.println("<------------------------>"); //
	 * System.out.print("Renting Company: "); rentingCompany = valueAttribute;
	 * customerCompanyName = valueAttribute; // System.out.println(idAttribute + " "
	 * + valueAttribute); break; case "Reservation ID:": index =
	 * words.indexOf(word); valueAttribute = words.get(index + 1).toString(); //
	 * System.out.println(); // System.out.println("<------------------------>"); //
	 * System.out.print("Reservation ID: "); reservationID = valueAttribute; //
	 * System.out.println(idAttribute + " " + valueAttribute); break; case
	 * "Renter:": index = words.indexOf(word); valueAttribute = words.get(index +
	 * 1).toString(); // System.out.println(); //
	 * System.out.println("<------------------------>"); //
	 * System.out.print("Renter: "); renter = valueAttribute; //
	 * System.out.println(idAttribute + " " + valueAttribute); break; case
	 * "SUBTOTAL": index = words.indexOf(word); valueAttribute = words.get(index +
	 * 1).toString(); // System.out.println(); //
	 * System.out.println("<------------------------>"); //
	 * System.out.print("Invoice Amount: "); invoiceSubTotalAmount =
	 * Double.parseDouble(valueAttribute); // System.out.println(idAttribute + " " +
	 * valueAttribute); break; case "Please Pay:": index = words.indexOf(word);
	 * valueAttribute = words.get(index + 1).toString().substring(0, 6);
	 * invoiceTotalPayAmount = valueAttribute; break; } }
	 * 
	 * // Writing output to console
	 * System.out.println("<-------------------------------->");
	 * System.out.println("        INVOICE");
	 * 
	 * System.out.println("My Company Name: " + myCompanyName);
	 * System.out.println("Customer Company: " + customerCompanyName);
	 * System.out.println("Renting Company: " + rentingCompany);
	 * System.out.println("Invoice Date: " + invoiceDate);
	 * System.out.println("Account No: " + accountNumber);
	 * System.out.println("Rental Agreement No: " + rentalAgreementNumber);
	 * System.out.println("Reservation ID: " + reservationID);
	 * System.out.println("Renter: " + renter);
	 * System.out.println("Car Description: " + carDescription);
	 * System.out.println("Rented On: " + rentDate);
	 * System.out.println("Returned On: " + returnDate);
	 * System.out.println("Terms of Payment: " + termsOfPayment);
	 * System.out.println("Invoice Amount: " + invoiceSubTotalAmount);
	 * System.out.println("Please Pay: " + invoiceTotalPayAmount);
	 * 
	 * System.out.println("<-------------------------------->");
	 * System.out.println();
	 * 
	 * writeAsExcelFile(f, extensionRemoved);
	 * 
	 * System.out.println("Finished Parsing " + f.getName());
	 * 
	 * myCompanyName = ""; customerCompanyName = ""; // private static String
	 * myCompanyAddress = ""; // private static String customerCompanyAddress = "";
	 * invoiceDate = ""; carDescription = ""; rentDate = ""; returnDate = "";
	 * termsOfPayment = "";
	 * 
	 * accountNumber = ""; rentalAgreementNumber = 0; reservationID = ""; renter =
	 * ""; invoiceSubTotalAmount = 0; invoiceTotalPayAmount = ""; rentingCompany =
	 * ""; valueAttribute = ""; } finally { if (document != null) {
	 * document.close(); } else { document.close(); } } }
	 * 
	 */ 

	/*
	 * public void writeAsExcelFile(File f, String sheetName) { isSaved = false; //
	 * Writes to an excel file (using apache.poi/tika) // workbook XSSFWorkbook
	 * workbook = new XSSFWorkbook();
	 * 
	 * // spreadsheet XSSFSheet spreadsheet = workbook.createSheet(sheetName);
	 * 
	 * // creating a row XSSFRow row = null;
	 * 
	 * Map<String, Object[]> studentData = new TreeMap<String, Object[]>();
	 * 
	 * // save to file try { studentData.put("1", new Object[] { "Car Provider",
	 * "Customer Company", "Invoice Date", "Account No", "Rental Agreement No",
	 * "Reservation ID", "Renter", "Car Description", "Rented On", "Returned On",
	 * "Terms of Payment", "Invoice Amount" }); studentData.put("2", new Object[] {
	 * myCompanyName, customerCompanyName, invoiceDate, accountNumber,
	 * rentalAgreementNumber, reservationID, renter, carDescription, rentDate,
	 * returnDate, termsOfPayment, invoiceSubTotalAmount }); // studentData.put("3",
	 * new Object[]{"Customer Company", customerCompanyName}); //
	 * studentData.put("4", new Object[]{"Renting Company", myCompanyName}); //
	 * studentData.put("5", new Object[]{"Invoice Date", invoiceDate}); //
	 * studentData.put("6", new Object[]{"Account No", accountNumber}); //
	 * studentData.put("7", new Object[]{"Rental Agreement No",
	 * rentalAgreementNumber}); // studentData.put("8", new
	 * Object[]{"Reservation ID", reservationID}); // studentData.put("9", new
	 * Object[]{"Renter", renter}); // studentData.put("10", new
	 * Object[]{"Car Description", carDescription}); // studentData.put("11", new
	 * Object[]{"Rented On", rentDate}); // studentData.put("12", new
	 * Object[]{"Returned On", returnDate}); // studentData.put("13", new
	 * Object[]{"Terms of Payment", termsOfPayment}); // studentData.put("14", new
	 * Object[]{"Invoice Amount", invoiceSubTotalAmount}); // studentData.put("15",
	 * new Object[]{"Please Pay", invoiceTotalPayAmount}); // studentData.put("16",
	 * new Object[]{"Please Pay", invoiceTotalPayAmount});
	 * 
	 * Set<String> keyid = studentData.keySet();
	 * 
	 * int rowid = 0;
	 * 
	 * // writing the data into the excel sheet
	 * 
	 * for (String key : keyid) {
	 * 
	 * row = spreadsheet.createRow(rowid++); Object[] objectArr =
	 * studentData.get(key); int cellid = 0;
	 * 
	 * for (Object obj : objectArr) { Cell cell = row.createCell(cellid++);
	 * cell.setCellValue(String.valueOf(obj)); } makeRowBold(workbook,
	 * spreadsheet.getRow(0)); for (int i = 0; i < row.getLastCellNum(); i++) {//
	 * For each cell in the row spreadsheet.autoSizeColumn(i); } }
	 * 
	 * // writing the data into the file FileOutputStream out = new
	 * FileOutputStream(new File(savedFileLocation + "/" + sheetName + ".xlsx"));
	 * 
	 * workbook.write(out); out.close(); isSaved = true;
	 * System.out.println("Successfully wrote to the Excel file."); writeChecker();
	 * for (int i = 0; i < filesReadList.size(); i++) {
	 * System.out.println(filesReadList.get(i).toString()); }
	 * 
	 * } catch (IOException e) { System.out.println("An error occurred."); isSaved =
	 * false; writeChecker();
	 * 
	 * e.printStackTrace(); }
	 * 
	 * }
	 */

	public static void makeRowBold(Workbook wb, Row row)
	{
		CellStyle style = wb.createCellStyle();// Create style
		Font font = wb.createFont();// Create font
		font.setBold(true);// Make font bold
		style.setFont(font);// set it to bold

		for (int i = 0; i < row.getLastCellNum(); i++)
		{// For each cell in the row
			row.getCell(i).setCellStyle(style);// Set the sty;e

		}
	}

	public boolean writeChecker()
	{
		return isSaved;
	}

	/*
	 * public boolean writeAsTextFile() { boolean isSaved = false; fileChooser = new
	 * JFileChooser("Exports"); if (fileChooser.showSaveDialog(null) ==
	 * JFileChooser.APPROVE_OPTION) { File file = fileChooser.getSelectedFile(); //
	 * save to file // writes the relevant info to .txt file try { FileWriter
	 * myWriter = new FileWriter(fileChooser.getSelectedFile());
	 * 
	 * myWriter.write("My Company Name: " + myCompanyName + "\nCustomer Company: " +
	 * customerCompanyName + "\nRenting Company: " + rentingCompany +
	 * "\nInvoice Date: " + invoiceDate + "\nAccount No: " + accountNumber +
	 * "\nRental Agreement No: " + rentalAgreementNumber + "\nReservation ID: " +
	 * reservationID + "\nRenter: " + renter + "\nCar Description: " +
	 * carDescription + "\nRented On: " + rentDate + "\nReturned On: " + returnDate
	 * + "\nTerms of Payment: " + termsOfPayment + "\nInvoice Amount: " +
	 * invoiceSubTotalAmount + "\nPlease Pay: " + invoiceTotalPayAmount);
	 * myWriter.close(); isSaved = true;
	 * System.out.println("Successfully wrote to the file."); } catch (IOException
	 * e) { isSaved = false; System.out.println("An error occurred.");
	 * e.printStackTrace(); } } return isSaved; }
	 */

//    public void writeToFile()
//    {
//    	JFileChooser fileChooser = new JFileChooser("Exports");
//    	if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
//    	  File file = fileChooser.getSelectedFile();
//    	  // save to file
//    	  //writes the relevant info to .txt file
//    	  try {
//    		  FileWriter myWriter = new FileWriter(fileChooser.getSelectedFile());
//    		  
//    		  myWriter.write("My Company Name: " + myCompanyName +
//    				  "\nCustomer Company: " + customerCompanyName +
//    				  "\nRenting Company: " + rentingCompany +
//    				  "\nInvoice Date: " + invoiceDate +
//    				  "\nAccount No: " + accountNumber +
//    				  "\nRental Agreement No: " + rentalAgreementNumber +
//    				  "\nReservation ID: " + reservationID +
//    				  "\nRenter: " + renter +
//    				  "\nCar Description: " + carDescription +
//    				  "\nRented On: " + rentDate +
//    				  "\nReturned On: " + returnDate +
//    				  "\nTerms of Payment: " + termsOfPayment +
//    				  "\nInvoice Amount: " + invoiceSubTotalAmount +
//    				  "\nPlease Pay: " + invoiceTotalPayAmount);
//    		  myWriter.close();
//    		  System.out.println("Successfully wrote to the file.");
//    	  } catch (IOException e) {
//    		  System.out.println("An error occurred.");
//    		  e.printStackTrace();
//    	  }
//    	  
//    	//Writes to an excel file (using apache.poi/tika)
//          // workbook
//          XSSFWorkbook workbook = new XSSFWorkbook();
//
//          // spreadsheet
//          XSSFSheet spreadsheet
//                  = workbook.createSheet(" Student Data ");
//
//          // creating a row
//          XSSFRow row;
//
//          Map<String, Object[]> studentData
//                  = new TreeMap<String, Object[]>();
//
//          try {
//              studentData.put("1", new Object[]{"ID Attribute", "Value Attribute"});
//              studentData.put("2", new Object[]{"My Company Name", myCompanyName});
//              studentData.put("3", new Object[]{"Customer Company", customerCompanyName});
//              studentData.put("4", new Object[]{"Renting Company", myCompanyName});
//              studentData.put("5", new Object[]{"Invoice Date", invoiceDate});
//              studentData.put("6", new Object[]{"Account No", accountNumber});
//              studentData.put("7", new Object[]{"Rental Agreement No", rentalAgreementNumber});
//              studentData.put("8", new Object[]{"Reservation ID", reservationID});
//              studentData.put("9", new Object[]{"Renter", renter});
//              studentData.put("10", new Object[]{"Car Description", carDescription});
//              studentData.put("11", new Object[]{"Rented On", rentDate});
//              studentData.put("12", new Object[]{"Returned On", returnDate});
//              studentData.put("13", new Object[]{"Terms of Payment", termsOfPayment});
//              studentData.put("14", new Object[]{"Invoice Amount", invoiceSubTotalAmount});
//              studentData.put("15", new Object[]{"Please Pay", invoiceTotalPayAmount});
//              studentData.put("16", new Object[]{"Please Pay", invoiceTotalPayAmount});
//
//              Set<String> keyid = studentData.keySet();
//
//              int rowid = 0;
//
//              // writing the data into the excel sheet
//
//              for (String key : keyid) {
//
//                  row = spreadsheet.createRow(rowid++);
//                  Object[] objectArr = studentData.get(key);
//                  int cellid = 0;
//
//                  for (Object obj : objectArr) {
//                      Cell cell = row.createCell(cellid++);
//                      cell.setCellValue(String.valueOf(obj));
//                  }
//              }
//
//              // writing the data into the file
//              FileOutputStream out = new FileOutputStream(
//                      new File(fileChooser.getSelectedFile().getAbsolutePath()+".xlsx"));
//
//              workbook.write(out);
//              out.close();
//              System.out.println("Successfully wrote to the Excel file.");
//          } catch (IOException e) {
//              System.out.println("An error occurred.");
//              e.printStackTrace();
//          }
//    	}
//    }

//    public static void main(String[] args) throws IOException
//    {
//        //reading file
//        ParsePDFFiles readingData = new ParsePDFFiles();
//        String fileName = "files/sample.pdf";
//        readingData.readFile();
//
//        //writes to file
//        ParsePDFFiles writingData = new ParsePDFFiles();
//        writingData.writeToFile();
//
//        //Suggestion: call readFile() method inside writeToFile() method so that in main only need to call one method..
//    }

	/**
	 * Override the default functionality of PDFTextStripper.writeString() Adds
	 * words read in from PDF into ArrayList
	 */
	@Override
	protected void writeString(String str, List<TextPosition> textPositions) throws IOException
	{
		String[] wordsInStream = str.split(String.valueOf(getSeparateByBeads()));
		if (wordsInStream != null)
		{
			for (String word : wordsInStream)
			{
				words.add(word);
			}
		}
	}

	public JFileChooser getFileChooser()
	{
		return fileChooser;
	}

	public String getExtensionRemoved()
	{
		return extensionRemoved;
	}

	public List<String> getFilesReadList()
	{
		return filesReadList;
	}

}
package com.college.placement.connectivity;

import java.io.File;

/**
 * This is a class which has some static variables that some classes need.
 * @author mehad
 *
 */
public class Utility
{
	public static String user = System.getProperty("user.name");
	public static File attachmentsFolder = new File("C:/Users/" + user + "/Documents/attachmentsFolder");
	public static File savedFileLocation = new File("C:/Users/" + user + "/Documents/CSV_Files");
	
	public static String host = "outlook.office365.com";
	public static String port = "993";
	public static String mailStoreType = "imaps";
	
	public static String email = "forGroupTwo@outlook.com";
	public static String password = "kvgrqaswszdsweve";
}

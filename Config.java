//*******************************************************************
//QuizProgram by Joseph O'Flanagan
//This is a multi-use tool designed to display quizzes of any sort,
//from specialist interest quizzes, family quizzes and even school
//exams, this program is designed to read files and deliver the content
//in an easily digestible manner and allow
//Version 1 Initialised Program
//*******************************************************************

import java.util.Scanner;
import java.lang.Math;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// one class needs to have a main() method
public class Config
{

	Properties configFile;

	String directory = "";

	InputStream inputStream;
	File[] config_contents;

	public Config() 
	{
		super();
	}

	//This class will get the directory from the configuration file

	public void getPropValues() throws IOException 
	{
		//We need to find the configuration file first, it needs to be read from a place which the user can easily edit
		boolean fileExists = false;
		File configLocation = new File (System.getProperty("user.dir"));
		String propFileName = "config.properties";
		//search the directory that the program is stored in for a config file
		config_contents = configLocation.listFiles();
		for (int i = 0; i < config_contents.length; i++)
		{
			if ((config_contents[i].getName().equals(propFileName))) 
			{
				//We have found the configuration file
				fileExists = true;
			}
		}
	        if (fileExists == false) 
        	{
			//With no config file found, the program will use it's in-built file instead.
			System.out.println("No config file in directory, reading the in-built file instead");
			try
			{
				String propFileName2 = "resources/config.properties";
				inputStream = getClass().getClassLoader().getResourceAsStream(propFileName2);
            		} 
		        catch (Exception exc) 
			{
				// handle exception, e.g. log and warn user config could not be created
				throw new FileNotFoundException("Config file " + propFileName + " not found");
			}
	        }
		else 
		{
			//We have a config file so let the user know that this is what is being used and apply that file to the input stream
			System.out.println("Reading the configuration file in directory");
			try 
			{
				inputStream = new FileInputStream(configLocation + "/" + propFileName);
			}
			catch (Exception ext) 
			{
				// handle exception, e.g. log and warn user config could not be created
				throw new FileNotFoundException("Config file " + propFileName + " not found");
			}
		}
		//Use the input stream to configure the file
		try
		{
			configFile = new Properties();
			//If the config file is loaded then this shouldn't be Null
			if (inputStream != null) 
			{
				configFile.load(inputStream);    
			}
			else 
			{
				//Should the file not be found, an exception needs to be thrown.
				throw new FileNotFoundException("Config file " + propFileName + " not found");
			}
			//Assign the directory file
			directory = configFile.getProperty("directory");
	        }
		//Should the process fail at any moment, catch the exception.

		catch (Exception e) 
		{
			System.out.println("Unable to configure values. Please ensure that the configuration file " + propFileName + " has the correct values");
			System.exit(1);
		}
		finally 
		{
			inputStream.close();
		}
	}
	public String directory() 
	{
		return this.directory;
	}
}
	
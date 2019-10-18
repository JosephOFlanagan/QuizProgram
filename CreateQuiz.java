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
public class CreateQuiz
{
	String[] questions;
	String[] questionTypes;
	List<String[]> multiChoiceAnswers;
	String[] mCAnswers;
	String[] answers;
	Config config;

	public CreateQuiz() 
	{
		super();
	}
	public void setQuiz() throws FileNotFoundException, IOException
	{
	        config = new Config();
		try
		{
	        	config.getPropValues();
		}
		catch (IOException ex) 
                {
			System.out.println("Failed to create quiz");
			System.exit(0);
		}
		int lineCount = 0;
		String line;
		BufferedReader buffReader = new BufferedReader(new FileReader(config.directory));
		int questionCount = 0;
		int multiChoiceCount = 0;
		
		boolean answerCheck = false;
		while ((line = buffReader.readLine()) != null)
		{
			Pattern pQuest = Pattern.compile("\\?", Pattern.CASE_INSENSITIVE);
			Matcher mQuest = pQuest.matcher(line);
			if (mQuest.find())
			{
				if (answerCheck == true)
				{
					mCAnswers = new String[multiChoiceCount];
					
					answerCheck = false;
				}
				questionCount = questionCount + 1;
			}
			else
			{
				answerCheck = true;
			}
			if (answerCheck == true)
			{
				multiChoiceCount = multiChoiceCount + 1;
			}
		}
		questions = new String[questionCount];
		questionTypes = new String[questionCount];
		answers = new String[questionCount];
		BufferedReader bReader = new BufferedReader(new FileReader(config.directory));
		int questionCounter = 1;
		boolean midQuestion = false;
		boolean answersSection = false;
		int numberOfMultiChoiceAnswers = 1;
		List <String> mCA = new ArrayList<String>();
		multiChoiceAnswers = new ArrayList<String[]>();
		while ((line = bReader.readLine()) != null)
		{
			//declare variable to only be used here
			lineCount++;
			int numberCheck = -1;
			int questionMarkCheck = -1;
			int multiChoiceCheck = -1;
			int multiChoiceCorrectCheck = -1;
			Pattern pCheck1 = Pattern.compile(questionCounter + "\\.\\s*", Pattern.CASE_INSENSITIVE);
			Matcher mCheck1 = pCheck1.matcher(line);
			Pattern pCheck2 = Pattern.compile("\\?", Pattern.CASE_INSENSITIVE);
			Matcher mCheck2 = pCheck2.matcher(line);
			Pattern pCheck3 = Pattern.compile("\\w\\)(.)*", Pattern.CASE_INSENSITIVE);
			Matcher mCheck3 = pCheck3.matcher(line);
			Pattern pCheck3a = Pattern.compile("\\w\\.(.)*", Pattern.CASE_INSENSITIVE);
			Matcher mCheck3a = pCheck3a.matcher(line);
			Pattern pCheck4 = Pattern.compile("\\w\\$", Pattern.CASE_INSENSITIVE);
			Matcher mCheck4 = pCheck4.matcher(line);
			if (mCheck1.find())
			{
				numberCheck = lineCount;
			}
			if (mCheck2.find())
			{
				if (answersSection == true)
				{
					questionCounter = questionCounter + 1;
				}
				answersSection = false;
				questionMarkCheck = lineCount;
			}
			else if (answersSection == true)
			{
				midQuestion = false;
			}
			if (mCheck3.find())
			{
				if (answersSection == true)
				{
					multiChoiceCheck = lineCount;
				}
			}
			if (mCheck3a.find())
			{
				if (answersSection == true)
				{
					multiChoiceCheck = lineCount;
            			}
			}
			if (mCheck4.find())
			{
              			if (answersSection == true)
                		{
					multiChoiceCorrectCheck = lineCount;
				}
			}
			if (midQuestion == false)
			{
				if (numberCheck > -1)
				{
					questions[questionCounter - 1] = line.substring(line.length() - 2);
				}
				else if (answersSection == false)
				{
					questions[questionCounter - 1] = line;
				}
				if (questionMarkCheck == -1)
				{
					//Testing reveals this switch triggers on the answers section which naturally has no question mark
					if (answersSection == false)
					midQuestion = true;
				}
			}
			else
			{
				String totalQuestion = questions[questionCounter -1];
				totalQuestion = totalQuestion + line;
				questions[questionCounter - 1] = totalQuestion;
				if (questionMarkCheck > -1)
				{
					midQuestion = false;
				}
			}
			if (answersSection == true)
			{
				if (multiChoiceCheck > -1)
				{
					questionTypes[questionCounter - 1] = "Multi-Choice";
					if (multiChoiceCorrectCheck > -1)
					{
						mCA.add(line.substring(0,line.length() - 1));
						answers[questionCounter - 1] = line;
						numberOfMultiChoiceAnswers++;
					}
					else
					{
						mCA.add(line);
						numberOfMultiChoiceAnswers++;
					}
				}
				else
				{
					answers[questionCounter - 1] = line;
					mCA.add(line);
					String[] listOfAnswers = mCA.toArray(new String[0]);
					multiChoiceAnswers.add(listOfAnswers);
				}
			}
			else if (lineCount > 1)
			{
				if (midQuestion == false)
				{
					String[] listOfAnswers = mCA.toArray(new String[0]);
					multiChoiceAnswers.add(listOfAnswers);
				}
			}
			if (questionMarkCheck > -1)
			{
				answersSection = true;
			}
		}
	}
}


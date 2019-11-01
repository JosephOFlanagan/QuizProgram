//*******************************************************************
//QuizProgram by Joseph O'Flanagan
//This is a multi-use tool designed to display quizzes of any sort,
//from specialist interest quizzes, family quizzes and even school
//exams, this program is designed to read files and deliver the content
//in an easily digestible manner and allow users to test themselves on any form of quiz.
//Version 1 Initialised Program
//Version 2 Added commments and an extra line of text for clarification
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
public class QuizProgram
{
	// arguments are passed using the text field below this editor

	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		//Create the quiz that is to be answered
		CreateQuiz createQuiz = new CreateQuiz();
		createQuiz.setQuiz();
		Quiz quiz = new Quiz(createQuiz.questions, createQuiz.questionTypes, createQuiz.answers);
		
		//Variables for use in the main program
		String[] questions;
		String[] questionTypes;
		List<String[]> multiChoiceAnswers;
		String[] answers;
		String userInput;
		int score;

		questions = quiz.questions;
		questionTypes = quiz.questionTypes;
		multiChoiceAnswers = createQuiz.multiChoiceAnswers;
		answers = quiz.answers;
		score = 0;
		
		//Main quiz engine
		for (int i = 0; i < questions.length; i++)
		{
			//Print the question
			System.out.println(questions[i]);
			//Check if the question is a multi-choice question or a single answer question
			if (("Multi-Choice").equals(questionTypes[i]))
			{
				//Print all multi-choice answers
				String[] mCA = multiChoiceAnswers.get(i);
				for (int j = 0; j < mCA.length; j++)
				{
					System.out.println(mCA[j]);
				}
				System.out.println("Please enter the letter or number of the answer");
			}

			//User Input
			Scanner sc = new Scanner(System.in);
			userInput = sc.nextLine();
			//Multi Choice scenario
			if (("Multi-Choice").equals(questionTypes[i]))
			{
				//Check answer against correct answer
				if ((userInput).equals(answers[i].substring(0,1)))
				{
					System.out.println("Correct answer");
					score++;
				}
				else
				{
					System.out.println("Wrong answer");
				}
			}
			else 
			{
				//Check answer against correct answer
				if ((userInput).equals(answers[i]))
				{
					System.out.println("Correct answer");
					score++;
				}
				else
				{
					System.out.println("Wrong answer");
				}
			}
		}
		//Calculate the score and give an appropriate message
		System.out.println("Your score was " + score + " out of " + questions.length);
		if (score == questions.length)
		{
			System.out.println("Congratulations, you got them all right");
		}
		else if (score >= ((questions.length / 2) * 0.5))
		{
			System.out.println("Close, but not quite there.");
		}
		else
		{
			System.out.println("Better luck next time");
		}
	}
}

// This class defines the questions

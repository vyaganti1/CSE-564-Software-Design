/**
 * Javadoc for Translator Software Design.
 * This class implents the translator function which takes a file as input and translates into desired translations.
 * @ author Venkatesh Yaganti
 * @ version 1.0
 * @ since September 01, 2021.
 */

import java.io.*;
public class Translator
{
  public static String outputfirsthalf = "";
  public static String outputsecondhalf = "";
  public static char[] output_characters = {'<','>','(',')','[',']','-'};
  /**
  * @param outputfirsthalf It is used for printing the output from starting to middle
  * @param outputsecondhalf It is used for printing the output from middle to ending
  * @param output_characters It is used for printing the output characters
  * @param text It is used to read the input line by line
  * @param file It is used to read the input file 
  * @param s It is used to read the characters from input
  * @param array_of_strings It is used to store the array of strings for every line splitted with space
  * <p> In this main method we read the input the file using BufferedReader class. 
  * <p> And then we read every individual line, store every word separated with space as delimiter in an array.
  * <p> And then passing that array for every single line of input to splitWords() method.
  */
  public static void main(String[] args) throws Exception
  {
	String text="";
	File file = new File("/Users/user/Documents/Java Programs/Sample Read File.txt");
	BufferedReader br = new BufferedReader(new FileReader(file));
	String s;
	while((s=br.readLine())!=null)
	{
		text = text + s;
		String array_of_strings[] = s.split(" ",1);
		for(String a: array_of_strings)
		{
			splitWords(a);
		}
	}
	printOutput();	
  }
  /**
  * @param position It is used for keeping a track on position of the current.
  * @param array_of_strings_splitWord It is used for storing Array of strings received from splitWords() method.
  * <p> In this method, we will split the array of strings basing space as a delimiter. 
  * <p> And in the for loop, we will be checking if the current string in array is empty string or not. If it is empty string, increment position. This is because to find where the next string is.
  */ 
  public static void splitWords(String a)
  {
	int position = 0;
	String[] array_of_strings_splitWord = a.split(" ");
	for(int i=0;i<array_of_strings_splitWord.length;i++)
	{
		if(array_of_strings_splitWord[i].equals(""))
		{
			position++;
		}
	}
	checkMethodType(array_of_strings_splitWord,position);
  }  
  /**
  * @param array_of_strings_checkMethodType It is used for local functioning, which has the same values as array_of_strings_splitWords.
  * <p> In this method we are checking whether the current string is a "if" or "while" or any other word based on the string position we passed from above function.
  */ 
  public static void checkMethodType(String[] array_of_strings_splitWord, int position)
  {
	String[] array_of_strings_checkMethodType = array_of_strings_splitWord;
	try
	{
		if(array_of_strings_checkMethodType[position].equals("if") || array_of_strings_checkMethodType[position].equals("If"))
		{		
			gotoif(array_of_strings_checkMethodType,position);
		}
		else if(array_of_strings_checkMethodType[position].equals("while") || array_of_strings_checkMethodType[position].equals("While"))
		{
			gotowhile(array_of_strings_checkMethodType,position);
		}
		else if(!array_of_strings_checkMethodType[position].equals("}"))
		{
			gotoins(array_of_strings_checkMethodType,position);
		}
	}
	catch(Exception e){}
  }
  /**
  * @param i It is used for the local variable in this function. It's value is same as the position.
  * @param val It is used to store the value for checking if the next string( "()" ) is present after "if" or not.
  * @param array_of_strings_gotoif It is used for local functioning, which has the same values as array_of_strings_checkMethodType.
  * @throw ArrayIndexOutOfBoundsException This exception occurs when the current poistion is at the end and comparing the immediate string, which is not possible. If this is countered then exception raises.
  * <p> In this method, we will check if the format for "if" statement is valid or not. If yes, we are improving the final output by adding related characters ( '<','>'). If not, it's an Exceptional Error. And checking if there is any function again by calling the recursive method "methodsrecursive". This recursive method will checking the string is "if" or "while" or any other word. Based on the output it will again call this method or related methods again.
  */   
  public static void gotoif(String[] array_of_strings_checkMethodType, int position)
  {
	int i = position;
	String[] array_of_strings_gotoif = array_of_strings_checkMethodType;
	try
	{
		i++;
		int val = (array_of_strings_gotoif[i].equals("()")) ? 1 : 0;
		switch(val)
		{
			case 1:
				i++;
				if(array_of_strings_gotoif[i].equals("{"))
				{
					outputfirsthalf = outputfirsthalf + output_characters[0];
					outputsecondhalf = outputsecondhalf + output_characters[1];
					i++;
					methodsrecursive(array_of_strings_gotoif,i);
				}
				break;
			default: 
				System.out.println("Exceptional Error");
				break;
		}
	}
	catch (Exception e)
	{
		//System.out.println("This is from Catch block"+": "+e);
	}
  }
  /**
  * @param i It is used for the local variable in this function. It's value is same as the position.
  * @param val It is used to store the value for checking if the next string( "()" ) is present after "while" or not.
  * @param array_of_strings_gotowhile It is used for local functioning, which has the same values as array_of_strings_checkMethodType.
  * @throw ArrayIndexOutOfBoundsException This exception occurs when the current poistion is at the end and comparing the immediate string, which is not possible. If this is countered then exception raises.
  * <p> In this method, we will check if the format for "if" statement is valid or not. If yes, we are improving the final output by adding related characters ( '(',')'). If not, it's an Exceptional Error. And checking if there is any function again by calling the recursive method "methodsrecursive". This recursive method will checking the string is "if" or "while" or any other word. Based on the output it will again call this method or related methods again.
  */ 
  public static void gotowhile(String[] array_of_strings_checkMethodType, int position)
  {
	int i = position;
	String[] array_of_strings_gotowhile = array_of_strings_checkMethodType;
	try
	{
		i++;
		int val = (array_of_strings_gotowhile[i].equals("()")) ? 1 : 0;
		switch(val)
		{
			case 1:
				i++;
				if(array_of_strings_gotowhile[i].equals("{"))
				{
					outputfirsthalf = outputfirsthalf + output_characters[2];
					outputsecondhalf = outputsecondhalf + output_characters[3];
					i++;
					methodsrecursive(array_of_strings_gotowhile,i);
				}
				break;
			default: 
				System.out.println("Exceptional Error");
				break;
		}
	}
	catch (Exception e)
	{
		//System.out.println("This is from Catch block"+": "+e);
	}
  }
  /**
  * @param i It is used for the local variable in this function. It's value is same as the position.
  * @param val It is used to store the value for checking if the next string( "()" ) is present after any word or not.
  * @param array_of_strings_gotoins It is used for local functioning, which has the same values as array_of_strings_checkMethodType.
  * @throw ArrayIndexOutOfBoundsException This exception occurs when the current poistion is at the end and comparing the immediate string, which is not possible. If this is countered then exception raises.
  * <p> In this method, we will check if the format for "if" statement is valid or not. If yes, it is an function and we improve the final output by adding related characters ( '[',']'). If not, it's a word. We also then imporove the output by adding related character ('-').
  */ 
  public static void gotoins(String[] array_of_strings_checkMethodType, int position)
  {
	String[] array_of_strings_gotoins = array_of_strings_checkMethodType;
	int i = position;
	try
	{
		i++;
		if(array_of_strings_gotoins[i].equals("()"))
		{
			i++;
			if(array_of_strings_gotoins[i].equals("{"))
			{
				outputfirsthalf = outputfirsthalf + output_characters[4];
				outputsecondhalf = outputsecondhalf + output_characters[5];
			}
		}
	}
	catch (Exception e)
	{
		//System.out.println("This is from Catch block"+": "+e);
		outputfirsthalf = outputfirsthalf + output_characters[6];
	}
  }
  /**
  * @param array_of_strings_gotoins It is used for local functioning, which has the same values as array_of_strings_checkMethodType.
  * <p> This is arecursive method, which calls gotoif() or gotowhile() or gotoins() functions if the current string is "if" or "while" or any other word.
  */ 
  public static void methodsrecursive(String[] array_of_strings, int position)
  {
	String[] array_of_strings_methodsrecursive = array_of_strings;
	if(array_of_strings_methodsrecursive[position].equals("if"))
	{
		gotoif(array_of_strings_methodsrecursive,position);
	}
	else if(array_of_strings_methodsrecursive[position].equals("while"))
	{
		gotowhile(array_of_strings_methodsrecursive,position);
	}		
 	else 
	{
		gotoins(array_of_strings_methodsrecursive,position);
	}
  }
  /**
  * @param reverse_of_outputsecondhalf It is used to reverse the outputsecondhalf variable. Because, the variable outputsecondhalf is in reverse order as we needed. 
  * <p> In this method, we will print the final output as needed which is called in the main() function.
  */ 
  public static void printOutput()
  {
	StringBuffer reverse_of_outputsecondhalf = new StringBuffer(outputsecondhalf);
        reverse_of_outputsecondhalf.reverse();
	System.out.println(outputfirsthalf + reverse_of_outputsecondhalf);
  } 
}
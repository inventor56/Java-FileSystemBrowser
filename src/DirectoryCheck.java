import java.io.PrintWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.FileNotFoundException;

// Assignment 6
/*We’ll continue working with file IO and exception handling by building a simple text-based interface for browsing the file system.
When your program starts it should ask the user if they would like to dump results to a log file IN ADDITION to standard out. If so, your program should prompt for the name of the log file. If the file already exists, the contents should be overwritten.
The program will then display (6) options to the user:

1) List the contents of a directory provided by the user
2) List the contents of a directory provided by the user as well as all of its sub directories. (hint: use recursion)
3) Locate a file with a given name.
4) Locate files with a given file extension
5) Concatenate the contents of 2 files whose names are provided by the user and output the result to a third file (name also provided by the user)
6) Exit*/

// For each directory, start with /home/cpsc/...

// Small Issues:

// 1 - Method 5 puts null at the end of each file
// 2 - You need to type in the full absolute path name of each directory/subdirectory/file you are looking for
// For example, use: /home/cpsc/JavaStuff/Assignments
// 3 - Merged file content isn't logged, but if the method is successfull then a "method success" is written to the file

public class DirectoryCheck
{
	static boolean exitProgram = false;
	static File fileObject = null;
	static BufferedReader keyboard;
	static String pathName = null;
	static boolean writeToLog;
	static String infoJustContents = "";
	static String infoDirectories = ""; // for 2
	static String fileToFind = ""; // for 3/4
	static String foundFiles = ""; // for 3/4
	static String firstFile; // for 5
	static String secondFile; // for 5
	static String outputFileName; // for 5
	
	
	//When to display directory?
	public static void listContents(File pathNameHere) // OPTION 1: lists the contents of the directory inputted
	{
		
		try
		{
			File[] directoryContents = pathNameHere.listFiles();
			for(File dir : directoryContents)
			{
				System.out.println("\t" + dir.getCanonicalPath());
				infoJustContents += "\t" + dir.getCanonicalPath() + "\n";
			}
			
		}
		catch(IOException e)
		{
			System.out.println("Error in reading from file.");
		}

	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void listAllDirectories(File pathNameHere)// OPTION 2 RECURSIVE METHOD: lists the contents of all directories and their subdirectories
	{
		try
		{
			File[] directories = pathNameHere.listFiles();
			for (File dir : directories)
			{
				if (dir.isDirectory())
				{
					System.out.println("directory:" + dir.getCanonicalPath());
					infoDirectories += "directory:" + dir.getCanonicalPath() + "\n";
					listAllDirectories(dir);
				}
				else
				{
					System.out.println("	file:" + dir.getCanonicalPath());
					infoDirectories += "	file:" + dir.getCanonicalPath() + "\n";
				}
			}
			
			
		}
		catch(IOException e)
		{
			System.out.println("Error in reading from file.");
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void findFile(File pathNameHere)// OPTION 3  AND 4RECURSIVE METHOD: Finds a file or files with a certain extension
	{
	
		
		try
		{
			
			File[] directories = pathNameHere.listFiles();
			for (File dir : directories)
			{
				if (dir.isDirectory())
				{
					if(dir.getCanonicalPath().contains(fileToFind))
					{
						foundFiles += dir.getCanonicalPath() + "\n";
					}
					findFile(dir);
				}
				else
				{
					if(dir.getCanonicalPath().contains(fileToFind))
					{
						foundFiles += dir.getCanonicalPath() + "\n";
					}
				}
			}
			
		}
		catch(IOException e)
		{
			System.out.println("Error in reading from file.");
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////
	public static void mergeFiles()
	{
		try
		{

			System.out.println("Please type the full path name of the first file: ");
			firstFile = keyboard.readLine();
			System.out.println("Please type the full path name of the second file: ");
			secondFile = keyboard.readLine();
			System.out.println("Please type the name of the file you want the merged files to have:");
			outputFileName = keyboard.readLine();
			BufferedReader file1 = new BufferedReader(new FileReader(firstFile));
			BufferedReader file2 = new BufferedReader(new FileReader(secondFile));
			PrintWriter file3 = new PrintWriter(new FileOutputStream(outputFileName));
			//
			String string1 = file1.readLine();
			file3.println(string1);
			while (string1 != null)
			{
				string1 = file1.readLine();
				file3.println(string1);
			}
			String string2 = file2.readLine();
			file3.println(string2);
			while (string2 != null)
			{
				string2 = file2.readLine();
				file3.println(string2);
			}

			file1.close();
			file2.close();
			file3.close();
			
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Problem opening files.");
		}
		catch(IOException e)
		{
			System.out.println("Error in reading from file.");
		}
				
	}
	public static void whatWouldYouLikeToDo()
	{
		try
		{
			
			System.out.println("What would you like to do?:\n" +
					"(1) List Contents\n" +
					"(2) List all directories and subdirectories\n" +
					"(3) Find a file\n" +
					"(4) Locate a file with a given extension\n" +
					"(5) Concatenate two files\n" +
					"(6) Quit");
			int answer = Integer.parseInt(keyboard.readLine());
			if(answer == 1)
			{
				
				System.out.println("Please type the full path name of the directory you would like to check out: ");
				pathName = keyboard.readLine();
				fileObject = new File(pathName);
				listContents(fileObject);
				if(writeToLog)
				{
					LogInfo.writeToFile("FILE CONTENTS:\n" + infoJustContents);
				}
				infoJustContents = "";
			}
			if(answer == 2)
			{
				
				System.out.println("Please type the full path name of the directory you would like to check out: ");
				pathName = keyboard.readLine();
				fileObject = new File(pathName);
				listAllDirectories(fileObject);
				if(writeToLog)
				{
					LogInfo.writeToFile("FILE DIRECTORIES AND SUBDIRECTORIES:\n" + infoDirectories);
				
				}
				infoDirectories = "";
				
			}
			if(answer == 3 || answer == 4)
			{
				System.out.println("Please type the full path name of the directory you would like to search in: ");
				pathName = keyboard.readLine();
				fileObject = new File(pathName);
				System.out.println("Please enter the full name of the file/file extensions you'd look to look for");
				fileToFind = keyboard.readLine();
				findFile(fileObject);
				if(writeToLog)
				{
					LogInfo.writeToFile("FILE(S) ENITLED:" + fileToFind + " found at:\n" + foundFiles);
				}
				System.out.println("FILE(S) ENITLED:" + fileToFind + " found at:\n" + foundFiles);
				fileToFind = "";
				foundFiles = "";
			}
			if(answer == 5)
			{
				
				mergeFiles();
				if(writeToLog)
				{
					LogInfo.writeToFile("Method 6)\nFiles have been Merged Sucessfully!\n");
				}
			}
			
			
			if(answer == 6)
			{
				exitProgram = true;
			}
		}
		catch(IOException e)
		{
			System.out.println("Error in reading from file.");
		}
		
		
	}
	public static void main(String[] args)
	{
		try
		{
			//put log method here
			
			
			//Grabbing path name from user
			keyboard = new BufferedReader(new InputStreamReader(System.in));
			//Log to file?
			LogInfo.logging();
			//////////////////////////
			while(!exitProgram)
			{
				DirectoryCheck.whatWouldYouLikeToDo();
				System.out.println("\n");
			}
			LogInfo.endWriting();
			
			
			
		}
		catch (Exception e)
		{
			System.out.println("A problem occurred in the main program.");
		}
		
		
	}
	
}

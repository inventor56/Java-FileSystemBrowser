import java.io.PrintWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class LogInfo
{
	static PrintWriter writer = null;
	
	public LogInfo()
	{
		
	}
	
	public static void logging()
	{
		try
		{
			
			BufferedReader keyboard2 = new BufferedReader(new InputStreamReader(System.in));
			//  Would you like to write to a log file?
			System.out.println("Would you like these results to be written to a log file?(YES or NO) ");
			String logAnswer = keyboard2.readLine();
			if (logAnswer.equals("YES"))
			{
				DirectoryCheck.writeToLog = true;
				System.out.println("What is the name of the file you would like to write to?:");
				String outFile = keyboard2.readLine();
				writer = new PrintWriter(new FileOutputStream(outFile));
				
			}
			else
			{
				DirectoryCheck.writeToLog = false;
			}
		
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
	
	public static void writeToFile(String s)
	{
		try
		{
			writer.println(s);
			
		}
		catch(Exception e)
		{
			System.out.println("And error occured with writing to the file.");
		}
			
	}
	public static void endWriting()
	{
		writer.close();
	}
	
	
	
	
	
}
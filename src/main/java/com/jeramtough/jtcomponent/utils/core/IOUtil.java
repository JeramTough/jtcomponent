package com.jeramtough.jtcomponent.utils.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class IOUtil
{
	/**
	 * get content of text from file
	 *
	 */
	public static String readTxtFile(String fileUri, String encoding)
	{
		try
		{
			File file = new File(fileUri);
			if (file.isFile() && file.exists())
			{
				InputStreamReader read =
						new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				StringBuilder strBuilder = new StringBuilder();
				String line = null;
				while ((line = bufferedReader.readLine()) != null)
				{
					//					System.out.println(line);
					strBuilder.append(line + "\n");
				}
				read.close();
				return strBuilder.toString();
			}
			else
			{
				System.out.println("The file isn't exist");
			}
		}
		catch (Exception e)
		{
			System.out.println("Wrongly read the file");
			e.printStackTrace();
		}
		return null;
		
	}
}

package com.jeramtough.jtcomponent.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NovelUtil
{
	// 正则表达式
	private static ArrayList<Pattern> patterns;
	;
	
	static
	{
		patterns = new ArrayList<>();
		patterns.add(Pattern.compile("&#[0-9]+;"));
		patterns.add(Pattern.compile("<strong>((.|\\n)*)</strong>"));
		patterns.add(Pattern.compile("\\[((.|\\n)*)\\]"));
	}

	/**
	 * @param novelFile
	 * @param outFile
	 */
	public static void formal(File novelFile, File outFile)
	{
		// 要验证的字符串
		String str =
				"&#32;&#25552;&#20379;&#84;&#120;&#116;&#20813;&#36153;&#19979;&#36733;&#65289;";
		
		try
		{
			InputStreamReader read =
					new InputStreamReader(new FileInputStream(novelFile), "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(read);
			StringBuilder strBuilder = new StringBuilder();
			String line = null;
			while ((line = bufferedReader.readLine()) != null)
			{
				for (Pattern pattern : patterns)
				{
					Matcher matcher = pattern.matcher(line);
					// 当字符串与正则表达式相匹配时
					while (matcher.find())
					{
						//得到匹配结果
						System.out.println(matcher.group());
					}
					//替换所有匹配的字符串
					line = matcher.replaceAll("");
				}
				
				strBuilder.append(line + "\n");
			}
			
			FileOutputStream fileOutputStream = new FileOutputStream(outFile);
			fileOutputStream.write(strBuilder.toString().getBytes());
			read.close();
		}
		catch (Exception e)
		{
			System.out.println("Wrongly read the file");
			e.printStackTrace();
		}
		
		
	}
}

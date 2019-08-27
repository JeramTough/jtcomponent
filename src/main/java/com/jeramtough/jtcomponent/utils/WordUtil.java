package com.jeramtough.jtcomponent.utils;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author JeramTough
 * on 2017  October 21 Saturday 14:47.
 */
public class WordUtil
{
	/**
	 * create random char or number
	 *
	 * @param length `
	 * @return `
	 */
	public static String getCharAndNumr(int length)
	{
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++)
		{
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			if ("char".equalsIgnoreCase(charOrNum))
			{
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (choice + random.nextInt(26));
			}
			else if ("num".equalsIgnoreCase(charOrNum))
			{
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}
	
	/**
	 * 获取指定长度随机简体中文
	 *
	 * @param len int
	 * @return String
	 */
	public static String getRandomChinese(int len)
	{
		String ret = "";
		for (int i = 0; i < len; i++)
		{
			String str = null;
			int hightPos, lowPos; // 定义高低位
			Random random = new Random();
			hightPos = (176 + Math.abs(random.nextInt(39))); //获取高位值
			lowPos = (161 + Math.abs(random.nextInt(93))); //获取低位值
			byte[] b = new byte[2];
			b[0] = (new Integer(hightPos).byteValue());
			b[1] = (new Integer(lowPos).byteValue());
			try
			{
				str = new String(b, "GBk"); //转成中文
			}
			catch (UnsupportedEncodingException ex)
			{
				ex.printStackTrace();
			}
			ret += str;
		}
		return ret;
	}
	
	/**
	 * 仅仅是针对中文的，不是中文的就无效
	 *
	 * @param str 要测试的字符串
	 * @return 编码
	 */
	public static String getEncodingForChinese(String str)
	{
		String encode = "ISO-8859-1";
		try
		{
			if (str.equals(new String(str.getBytes(encode), encode)))
			{
				String s = encode;
				return s;
			}
		}
		catch (Exception exception)
		{
		}
		encode = "GB2312";
		try
		{
			if (str.equals(new String(str.getBytes(encode), encode)))
			{
				return "UTF-8";
			}
		}
		catch (Exception exception2)
		{
		}
		encode = "GBK";
		try
		{
			if (str.equals(new String(str.getBytes(encode), encode)))
			{
				String s3 = encode;
				return s3;
			}
		}
		catch (Exception exception3)
		{
		}
		encode = "UTF-8";
		try
		{
			if (str.equals(new String(str.getBytes(encode), encode)))
			{
				return "GB2312";
			}
		}
		catch (Exception exception1)
		{
		}
		return null;
	}
	
	/**
	 * 判断是否含有中文
	 */
	public static boolean isContainsChinese(String str)
	{
		String regEx = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(regEx);
		Matcher matcher = pat.matcher(str);
		boolean is = false;
		if (matcher.find())
		{
			is = true;
		}
		return is;
	}
	
	public static String solveWrongChinese(String context)
	{
		String encoding = getEncodingForChinese(context);
		
		try
		{
			String newContext = new String(context.getBytes(encoding), "UTF-8");
			if (isContainsChinese(newContext) == true)
			{
				return newContext;
			}
			else
			{
				return context;
			}
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		return context;
	}
}

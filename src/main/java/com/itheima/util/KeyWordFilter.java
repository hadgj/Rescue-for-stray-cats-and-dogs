package com.itheima.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeyWordFilter {
    public static Pattern pattern = null;
    // 从words.properties初始化正则表达式字符串
    public static void initPattern()
    {
        StringBuffer patternBuf = new StringBuffer("");
        try
        {
            InputStream in = KeyWordFilter.class.getClassLoader().getResourceAsStream("words.properties");
            Properties pro = new Properties();
            pro.load(in);
            Enumeration enu = pro.propertyNames();
            patternBuf.append("(");
            while(enu.hasMoreElements())
            {
                patternBuf.append((String)enu.nextElement()+"|");
            }
            patternBuf.deleteCharAt(patternBuf.length()-1);
            patternBuf.append(")");

            //unix换成UTF-8
            //pattern = Pattern.compile(new String(patternBuf.toString().getBytes("ISO-8859-1"), "UTF-8"));
            //win下换成gb2312
            pattern = Pattern.compile(new String(patternBuf.toString().getBytes("ISO-8859-1"), "UTF-8"));
        }
        catch(IOException ioEx)
        {
            ioEx.printStackTrace();
        }
    }
    public static String doFilter(String str)
    {
        Matcher m = pattern.matcher(str);
        str = m.replaceAll("***");
        return str;
    }
}

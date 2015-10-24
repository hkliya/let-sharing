package org.blackwhite.share.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jodd.util.RandomString;
import jodd.util.StringUtil;

import com.jfinal.kit.HashKit;

public class StringUtils {
	public static final String RAND_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	public static final String RAND_NUM_STR = "1234567890";
	public static final char[] RAND_CHAR_ARR = RAND_STR.toCharArray();
	public static final char[] RAND_NUM_CHAR_ARR = RAND_NUM_STR.toCharArray();
	/**
	 * 友好时间串
	 * @param time
	 * @return
	 */
	public static String friendlyDate(Date time){
		if(time == null){
			return "";
		}
	    int ct = (int)((System.currentTimeMillis() - time.getTime())/1000);
	    if(ct < 3600){
	    	return Math.max(ct / 60,1) + "分钟前";
	    }else if(ct >= 3600 && ct < 86400){
	    	return ct/3600 + "小时前";
	    }else if(ct >= 86400 && ct < 2592000){
	    	int day = ct / 86400 ;   
	    	return (day>1)? "昨天" : day + "天前";
	    }else{
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINESE);
	    	return sdf.format(time);
	    }
	}
	
	/**
	 * 日期格式化
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String dateFormat(Date date, String pattern) {
		if(date == null){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	public static Date convertToDate(String date, String pattern) {
		if(StringUtil.isBlank(date)){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date tDate = null;
		try {
			tDate = sdf.parse(date);
		} catch (ParseException e) {
		}
		return tDate;
	}
	
	/**
	 * 字符串连接操作
	 * @param list
	 * @param delimiter
	 * @return
	 */
	public static String join(List<?> list,String delimiter){
		StringBuilder sb = new StringBuilder();
		if(CollectionUtils.isBlank(list)){
			return sb.toString();
		}
		Iterator<?> iter = list.iterator();
		while(iter.hasNext()){
			sb.append(StringUtil.toSafeString(iter.next()))
			  .append(StringUtil.toSafeString(delimiter));
		}
		return sb.substring(0, sb.length() - StringUtil.toSafeString(delimiter).length());
	}
	
	public static String join(List<?> list,String delimiter,String prefix,String suffix){
		StringBuilder sb = new StringBuilder();
		if(CollectionUtils.isBlank(list)){
			return sb.toString();
		}
		Iterator<?> iter = list.iterator();
		while(iter.hasNext()){
			sb.append(prefix)
			  .append(StringUtil.toSafeString(iter.next()))
			  .append(suffix)
			 .append(StringUtil.toSafeString(delimiter));
		}
		int len = StringUtil.toSafeString(delimiter).length();
		return sb.substring(0, sb.length() - len);
	}
	
	public static String uuid(){
		String uuid = UUID.randomUUID().toString();
		long millis = System.currentTimeMillis();
		return HashKit.md5(uuid + millis);
	}
	
	public static String rand(int len){
		return RandomString.getInstance().random(len, RAND_CHAR_ARR);
	}
	
	public static String randNum(int len){
		return RandomString.getInstance().random(len, RAND_NUM_CHAR_ARR);
	}
	
	public static void main(String[] args) {
		System.out.println(join(Arrays.asList("admin","user"), ",", "'", "'"));
	}
	
	public static String trim(String content, int len) {
		String tmp = StringUtil.toSafeString(content);
		if(tmp.length() <= len){
			return content;
		}
		return tmp.substring(0,len) + "...";
	}
	
	/** 
     * 手机号验证 
     *  
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isMobile(String str) {   
        Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号  
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    }  
    /** 
     * 电话号码验证 
     *  
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isPhone(String str) {   
        Pattern p1 = null,p2 = null;  
        Matcher m = null;  
        boolean b = false;    
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的  
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的  
        if(str.length() >9)  
        {   m = p1.matcher(str);  
            b = m.matches();    
        }else{  
            m = p2.matcher(str);  
            b = m.matches();   
        }    
        return b;  
    } 
    
    public static String concat(String...strs){
		StringBuilder sb = new StringBuilder();
		for (String str : strs) {
			sb.append(str);
		}
		return sb.toString();
	}
}

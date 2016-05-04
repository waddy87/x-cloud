package com.sugon.cloudview.cloudmanager.event.util;

import java.io.UnsupportedEncodingException;

import com.sugon.cloudview.cloudmanager.event.type.ResourceType;
import com.sugon.cloudview.cloudmanager.event.type.SeverityLevel;

public class StringUtils {
	
	private static final String NONE = "none";
	private static final String USER = "user";
	private static final String NAME = "name";
	
    /**
     * 检查字符串是否为<code>null</code>或空字符串<code>""</code>。
     * 
     * <pre>
     * StringUtil.isEmpty(null)      = true
     * StringUtil.isEmpty("")        = true
     * StringUtil.isEmpty(" ")       = false
     * StringUtil.isEmpty("bob")     = false
     * StringUtil.isEmpty("  bob  ") = false
     * </pre>
     * 
     * @param str
     *            要检查的字符串
     * 
     * @return 如果为空, 则返回<code>true</code>
     */
    public static boolean isEmpty(final String str) {
        return str == null || str.length() == 0;
    }


    /**
     * 检查字符串是否是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。
     * 
     * <pre>
     * StringUtil.isBlank(null)      = true
     * StringUtil.isBlank("")        = true
     * StringUtil.isBlank(" ")       = true
     * StringUtil.isBlank("bob")     = false
     * StringUtil.isBlank("  bob  ") = false
     * </pre>
     * 
     * @param str
     *            要检查的字符串
     * 
     * @return 如果为空白, 则返回<code>true</code>
     */
    public static boolean isBlank(final String str) {
        int length;

        if (str == null || (length = str.length()) == 0) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }


	public static String replace(String inString, String oldPattern, String newPattern) {
		if (!hasLength(inString) || !hasLength(oldPattern) || newPattern == null) {
			return inString;
		}
		StringBuilder sb = new StringBuilder();
		int pos = 0; // our position in the old string
		int index = inString.indexOf(oldPattern);
		// the index of an occurrence we've found, or -1
		int patLen = oldPattern.length();
		while (index >= 0) {
			sb.append(inString.substring(pos, index));
			sb.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sb.append(inString.substring(pos));
		// remember to append any characters to the right of a match
		return sb.toString();
	}
	
	/**
	 * Check that the given CharSequence is neither <code>null</code> nor of length 0.
	 * Note: Will return <code>true</code> for a CharSequence that purely consists of whitespace.
	 * <p><pre>
	 * StringUtils.hasLength(null) = false
	 * StringUtils.hasLength("") = false
	 * StringUtils.hasLength(" ") = true
	 * StringUtils.hasLength("Hello") = true
	 * </pre>
	 * @param str the CharSequence to check (may be <code>null</code>)
	 * @return <code>true</code> if the CharSequence is not null and has length
	 */
	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}
	
	/**
	 * Check that the given String is neither <code>null</code> nor of length 0.
	 * Note: Will return <code>true</code> for a String that purely consists of whitespace.
	 * @param str the String to check (may be <code>null</code>)
	 * @return <code>true</code> if the String is not null and has length
	 */
	public static boolean hasLength(String str) {
		return hasLength((CharSequence) str);
	}
	
	public static byte[] getUtf8Bytes(String str){
		if(hasLength(str)){
			try {
				return str.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// ignore
				
			}
		}
		return null;
	}


	public static String getStringFromUtf8Bytes(byte[] tmpArray) {
		if(tmpArray != null && tmpArray.length > 0){
			try {
				return new String(tmpArray,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// ignore
				
			}
		}
		return null;
	}


	public static boolean equals(String str1, String str2) {
		if(str1==null && str2 == null){
			return true;
		}
		if(str1 == null || str2 == null){
			return false;
		}
		return str1.equals(str2);
	}
	
	public static String joinStrings(String[] strings, String delimiter,
			int startIndex) {
		
		int length = strings.length;
		if (length == 0)
			return "";
		if (length < startIndex)
			return "";
		
		StringBuilder words = new StringBuilder(strings[startIndex]);
		for (int i = startIndex + 1; i < length; i++) {
			words.append(delimiter).append(strings[i]);
		}
		return words.toString();
		
	}
	
	/**
	 * 算法：根据queueName计算bindingKey
	 * @param queueName 不能为空，其他调用函数之前已经判断
	 * @return
	 */
	public static String getBindingKey(String queueName) {
		
		if(null == queueName || queueName.length() <= 0
				|| "#".equals(queueName)){
			return "#";
		}
		
		String[] relaArr = queueName.split("\\.");
		String resourceType = ResourceType.getResourceType(relaArr);
		String severityLevel = SeverityLevel.getSeverityLevel(relaArr);
		
		String resourceName  = "*", userName  = "*";
		for(String s: relaArr){
			if(s.contains(USER)){
				userName = s;
			}
			if(s.contains(NAME)){
				resourceName = s;
			}
		}
		
		String strings[] = {resourceType, resourceName, severityLevel, userName};
		return StringUtils.joinStrings(strings, ".", 0);
	}
	
	public static String getQueueKey(
			String resourceType,
			String resourceName,
			String severityLevel,
			String userName) {
		
		StringBuffer queueKey = new StringBuffer();
		if(null != resourceType && resourceType.length() > 0) queueKey.append(resourceType);
		if(null != severityLevel && severityLevel.length() > 0) queueKey.append(".").append(severityLevel);
		if(null != resourceName && resourceName.length() > 0) {
			queueKey.append(".").append(NAME + ":" + resourceName);
		}
		if(null != userName && userName.length() > 0) {
			queueKey.append(".").append(USER + ":" + userName);
		}
		
		if(null == queueKey || queueKey.length() <= 0){
			return "#";
		}
		if('.' == queueKey.charAt(0)){
			return queueKey.substring(1);
		}
		return queueKey.toString();
	}
	
	public static String getRoutingKey(
			String resourceType,
			String resourceName,
			String SeverityLevel,
			String userName) {
		
		if(null == resourceType || resourceType.length() <= 0) resourceType = NONE;
		if(null == SeverityLevel || SeverityLevel.length() <= 0) SeverityLevel = NONE;
		if(null != resourceName && resourceName.length() > 0) {
			resourceName = NAME + ":" + resourceName;
		}else{
			resourceName = NONE;
		}
		if(null != userName && userName.length() > 0) {
			userName = USER + ":" + userName;
		}else{
			userName = NONE;
		}
		
		String routingKey = resourceType + "." + resourceName + "." + 
				SeverityLevel + "." + userName;
		System.out.println("getRoutingKey : " + routingKey);
		return routingKey;
	}
	
	public static boolean isRoutingQueue(String queueName, String routingKey) {
		
		if(null == queueName || queueName.length() <= 0){
			return false;
		}
		
		queueName = queueName.split("\\|")[0];
		String[] relaArr = queueName.split("\\.");
		for(String relaArrPart: relaArr){
			if(!routingKey.contains(relaArrPart)){
				return false;
			}
				
		}
		return true;
	}
	
	public static void main(String[] argv) throws Exception {

		System.out.println(getBindingKey(""));
		System.out.println(getBindingKey("1"));
		System.out.println(getBindingKey("host"));
		System.out.println(getBindingKey("info"));
		System.out.println(getBindingKey("host.info"));
		System.out.println(getBindingKey("info.host"));
	}

}
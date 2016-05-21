package org.waddys.xcloud.common.base.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class StringUtils {
    private static final char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
            'F' };

    public static String join(Iterable<? extends Object> iterable, String delim) {
        StringBuilder sb = new StringBuilder();
        if (iterable != null) {
            Iterator<? extends Object> iter = iterable.iterator();
            if (iter.hasNext()) {
                Object next = iter.next();
                sb.append(next.toString());
            }
            while (iter.hasNext()) {
                Object next = iter.next();
                sb.append(delim + next.toString());
            }
        }
        return sb.toString();
    }

    public static String join(final String delimiter, final Object... components) {
        return org.apache.commons.lang.StringUtils.join(components, delimiter);
    }

    public static boolean isNotBlank(String str) {
        if (str != null && str.trim().length() > 0) {
            return true;
        }

        return false;
    }

    /**
     * @param tags
     * @return List of tags
     */

    public static List<String> csvTagsToList(String tags) {
        List<String> tagsList = new ArrayList<String>();

        if (tags != null) {
            String[] tokens = tags.split(",");
            for (int i = 0; i < tokens.length; i++) {
                tagsList.add(tokens[i].trim());
            }
        }

        return tagsList;
    }

    /**
     * Converts a List of tags to a comma separated list
     * 
     * @param tags
     * @return String containing a comma separated list of tags
     */

    public static String listToCsvTags(List<String> tagsList) {
        String tags = "";
        if (tagsList.size() > 0) {
            for (int i = 0; i < tagsList.size(); i++) {
                tags += tagsList.get(i);
                if (i != tagsList.size() - 1) {
                    tags += ",";
                }
            }
        }

        return tags;
    }

    public static String getExceptionStackInfo(Throwable e) {
        StringBuffer sb = new StringBuffer();

        sb.append(e.toString()).append("\n");
        StackTraceElement[] elemnents = e.getStackTrace();
        for (StackTraceElement element : elemnents) {
            sb.append(element.getClassName()).append(".");
            sb.append(element.getMethodName()).append("(");
            sb.append(element.getFileName()).append(":");
            sb.append(element.getLineNumber()).append(")");
            sb.append("\n");
        }

        return sb.toString();
    }

    public static String unicodeEscape(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c >> 7) > 0) {
                sb.append("\\u");
                sb.append(hexChar[(c >> 12) & 0xF]); // append the hex character
                                                     // for the left-most 4-bits
                sb.append(hexChar[(c >> 8) & 0xF]); // hex for the second group
                                                    // of 4-bits from the left
                sb.append(hexChar[(c >> 4) & 0xF]); // hex for the third group
                sb.append(hexChar[c & 0xF]); // hex for the last group, e.g.,
                                             // the right most 4-bits
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String getMaskedPasswordForDisplay(String password) {
        if (password == null || password.isEmpty()) {
            return "*";
        }

        StringBuffer sb = new StringBuffer();
        sb.append(password.charAt(0));
        for (int i = 1; i < password.length(); i++) {
            sb.append("*");
        }

        return sb.toString();
    }

    // removes a password request param and it's value
    private static final Pattern REGEX_PASSWORD_QUERYSTRING = Pattern
            .compile("&?(password|accesskey|secretkey)=.*?(?=[&'\"])");

    // removes a password/accesskey/ property from a response json object
    private static final Pattern REGEX_PASSWORD_JSON = Pattern.compile("\"(password|accesskey|secretkey)\":\".*?\",?");

    // Responsible for stripping sensitive content from request and response
    // strings
    public static String cleanString(String stringToClean) {
        String cleanResult = "";
        if (stringToClean != null) {
            cleanResult = REGEX_PASSWORD_QUERYSTRING.matcher(stringToClean).replaceAll("");
            cleanResult = REGEX_PASSWORD_JSON.matcher(cleanResult).replaceAll("");
        }
        return cleanResult;
    }

    /**
     * Removes all unprintable characters from a string and replaces with a
     * space.
     * 
     * @param input
     * @return the stripped value
     */
    public static String stripControls(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c > 0x20 && c < 0x7f) {
                sb.append(c);
            } else {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    public static String stripControlCharacters(String s) {
        return StringUtils.stripControls(s);
    }

    public static int formatForOutput(String text, int start, int columns, char separator) {
        if (start >= text.length()) {
            return -1;
        }

        int end = start + columns;
        if (end > text.length()) {
            end = text.length();
        }
        String searchable = text.substring(start, end);
        int found = searchable.lastIndexOf(separator);
        return found > 0 ? found : end - start;
    }

    public static Map<String, String> stringToMap(String s) {
        Map<String, String> map = new HashMap<String, String>();
        String[] elements = s.split(";");
        for (String parts : elements) {
            String[] keyValue = parts.split(":");
            map.put(keyValue[0], keyValue[1]);
        }
        return map;
    }

    public static String mapToString(Map<String, String> map) {
        String s = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            s += entry.getKey() + ":" + entry.getValue() + ";";
        }
        if (s.length() > 0) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }

    public static String escapeSQLLike(String likeStr) {
        String str = org.apache.commons.lang.StringUtils.replace(likeStr, "_", "/_");
        str = org.apache.commons.lang.StringUtils.replace(str, "%", "/%");
        str = org.apache.commons.lang.StringUtils.replace(str, "?", "_");
        str = org.apache.commons.lang.StringUtils.replace(str, "*", "%");
        return str;
    }

    public static void main(String[] args) {
        System.out.println(escapeSQLLike("aaa_aaa"));
    }
}

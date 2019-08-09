package com.jeramtough.jtcomponent.utils.core;

public class StringUtil {
    /**
     *
     */
    public static String addOrDeleteWords(String originalText, boolean isAdded, int start,
                                          String words) {
        originalText = (originalText == null ? "" : originalText);
        String textHead = originalText.substring(0, start);
        String textBoot = originalText.substring(start, originalText.length());
        if (isAdded) {
            textHead = textHead + words;
        }
        else {
            if (textHead.length() >= words.length()) {
                textHead = textHead.substring(0, textHead.length() - words.length());
            }
        }
        originalText = textHead + textBoot;
        return originalText;
    }

    /**
     * return true if the str is empty or the str just is comprised of spaces.
     */
    public static boolean isEmptyOrSpaces(String str) {

        //first pass
        if (str == null || "".equals(str) || str.length() == 0) {
            return true;
        }

        //second pass
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }
}

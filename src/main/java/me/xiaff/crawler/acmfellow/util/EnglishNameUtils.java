package me.xiaff.crawler.acmfellow.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class EnglishNameUtils {

    /**
     * convert English name like 'Stephen M. Blackburn' to comma-separated form as 'Blackburn, Stephen M.'
     *
     * @param name English name
     * @return comma-separated form, family name at first
     */
    public static String toCommaSepName(String name) {
        if (!name.contains(" ")) {
            return name;
        }
        String[] tokens = name.split(" ");
        String[] nameTokens = Arrays.copyOfRange(tokens, 0, tokens.length - 1);
        return tokens[tokens.length - 1] + ", " + StringUtils.join(nameTokens, " ");
    }

    public static boolean isSameName(String commaName, String fullName) {
        String convertedName = toCommaSepName(fullName);
        System.out.println("1:" + commaName);
        System.out.println("2:" + convertedName);
        return convertedName.startsWith(commaName) || commaName.startsWith(convertedName);
    }
}

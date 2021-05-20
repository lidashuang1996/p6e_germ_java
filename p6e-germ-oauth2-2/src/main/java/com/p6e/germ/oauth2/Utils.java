package com.p6e.germ.oauth2;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Utils {
    private static final Pattern PATTERN = Pattern.compile("\\$\\{(.*?)}");

    public static String variableFormatting(String content, String[] data) {
        if (data == null) {
            return content;
        } else {
            final int len = data.length / 2;
            final Map<String, String> map = new HashMap<>(len);
            for (int i = 0; i < len; i++) {
                final String key = data[i * 2];
                final String value = data[i * 2 + 1];
                map.put(key, value);
            }
            return variableFormatting(content, map);
        }
    }

    public static String variableFormatting(String content, Map<String, String> data) {
        if (content == null) {
            return null;
        } else if (data == null || data.size() == 0) {
            return content;
        } else {
            int index = 0;
            final Matcher matcher = PATTERN.matcher(content);
            final StringBuilder result = new StringBuilder();
            // 处理匹配到的值
            while (matcher.find()) {
                final int end = matcher.end();
                final int start = matcher.start();
                final String key = matcher.group();
                if (key != null && key.length() > 3) {
                    final String value = data.get(key.substring(2, key.length() - 1));
                    if (value != null) {
                        result.append(content, index, start);
                        result.append(value);
                        index = end;
                    }
                }
            }
            result.append(content, index, content.length());
            return result.toString();
        }
    }
}

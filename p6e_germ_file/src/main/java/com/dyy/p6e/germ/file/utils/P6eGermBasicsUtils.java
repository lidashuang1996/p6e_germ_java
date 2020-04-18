package com.dyy.p6e.germ.file.utils;

import javax.swing.text.DateFormatter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.UUID;

/**
 * 基础的工具类
 */
public final class P6eGermBasicsUtils {

    public enum FileFormat {
        JPG,
        GIF,
        JPEG,
        PNG;

        public boolean verification(String format) {
            return this.name().equals(format.toUpperCase());
        }
    }

    public static String generateUnique() {
        return generateUnique("");
    }

    public static String generateUnique(String name) {
        return (name + UUID.randomUUID().toString().replaceAll("-", "")).toLowerCase();
    }

    public static String currentDatePath() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return DateTimeFormatter.ofPattern("yyyy/MM/dd").format(localDateTime);
    }

    public static FileFormat[] formatCompile(String[] formats) {
        FileFormat[] result = new FileFormat[formats.length];
        for (int i = 0; i < formats.length; i++) {
            String f = formats[i].toUpperCase();
            switch (f) {
                case "JPG":
                    result[i] = FileFormat.JPG;
                    break;
                case "GIF":
                    result[i] = FileFormat.GIF;
                    break;
                case "JPEG":
                    result[i] = FileFormat.JPEG;
                    break;
                case "PNG":
                    result[i] = FileFormat.PNG;
                    break;
            }
        }
        return result;
    }

    public static String fileFormatValidation(String fileName) {
        return fileFormatValidation(fileName, null);
    }

    public static String fileFormatValidation(String fileName, FileFormat[] fileFormats) {
        if (fileName == null || fileName.length() == 0) return null;
        else {
            boolean bool = false;
            fileName = fileName.toLowerCase();
            StringBuilder formatSB = new StringBuilder();
            for (int i = fileName.length() - 1; i >= 0; i--) {
                char ch = fileName.charAt(i);
                if (ch == '.') {
                    bool = true;
                    break;
                } else formatSB.append(ch);
            }
            String format = formatSB.reverse().toString();
            if (bool) {
                if (fileFormats == null) return format;
                else {
                    for (FileFormat fileFormat : fileFormats) {
                        if (fileFormat.verification(format)) return format;
                    }
                }
            }
            return null;
        }
    }

}

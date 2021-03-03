package com.dyy.p6e.germ.file2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

/**
 * @author lidashuang
 * @version 1.0
 */
public class TestMain {


    public static void main(String[] args) throws Exception {

        File file1 = new File("/Users/admin/Downloads/888888.jpg");
        System.out.println(file1);

        FileInputStream fis = new FileInputStream(file1);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len = -1;
        while((len = fis.read(b)) != -1) {
            bos.write(b, 0, len);
        }

        byte[] result = bos.toByteArray();

        System.out.println(Arrays.toString(result));
        System.out.println(result.length);


        byte[] sss = new byte[] {
                -2, 4, 10, 3
        };

        // 加密数据 ---- START ------

        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) ((int) result[i] ^ (int) sss[i % 4]);
        }

        // 加密数据 ---- END ------


        // 解密数据 ---- START ------

        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) ((int) result[i] ^ (int) sss[i % 4]);
        }

        // 解密数据 ---- END ------


        File file2 = new File("/Users/admin/Downloads/999999.jpg");
        System.out.println(file2);
        if (file2.exists()) {
            file2.delete();
        }
        file2.createNewFile();
        FileOutputStream fos = new FileOutputStream(file2);

        fos.write(result);
        fos.close();

        System.gc();
    }



}

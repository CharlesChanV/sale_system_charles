package com.dgut.utils;

//import sun.misc.BASE64Decoder;
import org.apache.commons.codec.binary.Base64;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author Charles
 * @version 1.0
 * @date 2020/6/16
 */
public class Base64Util {
    /*
     * @Description:
     * @param baseStr 1
     * @param imagePath 2
     * @return : boolean
     * @author : CR
     * @date : 2020/6/16 13:09
     */
    public static boolean base64ChangeImage(String baseStr,String imagePath){
        if (baseStr == null){
            return false;
        }
//        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 解密
//            byte[] b = decoder.decodeBuffer(baseStr);
            byte[] b = Base64.decodeBase64(baseStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(imagePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

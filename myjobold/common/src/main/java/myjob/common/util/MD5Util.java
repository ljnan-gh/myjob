package myjob.common.util;

import java.security.MessageDigest;

public class MD5Util {
    /***
     * MD5加密 生成32位md5码
     */
    public static String md5Encode(String sourceStr) {
        StringBuffer encodeValue = new StringBuffer();
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] byteArray = sourceStr.getBytes("UTF-8");
            byte[] md5Bytes = md5.digest(byteArray);

            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    encodeValue.append("0");
                }
                encodeValue.append(Integer.toHexString(val));
            }
        } catch (Exception e) {
            return null;
        }
        return encodeValue.toString();
    }
}

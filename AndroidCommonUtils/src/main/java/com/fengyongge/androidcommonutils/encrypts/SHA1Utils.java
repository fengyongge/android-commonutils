package com.fengyongge.androidcommonutils.encrypts;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * describe
 * SHA1 加密
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2019/6/1
 */

public final class SHA1Utils {

    /**
     * SHA加密
     * @param strSrc 明文
     * @return 加密之后的密文
     */
    public static String shaEncrypt(String strSrc) {
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = new byte[0];
        try {
            bt = strSrc.toString().getBytes("UTF-8");
            try {
                // 将此换成SHA-1、SHA-512、SHA-384等参数
                md = MessageDigest.getInstance("SHA1");
                byte[] thedigest = md.digest(bt);
                // to HexString
                strDes = bytesToHexString(thedigest);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

//            md.update(bt);

        return strDes;
    }


    private static String bytesToHexString(byte[] src) {
        try {
            StringBuilder stringBuilder = new StringBuilder("");
            if (src == null || src.length <= 0) {
                return null;
            }
            for (int i = 0; i < src.length; i++) {
                int v = src[i] & 0xFF;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    stringBuilder.append(0);
                }
                stringBuilder.append(hv);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return null;
        }
    }



}

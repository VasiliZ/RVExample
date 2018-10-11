package com.github.vasiliz.myapplication.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AvatarHelper {

    public static final String GAVATAR_URL = "http://www.gravatar.com/avatar/";

    public static String getAvatarUrl(String pEmail) {
        return GAVATAR_URL + md5(pEmail) + "?s=72";
    }

    private static String md5(String pEmail) {
        final String MD5 = "MD5";

        try {
            MessageDigest digest = MessageDigest.getInstance(MD5);
            digest.update(pEmail.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuilder stringBuilder = new StringBuilder();
            for (byte messageDigest0 : messageDigest) {
                String s = Integer.toHexString(0xFF & messageDigest0);
                while (s.length() < 2) {
                    s = "0" + s;
                    stringBuilder.append(s);
                }
                return stringBuilder.toString();
            }
        } catch (NoSuchAlgorithmException pE) {
            pE.printStackTrace();
        }
        return "";
    }
}

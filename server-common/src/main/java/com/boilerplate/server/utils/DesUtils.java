package com.boilerplate.server.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;

/**
 * Des加解密工具
 */
public class DesUtils {
    public static final String secretKey = "C8RYnYmFEzE=";

    /**
     * 加密
     * @param info
     * @return
     */
    public static String encrypt(String info) {
        byte[] key = Base64.decode(secretKey);
        DES des = SecureUtil.des(key);
        return des.encryptHex(info);
    }

    /**
     * 解密
     * @param encrypt
     * @return
     */
    public static String decrypt(String encrypt) {
        byte[] key = Base64.decode(secretKey);
        DES des = SecureUtil.des(key);
        return des.decryptStr(encrypt);
    }

    /**
     * 随机生成一个新密钥，并转为字符串
     * @return
     */
    public static String genSecretKey(){
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.DES.getValue()).getEncoded();
        return Base64.encode(key);
    }

    public static void main(String[] args) {
        String secretKey = genSecretKey();
        System.out.println(secretKey);

        //加密
        String str = DesUtils.encrypt("20230916");
        System.out.println(str);
        String str2 = DesUtils.encrypt("20230917");
        System.out.println(str2);

        // 解密
        String decode = DesUtils.decrypt(str);
        System.out.println(decode);
    }
}

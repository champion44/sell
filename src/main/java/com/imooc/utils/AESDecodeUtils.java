package com.imooc.utils;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;
 
/**
 * Created by hgg on 2018/2/24.
 * 小程序AES解密
 */
public class AESDecodeUtils{
 
    public static void main(String[] args) throws Exception {
        byte[] encrypData = Base64.decodeBase64("mgxuts7uEbdnBclp0Qim0TS3MGoaSyE09MqTvbeG9Z1PjxsDwjVH0FxG1Q==");
        byte[] ivData = Base64.decodeBase64("SG386etdA3sOXHxqfnw==");
        byte[] sessionKey = Base64.decodeBase64("Fn6r4IOiZJBXn4hQ0w==");
        System.out.println(decrypt(sessionKey,ivData,encrypData));
    }
 
    public static String decrypt(byte[] key, byte[] iv, byte[] encData) throws Exception {
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        //解析解密后的字符串
        return new String(cipher.doFinal(encData),"UTF-8");
    }
}

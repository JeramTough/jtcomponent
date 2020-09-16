package com.jeramtough.jtcomponent.key.util;

import com.jeramtough.jtcomponent.key.bean.RsaKeysProvider;
import com.jeramtough.jtcomponent.utils.Base64Util;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

/**
 * Created on 2018-09-10 15:43
 * by @author JeramTough
 */
public class KeyUtil {

    /**
     * MD5和SHA属于单向加密，即没有办法解谜，一般用来判断是不是同一个文件，只要是同一个
     * 文件，md5或者sha加密后的值一定是一样的
     */
    public static final String ALGORITHM_NAME_SHA = "SHA";

    /**
     * MD5和SHA属于单向加密，即没有办法解谜，一般用来判断是不是同一个文件，只要是同一个
     * 文件，md5或者sha加密后的值一定是一样的
     */
    public static final String ALGORITHM_NAME_MD5 = "MD5";

    /**
     * MAC算法可选以下多种算法
     *
     * <pre>
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     * </pre>
     */
    public static final String ALGORITHM_NAME_HMAC = "HmacMD5";


    /**
     * 非对称加密算法，有公钥和私钥才能解开加密数据
     */
    public static final String ALGORITHM_NAME_RSA = "RSA";

    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS_LOWER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS_UPPER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};





    /**
     * MD5加密
     *
     * @param data 字节码数组
     * @return 加密后的字节码数组
     */
    public static byte[] encryptByMD5(byte[] data) {
        try {
            MessageDigest md5;
            md5 = MessageDigest.getInstance(ALGORITHM_NAME_MD5);
            md5.update(data);
            return md5.digest();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * MD5加密
     *
     * @param dataStr·
     * @return ·
     */
    public static byte[] encryptByMD5(String dataStr) {
        return encryptByMD5(dataStr.getBytes(Charset.forName("UTF-8")));
    }

    /**
     * SHA加密
     *
     * @param data ·
     * @return ·
     */
    public static byte[] encryptForSHA(byte[] data) {
        try {
            MessageDigest sha = MessageDigest.getInstance(ALGORITHM_NAME_SHA);
            sha.update(data);
            return sha.digest();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * SHA加密
     *
     * @param dataStr ·
     * @return ·
     */
    public static byte[] encryptForSHA(String dataStr) {
        return encryptForSHA(dataStr.getBytes(Charset.forName("UTF-8")));
    }

    /**
     * 生成HMAC密钥
     *
     * @return ·
     */
    public static String processHMACKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM_NAME_HMAC);
            SecretKey secretKey = keyGenerator.generateKey();
            return Base64Util.toBase64Str(secretKey.getEncoded());
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用HMAC加密算法对data进行加密
     *
     * @param data ·
     * @param key  ·
     * @return ·
     */
    public static byte[] encryptByHMAC(byte[] data, String key) {

        try {
            SecretKey secretKey = new SecretKeySpec(Base64Util.decodeBytesFromBase64(key),
                    ALGORITHM_NAME_HMAC);
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            return mac.doFinal(data);
        }
        catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将bytes转成16进制，32长度的字符串
     *
     * @param bytes         ·
     * @param isToUpperCase ·
     * @return ·
     */
    public static String to16Hex32LengthString(byte[] bytes, boolean isToUpperCase) {
        char[] toDigits = isToUpperCase ? DIGITS_UPPER : DIGITS_LOWER;
        final int l = bytes.length;
        final char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & bytes[i]) >>> 4];
            out[j++] = toDigits[0x0F & bytes[i]];
        }
        return new String(out);
    }

    /**
     * 得到一个新创建的RsaKey对
     */
    public static RsaKeysProvider getRsaKeysProvider() {
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance(ALGORITHM_NAME_RSA);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyPairGen.initialize(1024);
        //通过对象 KeyPairGenerator 获取对象KeyPair
        KeyPair keyPair = keyPairGen.generateKeyPair();
        //通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

        RsaKeysProvider rsaKeysProvider = new RsaKeysProvider(rsaPublicKey, rsaPrivateKey);
        return rsaKeysProvider;
    }

    /**
     * 返回解析出的ResKey对实例
     *
     * @param rsaPublicKeyBase64String  base64编码的公钥
     * @param rsaPrivateKeyBase64String base64编码的私钥
     * @return RsaKeysProvider
     */
    public static RsaKeysProvider getRsaKeysProvider(String rsaPublicKeyBase64String,
                                                     String rsaPrivateKeyBase64String) {
        RSAPublicKey rsaPublicKey = null;
        RSAPrivateKey rsaPrivateKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_NAME_RSA);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
                    Base64Util.decodeBytesFromBase64(rsaPublicKeyBase64String));
            rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);

            PKCS8EncodedKeySpec keySpec1 = new PKCS8EncodedKeySpec(
                    Base64Util.decodeBytesFromBase64(rsaPrivateKeyBase64String));
            rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec1);
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        RsaKeysProvider rsaKeysProvider = new RsaKeysProvider(rsaPublicKey, rsaPrivateKey);
        return rsaKeysProvider;
    }


}

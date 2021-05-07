import com.alibaba.druid.filter.config.ConfigTools;
import org.junit.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * <一句话概述功能>
 *
 * @author Marcus
 * @date 2021/5/7-11:19
 * @description this is function description
 */
public class EncrytionTest {
    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCkQNqK8eYsYI7f0ZO4y6eT3cGc/K8H2TweOIkY" +
            "ifVIOgLdX8pOwzXKErZmPnYk4E56q5AGY3Uj7KGnKZ+KT1qls9aXwnP4pdxzy7KObEU0bZAEkflJ" +
            "toEwHeqXyLULXWCnFRh0OERa7er8poPD2vXfoeFEZLYyE+1IJ8CN+JYMTQIDAQAB";

    private static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKRA2orx5ixgjt/Rk7jLp5PdwZz8" +
            "rwfZPB44iRiJ9Ug6At1fyk7DNcoStmY+diTgTnqrkAZjdSPsoacpn4pPWqWz1pfCc/il3HPLso5s" +
            "RTRtkASR+Um2gTAd6pfItQtdYKcVGHQ4RFrt6vymg8Pa9d+h4URktjIT7UgnwI34lgxNAgMBAAEC" +
            "gYEAl2lFTEITGSNlcUMjdn0dnDwWp11zB7wkJAOftWQvHIaFb4ZG2vYuVnmLdJLtm66+CWyvVoRG" +
            "Tnhqx8qhzAC8oHeNICNQQSiR4lFB530uQ/Hw3ovqWpRDJ2M0/ANfQk3AiuJtFV+WaY7vTiu/+Poz" +
            "ThDWqDFViYjfRUBH18v/Mc0CQQDrbV4QZ1mwJN3Mo++1jeiZgo19LUwj8HNtJ8CYGrCyCgwVfSDT" +
            "wfv/9srD+ygv+7Y+eNw3AWAzfwkdteUFFQCrAkEAsptJ2v+y5M4avmvuRtJX98ectyLFu34Fgqa7" +
            "eaBpMxqq5uTPHth/Ut7YjLpYQcwLOaYB4Hu5bccItlAAhztW5wJAOwkX12EzOlpkTBan240UULpO" +
            "JJ+hQjnfl/Wp8/ptaJfgY9sWykMQoCUQv4hRkYa7Mns4LYroxsEKVirwnQ+hNQJBALAx2W4eCdEG" +
            "MgPgXbOoFffuB3/y4bXQ4Ia/DPszcBRmHmqhFmKLmS+bf21091QRgpFgX0GhTJArZUDVM3A07ckC" +
            "QA8K/ok2bcsAuFJAz0KhstxcwKVa0nLrgC3DI/fASFQ0nFwtpFwQ/+BlUOLnyat8dzOIekYveDpf" +
            "AfPtmzJiox8=";

    @Test
    public void test1() throws Exception {
        String encryptedString = ConfigTools.encrypt(PRIVATE_KEY, "I have a beautiful girlfriend");
        System.out.println(ConfigTools.decrypt(PUBLIC_KEY, encryptedString));
    }


    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final String PUBLIC_KEY_RSA = "RSAPublicKey";
    private static final String PRIVATE_KEY_RAS = "RSAPrivateKey";

    @Test
    public void test2() {
        Map<String, Object> keyMap;
        try {
            keyMap = initKey();
            String publicKey = getPublicKey(keyMap);
            System.out.println("publicKey:\n" + publicKey);
            String privateKey = getPrivateKey(keyMap);
            System.out.println("privateKey:\n" + privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY_RSA);
        byte[] publicKey = key.getEncoded();
        return encryptBASE64(key.getEncoded());
    }

    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY_RAS);
        byte[] privateKey = key.getEncoded();
        return encryptBASE64(key.getEncoded());
    }

    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    public static Map<String, Object> initKey() throws Exception {
        //指定算法获取公私钥生成器
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //指定算法度量
        keyPairGen.initialize(1024);
        //生成这对密钥
        KeyPair keyPair = keyPairGen.generateKeyPair();
        //RSA公钥获取
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //RSA私钥获取
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY_RSA, publicKey);
        keyMap.put(PRIVATE_KEY_RAS, privateKey);
        return keyMap;
    }
}

package org.jarvis.java8;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tennyson
 * @date 2021/6/5-23:40
 */
public class JWTUtil {
    //过期时间
    private static final long EXPIRE_TIME = 15 * 60 * 1000;
    //私钥
    private static final String TOKEN_SECRET = "privateKey";

    /**
     * 生成签名，15分钟过期
     * @param **username**
     * @param **password**
     * @return
     */
    public static String sign(Long userId) {
        try {
            // 设置过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("Type", "Jwt");
            header.put("alg", "HS256");
            // 返回token字符串
            return JWT.create()
                    .withHeader(header)
                    .withClaim("userId", userId)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 检验token是否正确
     * @param **token**
     * @return
     */
    public static Long verify(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            Long userId = jwt.getClaim("userId").asLong();
            return userId;
        } catch (Exception e){
            return 0L;
        }
    }

    public static void main(String[] args) {
        DecodedJWT decodedJWT=JWT.decode("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MjI5MDM3MjYsIm5iZiI6MTYyMjkwMzcyNiwiZGF0YSI6eyJ1aWQiOjE2NDI2OTAyMiwidXNlcm5hbWUiOiJcdTViODdcdTY2N2FcdTZjZTJcdTVlMjZcdTRlYmEiLCJhdmF0YXIiOiJkZmMyMGUzNzNkM2Q4MGQxZTYwYzA2MjNjZWYyYzU0ZS5qcGVnIn19.E2v-XgHRmnXOg4wO8Kqp4h0O2Mzs_JS2SHH6irnDVgE");
        decodedJWT.getAlgorithm();
        decodedJWT.getPayload();
        decodedJWT.getAlgorithm();
        Map<String, Object> header = new HashMap<>(2);
        header.put("Type", "Jwt");
        header.put("alg", "HS256");
        JWT.create().withHeader(header)
                .withClaim("nbf","1622903726")
                .withClaim("iat","1622903726")
                .withClaim("data","{\"uid\":164269022,\"username\":\"宇智波带人\",\"avatar\":\"dfc20e373d3d80d1e60c0623cef2c54e.jpeg\"}");
    }
}

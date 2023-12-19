package com.zjq.dailyrecord.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;


/**
 * 商户公司验签加解密工具类
 * @author 共饮一杯无
 */
public class RSAKeyUtil {
    public final static String USER_KEY = "gongyinyibeiwu";

    /**
     * 验签校验
     * @param jwtToken
     * @param priKey
     * @param pubKey
     * @return
     * @throws Exception
     */
    public static Boolean verify(String jwtToken, String priKey, String pubKey) throws Exception {
        RSAPublicKey publicKey = RSAKeyUtil.defaultRSAPublicKey(pubKey);
        RSAPrivateKey privateKey = RSAKeyUtil.defaultRSAPrivateKey(priKey);
        //加密
        Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
        //解密
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(RSAKeyUtil.USER_KEY)
                .build();
        DecodedJWT jwt = null;
        try{
            jwt = verifier.verify(jwtToken);
            Claim subjectClaim  = verifier.verify(jwtToken).getClaim("data");
            String result = subjectClaim.asString();
            System.out.println("解密结果："+result);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return !StringUtils.isEmpty(jwt);
    }

    /**
     * 加密
     * @param dataJson
     * @param priKey
     * @param pubKey
     * @return
     */
    public static String encodeJwtToken(String dataJson, String priKey, String pubKey) {
        try {
            RSAPublicKey publicKey = defaultRSAPublicKey(pubKey);
            RSAPrivateKey privateKey = defaultRSAPrivateKey(priKey);
            //加密
            Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
            String token = JWT.create()
                    .withIssuer(RSAKeyUtil.USER_KEY)
                    //.withExpiresAt(expiresAt)
                    .withClaim("data", dataJson)
                    .sign(algorithm);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static RSAPublicKey defaultRSAPublicKey(String pubKey) throws Exception {
        RSAPublicKey publicKey = getRSAPublicKey(pubKey);
        return publicKey;
    }

    public static RSAPrivateKey defaultRSAPrivateKey(String priKey) throws Exception {
        RSAPrivateKey privateKey = getRSAPrivateKey(priKey);
        return privateKey;
    }

    public static RSAPublicKey getRSAPublicKey(String publicKey) throws Exception {
        byte[] publicBytes = decryptBASE64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        return pubKey;
    }

    public static RSAPrivateKey getRSAPrivateKey(String privateKey) throws Exception {
        byte[] clear = decryptBASE64(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(clear);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        RSAPrivateKey priKey = (RSAPrivateKey) fact.generatePrivate(keySpec);
        Arrays.fill(clear, (byte) 0);
        return priKey;
    }

    /**
     * 解码返回byte
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    public static void main(String[] args) {
//        当前平台的公钥
        String ownPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgBu0tGxKBDDUzEW498JYS5ibAtCTxXcw7fEALMek3D6mfbV9JOe10+Ukf9CSRYd4FEhjaq63Vy57mVs/Bmse9RQcv2AxyCBThwKJELA8kH2B8ulYkwlthEyz5/HJ49eLKljak9+b5Ya6uHTo3d5xIhXLc20IzW1O4QsxJVkmkTYS0jruFx3YrZdiiluawCYdmAUQ34JONS4cgsMrjwF8sRNsZ6Vu0trCttY94i2NYWJU1X0XCMWcYoTKLCenMa4XMoP+cgI1Q+6Ni4zZbCLMkwxo7rr+LBD6jpzrfi9/p15PdnzGFvOr+ZCQbKwemex+GuR86Xq/+ZozHK9YIjrcvQIDAQAB";
//        当前平台的私钥
        String ownPriKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCAG7S0bEoEMNTMRbj3wlhLmJsC0JPFdzDt8QAsx6TcPqZ9tX0k57XT5SR/0JJFh3gUSGNqrrdXLnuZWz8Gax71FBy/YDHIIFOHAokQsDyQfYHy6ViTCW2ETLPn8cnj14sqWNqT35vlhrq4dOjd3nEiFctzbQjNbU7hCzElWSaRNhLSOu4XHditl2KKW5rAJh2YBRDfgk41LhyCwyuPAXyxE2xnpW7S2sK21j3iLY1hYlTVfRcIxZxihMosJ6cxrhcyg/5yAjVD7o2LjNlsIsyTDGjuuv4sEPqOnOt+L3+nXk92fMYW86v5kJBsrB6Z7H4a5Hzper/5mjMcr1giOty9AgMBAAECggEANjoEEr8n0YBOAy2cLxvPvigZrWZWtqZOStnRxiK38RZ/6QRStbVzLP94pLXHxLCkLom5s2XDa57caLzL/86GKx1ZUdTJHbo5QRPcqvi+mjbWM9l8Sbka536ERLD/UXdijAlSdHeZiN2v2fZ6v7ex0cjB9tj3eeVEF1RieDm1bo+IffHsiDKidfJ3/VaG5rlOzxQvxq22R9BBh5I8C1N3OnDX+A4R4lqy85OTH5+IQcJh4Z4lDP2gMHMqDJDoa6yxOB4UdL9gmfWDx2TBD9Y0XbaILDRGHG/gdPPtUb7ybhuZ3GaRO0JfH6k0oeGo+g7dAOTkyZlkPFa++jgXf/YYrQKBgQC/Ar3E54fZAzdQv0f9uBtEhtgT4Um0OcXZin6WUqBbcYwH1XnATPxPpQG0SNpRDWlKiBVSGH2d9OcE6Rxtal3IbXR/nTtGQumWSFJ8cYIaQGS22Bynb5e5H5XXfgXZ+0qlGgMW3WeqJv7f47g/W14w8rBf4zCbQ44JALd3+CraBwKBgQCrshV+86pjEQfWzuuGGNOaVt4GEyomheGGxWd1vncDm6TwFYixiWEnt/0ZKRXjN7gmljv07u17WZHANRKeHJJ5mYTfge/ZKrrY5LUorWA3lpzTbFEdOof+ea7FUujfZ0VcFmRtEb1yyOwtxTNyUSuwJn9OzmbhsGZngUGtIbuyGwKBgBy4TsxSe8yXfTO47xwpGIB/PfIPR8O/hA4nks0Lc20Mb5+l636MlMts5gqzgY/6UkCQoZQMdqbPcgT8//c7rQo72u5tN8JiwTiFe1GWx5cm433SlMxgLRH6u88A9eRGsnyMorZHaBTfdCc52DQ+irUVaIuiX2aZC7wyzWNOfzL1AoGADEbJfrBRiI/ZLaTR9l6kEq8PZQPNyb9c5tQKl8Kso9dnLbt8cKVQCxT+xePIKtz7D9dCJjtHQ8CdyU6CLEgCuSse8xRJYA/MGGISCfyLmq3sPLnL+vkKbEmrE3TgLckmjnUTbTENiL2RFZy6Fvxy0T+PbsUXWh/Q0qnNVmHJF+0CgYBstz+ESSvKJhSCNpWccnsCNyza9gy1KrgHFQn8RbJ/ss4s21iXEBZYEex2sjwFUZmH2xUxeV45x2zOyCrxJrHqgaA+0og8CmD5mqLDpliiBOpIQr2Du7DLMUAQDZ4oJvkul/ADQ3HJcCcMqQs7xLI0PzIKHjhh/IeWtxbQDDp72g==";

//        对接平台公钥
        String otherPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4A6SXgt/nNh76Hn85pZxvh2mRv2y4VWfrwM8lFv7urzJ3xt5FocujjEoQT3Q5jeByxiZaOmPk6mfpTTCsel5vGZqLtM8mrNKoPrXiKuq0U0sWUpXdlRLaFV+SMujTsuYl76get9kLVjwXRu4qlhtZlLlWcLpNu6wEu1JwY8Kl9BDP46X0J+IoL+f9iB0i8PAw71n6NJmGk4bvYAjwnMd9zMPdY+s9hfbv2OILdGkSAcaZGoan03HK8u5JBikMZOvhXHkNkgBtRX8ATqCbYK4fTAC70RdOwqbyOhcW/qie18KnfZJsINb7cPfN406wIK6rMapCx7RJ7eu7Ey0Hm8GGQIDAQAB";
//        对接平台私钥
        String otherPriKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDgDpJeC3+c2HvoefzmlnG+HaZG/bLhVZ+vAzyUW/u6vMnfG3kWhy6OMShBPdDmN4HLGJlo6Y+TqZ+lNMKx6Xm8Zmou0zyas0qg+teIq6rRTSxZSld2VEtoVX5Iy6NOy5iXvqB632QtWPBdG7iqWG1mUuVZwuk27rAS7UnBjwqX0EM/jpfQn4igv5/2IHSLw8DDvWfo0mYaThu9gCPCcx33Mw91j6z2F9u/Y4gt0aRIBxpkahqfTccry7kkGKQxk6+FceQ2SAG1FfwBOoJtgrh9MALvRF07CpvI6Fxb+qJ7Xwqd9kmwg1vtw983jTrAgrqsxqkLHtEnt67sTLQebwYZAgMBAAECggEAHh23TM7M21854HitJq1oIn7Hv9kP9zvUF+kedm6aunHvzH5b20xWVKSE3agacSda2dq3nCNwug9dtw4NcW6Jf2pgsWSRS9X3nQW6fNNeRX3TDTlx4iTYcfCz5cMBc3DoVNU5qupmA7ZlsI4uVy9FnTBdRaBuSoTww8qDVHIOoXMLenXArosyQEytxEL1uBGF8xijBWC5CGQfLjegcqYiVJ7Bl5kGXGwo0q0dzTabx/P4sH1BBRWMkTEUgfBZs4QvFiTfCIB0/X44cIHpokBnaeYGKX7aVVDIRni1ot3gzYDgBnIGM9ZY/CjLn0mrnZciVr8SemTyobGf/GlPoID+xQKBgQD4HLuAQR1SqiNwuB+lDz0hYqFL/r3VvEa4Wety7lznppz9EWeDEnN89bk9+RXxq+cSg7EwGdI+LGNeZpShpCBrb/93VYN/kohywb6vgZJ3gjT50J+8mmvxm0cE61xZ+VGkAKn1QqO6voF17mOcHD4k/h1je0SVj7d40q2QbQwKWwKBgQDnLhCF9JZE23gPP2CCccQlA4QHR89i6YwQpFWyINIJA/p42cdVPErprQqsBDBoV5sk9GX9X08GBsU2tDDvOxCsQjPShe/pVSvdCg8aFnQzi5r8lEhGpirH16N9LHRx2dsFvoN/h+zqbuF4MRdmsPmUb6UFs3CnGfvraWo/OCATmwKBgAsS1TeDMHjZCR3lydvHE1ZjHYnTw8s/TPS7ZqvJgbIBGK6e0TzjXO4t1Wezr0wK/RQxdn4MGPsXZhP3hhi0x5Gj/QXpdiYCdz6P8R/KK6xTzmN78TUsuzacVI4epw+I9iDYur0sjTwvdn259w4noIpsIQmoncYDFRA6bu6sP0ZTAoGBAIra/Em5c1jAigh+hLVVJ/8jcXX+B+7dYKfOTTrEw+NuZE+aX0QvBr/8k+BmU70YgbNcqLXbPVfdUS9eY9YNoCIXUZEtqcB4y/PkStXcjsc3H0x+tDrbK+8E8soInZiUxh1ZJRrAxei52OuccPXZbs9dj70w3oU/8jSgCJUYXQvpAoGBAIv+AsEpjKnSCukCftO4XBdRFgyf4+XXbnqOqThHzUlSd02qk2aijjs9XFMtnoBobqOM/jDhh6uYSX5vWJtrarlgX7cvKD9xDroC54RmDnDfOl0CfN5DaffofsD8+z6MSQU1FN1PYigkqfDHCKCFRL4Hp90PvGuynejhAl8x1OfC";

        String jsonStr = "{\n" +
                "    \"name\":\"共饮一杯无\"\n" +
                "}";

//        1.先加密
        String jwtToken = RSAKeyUtil.encodeJwtToken(jsonStr,ownPriKey,otherPubKey);
        System.out.println(jwtToken);

        try {
//            2.解密
            Boolean verify =  RSAKeyUtil.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJkYXRhIjoie1xuICAgIFwibmFtZVwiOlwi5YWx6aWu5LiA5p2v5pegXCJcbn0iLCJpc3MiOiJnb25neWlueWliZWl3dSJ9.BGat7UWtc75ZyTMoySrtBOLyED9iNBOJOvw198_yg6ZbjCuitFIvl4p7qs9v2oo3BoAPWH8gKLQsLLhsUQ6p9thcCimB0ClTJkxQlxd_2I8J-fli2Jmc09I53mIptNOtfB_zDn5UuXyVtB5-aD_mUs9xLXkEb8DJbMl4__LHDQyr1v3ixh0rq_e5LaY0ZOfSq2l06MaSAssdKQRvN_H0snO0KJkodcPktfRr4hOXI_ZvcbZHgiuv6JUpbp6x7T9BWMvixD7TTd-lqqm2OsqGOCtWJ4URSJmjYdLDswHY89jNzfVHrQ6exuUIeranWJoRf2-smE8KpLAN8kxs1o1Rpg"
                    ,otherPriKey,ownPubKey);
            System.out.println(verify);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

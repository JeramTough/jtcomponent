package test.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jeramtough.jtcomponent.key.bean.RsaKeysProvider;
import com.jeramtough.jtcomponent.key.util.KeyUtil;
import com.jeramtough.jtcomponent.utils.Base64Util;
import com.jeramtough.jtlog.facade.L;
import org.junit.jupiter.api.Test;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * Created on 2018-09-17 23:56
 * by @author JeramTough
 */
public class KeyTest {

    @Test
    public void rsaTest() {
        L.debugs("start to encrypt");

        String issuer = "JeramTough";
        String uid = "12231";
        RsaKeysProvider rsaKeysProvider = KeyUtil.getRsaKeysProvider();

        RSAPublicKey publicKey = rsaKeysProvider.getRsaPublicKey();
        RSAPrivateKey privateKey = rsaKeysProvider.getRsaPrivateKey();

        String publicKeyBase64String = Base64Util.toBase64Str(publicKey.getEncoded());
        String privateKeyBase64String = Base64Util.toBase64Str(privateKey.getEncoded());

        Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
        String token = JWT.create()
                          .withClaim("uid", uid)
                          .withIssuer(issuer)
                          .sign(algorithm);
        L.debug(token);


        //Verify a Token
        try {
            RsaKeysProvider rsaKeysProvider1 =
                    KeyUtil.getRsaKeysProvider(publicKeyBase64String,
                            privateKeyBase64String);
            RSAPublicKey rsaPublicKey1 = rsaKeysProvider1.getRsaPublicKey();
            RSAPrivateKey rsaPrivateKey1 = rsaKeysProvider1.getRsaPrivateKey();
            Algorithm algorithm1 = Algorithm.RSA256(rsaPublicKey1, rsaPrivateKey1);
            JWTVerifier verifier = JWT.require(algorithm1)
                                      .withIssuer(issuer)
                                      .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            L.info("verify successfully");

            Map<String, Claim> claims = jwt.getClaims();    //Key is the Claim name
            Claim claim = claims.get("uid");

            L.debugs("get uid by token:"+claim.asString());
        }
        catch (JWTVerificationException exception) {
            L.info("verify unsuccessfully");
        }

    }

    @Test
    public void md5Test(){
        L.debug(KeyUtil.to16Hex32LengthString(KeyUtil.encryptByMD5("a73979901995"),false));
    }
}



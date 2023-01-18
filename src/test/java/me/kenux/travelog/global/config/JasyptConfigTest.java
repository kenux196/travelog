package me.kenux.travelog.global.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.security.KeyFactorySpi;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JasyptConfigTest {

    @Qualifier("encryptorBean")
    @Autowired
    private StringEncryptor encryptor;


    @Test
    @DisplayName("Jasypt 암호화 복호화 테스트")
    void test1() {
        // give
        String source = "starlight";

        // when
        final String enc = encryptor.encrypt(source);
        final String dec = encryptor.decrypt(enc);

        // then
        assertThat(dec).isEqualTo(source);

        System.out.println("source = " + source);
        System.out.println("enc = " + enc);
        System.out.println("dec = " + dec);
    }

    @Test
    void makeEncryptedPassword() {
        String password = "pass";

        final String enc = encryptor.encrypt(password);

        System.out.println("enc = " + enc);
    }

    @Test
    void generateJwtToken() {
        final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        final String secretString = Encoders.BASE64.encode(secretKey.getEncoded());
        final String encrypt = encryptor.encrypt(secretString);
        System.out.println("encrypt = " + encrypt);
    }
}

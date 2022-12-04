package me.kenux.travelog.global.config;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

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
        String password = "rotkfrn";

        final String enc = encryptor.encrypt(password);

        System.out.println("enc = " + enc);
    }
}

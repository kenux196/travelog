package me.kenux.travelog.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource messageSource;

    @Test
    void titleMessage() {
        final String message = messageSource.getMessage("title", null, null);
        assertThat(message).isEqualTo("제목");
    }

    @Test
    void getLoginMessage_en() {
        final String message = messageSource.getMessage("title", null, Locale.US);
        assertThat(message).isEqualTo("title");
    }
}

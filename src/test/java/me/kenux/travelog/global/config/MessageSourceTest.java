package me.kenux.travelog.global.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MessageSourceTest {

    @Autowired
    MessageSource messageSource;

    @Test
    void getMessageTitle() {
        final String title = messageSource.getMessage("title", null, null);
        assertThat(title).isEqualTo("제목");
    }

    @Test
    void getMessageTitle_en() {
        final String title = messageSource.getMessage("title", null, Locale.US);
        assertThat(title).isEqualTo("title");
    }
}

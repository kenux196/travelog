package me.kenux.travelog.domain.member.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserPasswordTest {

    @Test
    @DisplayName("패스워드가 변경되면 변경된 날짜도 업데이트한다.")
    void changePassword() throws InterruptedException {
        // given
        final UserPassword password = new UserPassword("before");
        final OffsetDateTime lastChangedDate = password.getLastChangedDate();

        // when
        Thread.sleep(10);
        password.changePassword("after");

        // then
        assertThat(password.getLastChangedDate()).isNotEqualTo(lastChangedDate);
    }
}
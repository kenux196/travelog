package me.kenux.travelog.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TravelLogTest {

    @Test
    @DisplayName("여행 타입은 필수이다.")
    void travelTypeIsNotNull() {
        final TravelLog history = TravelLog.builder()
            .title("title")
            .content("content")
            .build();


    }
}

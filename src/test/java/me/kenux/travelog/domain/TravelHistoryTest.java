package me.kenux.travelog.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TravelHistoryTest {

    @Test
    @DisplayName("여행 타입은 필수이다.")
    void travelTypeIsNotNull() {
        final TravelHistory history = TravelHistory.builder()
            .title("title")
            .content("content")
            .build();


    }
}

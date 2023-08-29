package me.kenux.travelog.domain.destination.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DestinationTest {

    @Test
    void createDestinationTest() {
        final Destination destination = new Destination("target", "city", "info");
        assertThat(destination).isNotNull();
    }

}

package me.kenux.travelog.zstudy;

import com.fasterxml.uuid.Generators;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

class UUIDTest {

    @Test
    void basicUUIDTest() {
        final UUID uuid = UUID.randomUUID();
        System.out.println("uuid = " + uuid);
    }

    @Test
    void timeBasedUUIDTest() {
        final UUID timeBaseUUID = Generators.timeBasedGenerator().generate();
        final int version = timeBaseUUID.version();
        System.out.println("version = " + version);
        System.out.println("timeBaseUUID = " + timeBaseUUID);
        final long timestamp = timeBaseUUID.timestamp();
        System.out.println("timeBaseUUID.timestamp() = " + timestamp);
        System.out.println("current = " + System.currentTimeMillis());
        final Instant instant = Instant.ofEpochMilli(timestamp);
        System.out.println("time = " + OffsetDateTime.ofInstant(instant, ZoneId.of("Asia/Seoul")));

        final LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Seoul"));
        System.out.println("localDateTime = " + localDateTime);

    }
}

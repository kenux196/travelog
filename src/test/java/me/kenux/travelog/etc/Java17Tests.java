package me.kenux.travelog.etc;

import me.kenux.travelog.domain.member.entity.enums.MemberStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Java17Tests {

    @Test
    void switchTest() {
        MemberStatus status = MemberStatus.BLOCKED;
        String result = getUserStatus(status);
        assertThat(result).isEqualTo("blocked user");
    }

    private String getUserStatus(MemberStatus status) {
        return switch (status) {
            case BLOCKED -> "blocked user";
            case DORMANCY -> "dormancy user";
            default -> "normal user";
        };
    }

    @Test
    void recordTest() {
        final Point point = new Point(1, 2);
        System.out.println("point.x = " + point.x);
        System.out.println("point.y = " + point.y());

//        point.x = 3; => error
        assertThat(point.x).isEqualTo(1);
        assertThat(point.y()).isEqualTo(2);
    }

    record Point(int x, int y) {

    }
}

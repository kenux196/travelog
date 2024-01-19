package me.kenux.travelog.zstudy;

import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.enums.UserRole;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class JavaFakerTest {

    private Faker faker;

    @BeforeEach
    void init() {
        faker = new Faker(new Locale("ko"));
    }

    @Test
    void nameTest() {
        System.out.println("name.name() = " + faker.name().name());
        System.out.println("faker.name().username() = " + faker.name().username());
        System.out.println("faker = " + faker.name().fullName());
    }

    @Test
    void addressTest() {
        System.out.println("faker.address().fullAddress() = " + faker.address().fullAddress());
        System.out.println("faker.address().country() = " + faker.address().country());
    }

    @Test
    void userTest() {
        List<Member> members = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String emailLocalPart = "member" + i;
            final Member member = Member.builder()
                    .name(faker.name().name())
                    .email(faker.internet().safeEmailAddress(emailLocalPart))
                    .userRole(UserRole.USER)
                    .build();
            members.add(member);
            System.out.println("member = " + member);
        }
    }
}

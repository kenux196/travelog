package me.kenux.travelog.repository;

import me.kenux.travelog.domain.Camp;
import me.kenux.travelog.domain.SiteInfo;
import me.kenux.travelog.domain.StarPoint;
import me.kenux.travelog.domain.enums.SiteType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class CampRepositoryTest {
    @Autowired
    private EntityManager em;

    @Autowired
    private CampRepository campRepository;

    @Autowired
    private SiteInfoRepository siteInfoRepository;
    @Autowired
    private StarPointRepository starPointRepository;

    final Random random = new Random();

    @BeforeEach
    void init() {
        List<Camp> campList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final Camp camp = Camp.builder()
                .name("base camp")
                .build();
            campList.add(camp);
            campRepository.save(camp);
        }

        List<SiteInfo> siteInfos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final SiteInfo siteInfo = SiteInfo.builder()
                .siteNumber("A-" + (i + 1))
                .width(10)
                .height(9)
                .type(SiteType.STONE)
                .price(30000)
                .build();
            siteInfo.setCamp(campList.get(0));
            siteInfos.add(siteInfo);
//            siteInfoRepository.save(siteInfo);
        }
        siteInfoRepository.saveAll(siteInfos);

        for (int i = 0; i < 100; i++) {
            final StarPoint starPoint = new StarPoint(random.nextInt(5), campList.get(0));
            starPointRepository.save(starPoint);
        }

        em.flush();
        em.clear();
    }

    @Test
    void save() {
        final Camp camp = Camp.builder()
            .name("base camp")
            .build();

        campRepository.save(camp);

        final Optional<Camp> findCamp = campRepository.findById(camp.getId());
        assertThat(findCamp).isPresent()
            .hasValue(camp);
    }

    @Test
    void findName() {
        final String campName = campRepository.findNameById(1L);
        assertThat(campName).isEqualTo("base camp");
    }

    @Test
    void find1() {
        System.out.println("=============== start");
        final Optional<Camp> findCamp = campRepository.findById(1L);
        System.out.println("=============== find");
        findCamp.ifPresent(camp -> System.out.println("camp = " + camp));
        System.out.println("=============== end");
    }

    @Test
    void findAllFetch() {
        System.out.println("=============== start");
        final List<Camp> camps = campRepository.findAllFetch();
        System.out.println("=============== find");
        for (Camp camp : camps) {
            System.out.println("camp = " + camp);
        }
    }

    @Test
    void findOneWithBatchSize() {
        System.out.println("=============== start");
        final List<Camp> camps = campRepository.findAll();
        System.out.println("=============== find");
        for (Camp camp : camps) {
            System.out.println("camp = " + camp);
        }
    }
}

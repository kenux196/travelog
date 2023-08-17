package me.kenux.travelog.domain.device;

import jakarta.persistence.EntityManager;
import me.kenux.travelog.RepositoryTestConfigure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class DeviceRepositoryTest extends RepositoryTestConfigure {

    @Autowired
    private EntityManager em;
    @Autowired
    private DeviceRepository repository;

    private final int totalCount = 10;

    @BeforeEach
    void setup() {
        final OffsetDateTime dateTime = OffsetDateTime.of(2023, 1, 1, 1, 1, 1, 0, ZoneOffset.UTC);
        for (int i = 0; i < totalCount; i++) {
            Device device = Device.builder()
                    .name("monitor-" + i)
                    .company(getCompanyName(i))
                    .price(i * 100)
                    .createdDate(dateTime.plusDays(i))
                    .build();
            em.persist(device);
        }
        em.flush();
        em.clear();
    }

    private String getCompanyName(int i) {
        final int value = i % 3;
        if (value == 0) {
            return "companyA";
        } else if (value == 1) {
            return "companyB";
        } else {
            return "companyC";
        }
    }

    @Test
    void findAllByJPA() {
        final List<Device> result = repository.findAll();
        assertThat(result).hasSize(totalCount);
    }

    @Test
    void findAllWithSort() {
        final Sort sort = Sort.by(
                Sort.Order.desc("name"),
                Sort.Order.asc("company")
        );
        final List<Device> result = repository.findAll(sort);
        display(result);
    }

    @Test
    void findAllWithNativeQuery() {
        final Sort sort = Sort.by(
                Sort.Order.desc("name")
//                ,Sort.Order.asc("company")
        );

//        final PageRequest pageRequest = PageRequest.of(0, 10);
//        final Page<Device> result = repository.findAllByNativeQuery(pageRequest);
//        System.out.println("result = " + result);
        String sql = "select * from device";
        List<Device> result = em.createNativeQuery(sql)
//                .setFirstResult(0)
//                .setMaxResults(10)
                .getResultList();
        System.out.println("result = " + result);
    }

    private void display(List<Device> list) {
        list.forEach(device -> System.out.println("device = " + device));
    }


}


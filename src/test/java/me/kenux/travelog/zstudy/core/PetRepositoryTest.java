package me.kenux.travelog.zstudy.core;

import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.RepositoryTestConfigure;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class PetRepositoryTest extends RepositoryTestConfigure {

    @Autowired
    private PetRepository petRepository;
    private final Faker faker = new Faker();
    private final List<Pet> petList = new ArrayList<>();

    @BeforeEach
    void setup() {
        insertPetData();
    }

    @Test
    void findAllPetTest() {
        final List<Pet> result = petRepository.findAll();
        assertThat(result).hasSize(petList.size());
    }

    @Test
    void test_findAll_withDto() {
        final List<PetDto> result = petRepository.findAllByPetType(PetType.DOG);
        for (PetDto petDto : result) {
            log.info(petDto.toString());
        }
    }

    private void insertPetData() {
        OffsetDateTime start = OffsetDateTime.of(2022, 1, 1, 13, 0, 0, 0, ZoneOffset.UTC);
        for (int i = 0; i < 100; i++) {
            final Pet pet = Pet.builder()
                    .name(faker.cat().name())
                    .petType(randomPetType())
                    .age(i % 15)
                    .registrationDate(start.plusWeeks(i))
                    .build();
            petRepository.save(pet);
            petList.add(pet);
            log.info("Pet : " + pet);
        }
    }

    private PetType randomPetType() {
        final Random random = new Random();
        final int i = random.nextInt() % 3;
        if (i == 0) {
            return PetType.CAT;
        } else if (i == 1) {
            return PetType.DOG;
        } else {
            return PetType.BIRD;
        }
    }
}

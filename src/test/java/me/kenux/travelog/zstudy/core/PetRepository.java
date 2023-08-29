package me.kenux.travelog.zstudy.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query(value = "select p from Pet p where p.name = :petName")
    List<Pet> findAllByPetName(String petName);

    @Query(value = "select new me.kenux.travelog.zstudy.core.PetDto(p.id, p.name, p.petType) from Pet p where p.petType = :petType")
    List<PetDto> findAllByPetType(PetType petType);
}

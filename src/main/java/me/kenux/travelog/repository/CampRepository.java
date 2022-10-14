package me.kenux.travelog.repository;

import me.kenux.travelog.domain.Camp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CampRepository extends JpaRepository<Camp, Long> {

    @Query("select c.name from Camp c where c.id = :id")
    String findNameById(@Param("id") Long id);

    @Query("select distinct c from Camp c join fetch c.siteInfos")
    List<Camp> findAllFetch();
}

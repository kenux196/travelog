package me.kenux.travelog.domain.device;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    @Query(nativeQuery = true, value = "select d.* from device d")
    List<Device> findAllByNativeQuery();

//    @Query(nativeQuery = true, value = "select d.* from device d")
//    List<Device> findAllByNativeQuery(Sort sort);

    @Query(nativeQuery = true,
            value = "select * from device",
            countQuery = "select count(*) from device"
    )
    Page<Device> findAllByNativeQuery(Pageable pageable);
}

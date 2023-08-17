package me.kenux.travelog.domain.device;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "device")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String company;

    private OffsetDateTime createdDate;

    private Integer price;

    @Builder
    public Device(String name, String company, OffsetDateTime createdDate, Integer price) {
        this.name = name;
        this.company = company;
        this.createdDate = createdDate;
        this.price = price;
    }
}

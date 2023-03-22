package me.kenux.travelog.domain.destination.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "destination")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String information;

    public Destination(String name, String address, String information) {
        this.name = name;
        this.address = address;
        this.information = information;
    }
}

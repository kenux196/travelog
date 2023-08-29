package me.kenux.travelog.zstudy.core;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "pet")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "pet_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PetType petType;

    private Integer age;

    private OffsetDateTime registrationDate;

    private OffsetDateTime reservedTime;

    public Pet(String name, PetType petType) {
        this.name = name;
        this.petType = petType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_owner_id")
    private PetOwner petOwner;

    public void updateOwner(PetOwner owner) {
        this.petOwner = owner;
    }

    public void reserveVisit(OffsetDateTime reservedTime) {
        this.reservedTime = reservedTime;
    }

    @Builder
    public Pet(String name, PetType petType, Integer age, OffsetDateTime registrationDate) {
        this.name = name;
        this.petType = petType;
        this.age = age;
        this.registrationDate = registrationDate;
    }
}

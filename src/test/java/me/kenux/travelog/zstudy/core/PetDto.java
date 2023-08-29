package me.kenux.travelog.zstudy.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class PetDto {
    private Long id;
    private String name;
    private PetType petType;
}

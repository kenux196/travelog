package me.kenux.travelog.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "password")
public class Password extends BaseTimeEntity {

    @Id
    private String memberName;

    private String password;
}

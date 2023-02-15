package me.kenux.travelog.zstudy.core;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "book")
@Data
public class Book implements Serializable {

    @EmbeddedId
    private BookId bookId;

    private Integer price;


    @Data
    @Embeddable
    public static class BookId implements Serializable {

        @Column(name = "isbn")
        private String isbn;

        @Column(name = "name")
        private String name;
    }
}

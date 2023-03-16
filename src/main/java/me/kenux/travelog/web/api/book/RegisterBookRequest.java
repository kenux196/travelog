package me.kenux.travelog.web.api.book;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class RegisterBookRequest {

    List<BookInfoBase> bookInfoBases;
}
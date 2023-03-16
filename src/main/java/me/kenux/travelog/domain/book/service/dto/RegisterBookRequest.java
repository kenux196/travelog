package me.kenux.travelog.domain.book.service.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class RegisterBookRequest {

    List<BookInfoDto> bookInfos;
}
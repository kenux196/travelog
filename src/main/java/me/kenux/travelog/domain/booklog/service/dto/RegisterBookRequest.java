package me.kenux.travelog.domain.booklog.service.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class RegisterBookRequest {

    List<BookInfoDto> bookInfos;
}
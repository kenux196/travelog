package me.kenux.travelog.domain.booklog.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddBookLogRequest {

    private List<Long> bookIds;
}

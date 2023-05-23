package me.kenux.travelog.domain.booklog.service;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.booklog.repository.BookRepository;
import me.kenux.travelog.domain.booklog.repository.BookRatingRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookInfoService {

    private final BookRepository bookRepository;
    private final BookRatingRepository pointRepository;

    // TODO - 고객의 책 조회 기능 추가 2023-04-23 sky
}

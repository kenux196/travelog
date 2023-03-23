package me.kenux.travelog.domain.booklog.service;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.booklog.repository.BookRepository;
import me.kenux.travelog.domain.booklog.repository.dto.BookSearchCond;
import me.kenux.travelog.domain.booklog.service.dto.BookInfoDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookSearchService {

    private final BookRepository bookRepository;


    public List<BookInfoDto.BasicInfo> getBooks(BookSearchCond cond) {
        return bookRepository.findBooksByCondition(cond).stream()
                .map(BookInfoDto.BasicInfo::from)
                .toList();
    }
}
package me.kenux.travelog.domain.booklog.service;

import me.kenux.travelog.domain.booklog.entity.Book;
import me.kenux.travelog.domain.booklog.repository.BookRepository;
import me.kenux.travelog.domain.booklog.service.dto.BookInfoDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BookSearchServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookSearchService bookSearchService;

    @Test
    @DisplayName("특정 조건으로 책이 검색되어야 한다. ")
    void searchBookByCondition() {
        // given
        final Book book = Book.createNewBook("test", "author", "123", LocalDate.now(), "publisher");
        given(bookRepository.findBooksByCondition(any())).willReturn(Collections.singletonList(book));
        final BookInfoDto.BasicInfo basicInfo = BookInfoDto.BasicInfo.from(book);

        // when
        final List<BookInfoDto.BasicInfo> basicInfoList = bookSearchService.getBooks(any());

        // then
        assertThat(basicInfoList).hasSize(1).contains(basicInfo);
    }

}
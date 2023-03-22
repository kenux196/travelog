package me.kenux.travelog.domain.booklog.service;

import me.kenux.travelog.domain.booklog.entity.Book;
import me.kenux.travelog.domain.booklog.repository.BookRepository;
import me.kenux.travelog.domain.booklog.service.dto.RegisterBookRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class BookManagementServiceTest {

    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookManagementService bookManagementService;

    @Test
    @DisplayName("책 정보를 등록한다. 정상 성공")
    void registerBook() {
        // given
        RegisterBookRequest request = mock(RegisterBookRequest.class);
        List<Book> existBooks = new ArrayList<>();
        given(bookRepository.findAllByIsbn(any())).willReturn(existBooks);

        // when
        bookManagementService.addBook(request);

        // then
        BDDMockito.then(bookRepository).should(times(1)).saveAll(any());
    }
}
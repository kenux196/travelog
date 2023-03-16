package me.kenux.travelog.domain.book.service;

import me.kenux.travelog.domain.book.repository.BookRepository;
import me.kenux.travelog.domain.book.service.dto.RegisterBookRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class BookManagementServiceTest {

    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookManagementService bookManagementService;

    @Test
    @DisplayName("책 정보를 등록한다.")
    void registerBook() {
        // given
        RegisterBookRequest request = mock(RegisterBookRequest.class);

        // when
        bookManagementService.registerBook(request);

        // then
        BDDMockito.then(bookRepository).should(times(1)).saveAll(any());
    }
}
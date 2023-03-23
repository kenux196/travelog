package me.kenux.travelog.domain.booklog.service;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.booklog.entity.Book;
import me.kenux.travelog.domain.booklog.repository.BookRepository;
import me.kenux.travelog.domain.booklog.service.dto.BookInfoDto;
import me.kenux.travelog.domain.booklog.service.dto.RegisterBookRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookManagementService {

    private final BookRepository bookRepository;

    @Transactional
    public void addBook(RegisterBookRequest request) {
        final List<String> isbnList = request.getBookInfos().stream()
                .map(BookInfoDto::getIsbn)
                .toList();

        final List<Book> foundBooks = getBooksByIsbn(isbnList);
        final List<Book> newBooks = request.getBookInfos().stream()
                .filter(bookInfoDto -> !existBook(foundBooks, bookInfoDto))
                .map(BookInfoDto::toEntity)
                .toList();
        bookRepository.saveAll(newBooks);
    }

    private static boolean existBook(List<Book> books, BookInfoDto bookInfo) {
        return books.stream()
                .anyMatch(book -> book.isSameBook(bookInfo.getTitle(), bookInfo.getIsbn()));
    }

    private List<Book> getBooksByIsbn(List<String> isbnList) {
        return bookRepository.findAllByIsbn(isbnList);
    }
}
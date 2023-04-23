package me.kenux.travelog.domain.booklog.service;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.booklog.entity.Book;
import me.kenux.travelog.domain.booklog.repository.BookRepository;
import me.kenux.travelog.domain.booklog.service.dto.AddBookRequest;
import me.kenux.travelog.domain.booklog.service.dto.BookInfoDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookManagementService {

    private final BookRepository bookRepository;

    @Transactional
    public void addBook(AddBookRequest request) {
        final List<String> isbnList = request.getBooks().stream()
                .map(AddBookRequest.BookInfo::getIsbn)
                .toList();

        final List<Book> foundBooks = getBooksByIsbn(isbnList);
        final List<Book> newBooks = request.getBooks().stream()
                .filter(bookInfo -> !existBook(foundBooks, bookInfo))
                .map(AddBookRequest.BookInfo::toEntity)
                .toList();
        bookRepository.saveAll(newBooks);
    }

    private boolean existBook(List<Book> books, AddBookRequest.BookInfo bookInfo) {
        return books.stream()
                .anyMatch(book -> book.isSameBook(bookInfo.getTitle(), bookInfo.getIsbn()));
    }

    private List<Book> getBooksByIsbn(List<String> isbnList) {
        return bookRepository.findAllByIsbn(isbnList);
    }
}

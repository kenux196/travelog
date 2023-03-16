package me.kenux.travelog.domain.book.service;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.book.entity.Book;
import me.kenux.travelog.domain.book.repository.BookRepository;
import me.kenux.travelog.domain.book.service.dto.BookInfoDto;
import me.kenux.travelog.domain.book.service.dto.RegisterBookRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookManagementService {

    private final BookRepository bookRepository;

    @Transactional
    public void registerBook(RegisterBookRequest request) {
        final List<String> isbnList = request.getBookInfos().stream()
                .map(BookInfoDto::getIsbn)
                .toList();
        final List<Book> existBooks = bookRepository.findAllByIsbn(isbnList);

        final List<Book> books = request.getBookInfos().stream()
                .filter(bookInfoDto ->
                        existBooks.stream()
                                .noneMatch(book -> book.getIsbn().equals(bookInfoDto.getIsbn())))
                .map(BookInfoDto::toEntity)
                .toList();
        bookRepository.saveAll(books);
    }
}
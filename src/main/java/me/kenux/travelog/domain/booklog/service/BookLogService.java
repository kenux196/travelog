package me.kenux.travelog.domain.booklog.service;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.booklog.entity.BookLog;
import me.kenux.travelog.domain.booklog.repository.BookLogRepository;
import me.kenux.travelog.domain.booklog.repository.BookRepository;
import me.kenux.travelog.domain.booklog.service.dto.BookLogResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookLogService {

    private final BookRepository bookRepository;
    private final BookLogRepository bookLogRepository;

    public List<BookLogResponse> getBookLogs(Long memberId) {
        final List<BookLog> bookLogs = bookLogRepository.findByMember(memberId);
        return bookLogs.stream()
                .map(BookLogResponse::from)
                .toList();
    }

}
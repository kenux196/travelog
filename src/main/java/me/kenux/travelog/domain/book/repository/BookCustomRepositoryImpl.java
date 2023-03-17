package me.kenux.travelog.domain.book.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.book.entity.Book;
import me.kenux.travelog.domain.book.entity.QBook;
import me.kenux.travelog.domain.book.repository.dto.BookSearchCond;
import me.kenux.travelog.domain.book.service.BookManagementService;
import org.springframework.util.StringUtils;

import java.util.List;

import static me.kenux.travelog.domain.book.entity.QBook.*;

@RequiredArgsConstructor
public class BookCustomRepositoryImpl implements BookCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Book> findBooksByCondition(BookSearchCond cond) {
        return queryFactory
                .select(book)
                .from(book)
                .where(containsTitle(cond), eqIsbn(cond), eqPublisher(cond), containsAuthor(cond))
                .fetch();
    }

    private BooleanExpression containsTitle(BookSearchCond cond) {
        if (StringUtils.hasText(cond.getTitle())) {
            return book.title.contains(cond.getTitle());
        }
        return null;
    }

    private BooleanExpression eqIsbn(BookSearchCond cond) {
        if (StringUtils.hasText(cond.getIsbn())) {
            return book.isbn.eq(cond.getIsbn());
        }
        return null;
    }

    private BooleanExpression eqPublisher(BookSearchCond cond) {
        if (StringUtils.hasText(cond.getPublisher())) {
            return book.publisher.eq(cond.getPublisher());
        }
        return null;
    }

    private BooleanExpression containsAuthor(BookSearchCond cond) {
        if (StringUtils.hasText(cond.getAuthor())) {
            return book.authors.contains(cond.getAuthor());
        }
        return null;
    }
}
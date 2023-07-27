package me.kenux.travelog.domain.booklog.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.booklog.entity.Book;
import me.kenux.travelog.domain.booklog.repository.dto.BookSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static me.kenux.travelog.domain.booklog.entity.QBook.book;

@RequiredArgsConstructor
public class BookCustomRepositoryImpl implements BookCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Book> findBooksByCondition(BookSearchCond cond) {
        return getFindBookJpaQuery(cond)
                .fetch();
    }

    private JPAQuery<Book> getFindBookJpaQuery(BookSearchCond cond) {
        return queryFactory
                .select(book)
                .from(book)
                .where(containsTitle(cond), eqIsbn(cond), eqPublisher(cond), containsAuthor(cond))
                .orderBy(book.publishedDate.desc());
    }

    @Override
    public Page<Book> findBooksByCondition(BookSearchCond cond, Pageable pageable) {
        final List<Book> contents = getFindBookJpaQuery(cond)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        final JPAQuery<Long> countQuery = queryFactory.select(book.count())
                .from(book)
                .where(containsTitle(cond), eqIsbn(cond), eqPublisher(cond), containsAuthor(cond));

        return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchOne);
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

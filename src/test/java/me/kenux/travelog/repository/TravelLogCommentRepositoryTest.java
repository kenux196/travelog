package me.kenux.travelog.repository;

import me.kenux.travelog.domain.TravelLogComment;
import me.kenux.travelog.global.config.QueryDslConfig;
import me.kenux.travelog.repository.base.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

class TravelLogCommentRepositoryTest extends RepositoryTest {

    @Autowired
    private TravelLogCommentRepository commentRepository;

    @Test
    void save() {
        final TravelLogComment comment = TravelLogComment.builder()
            .comment("댓글입니다.")
            .build();

        commentRepository.save(comment);
    }

}

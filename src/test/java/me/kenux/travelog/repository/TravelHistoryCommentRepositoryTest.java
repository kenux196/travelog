package me.kenux.travelog.repository;

import me.kenux.travelog.domain.TravelHistoryComment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("local")
class TravelHistoryCommentRepositoryTest {

    @Autowired
    private TravelHistoryCommentRepository commentRepository;

    @Test
    void save() {
        final TravelHistoryComment comment = TravelHistoryComment.builder()
            .comment("댓글입니다.")
            .build();

        commentRepository.save(comment);
    }

}

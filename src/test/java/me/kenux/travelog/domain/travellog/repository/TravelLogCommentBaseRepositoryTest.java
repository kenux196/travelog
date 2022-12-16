package me.kenux.travelog.domain.travellog.repository;

import me.kenux.travelog.domain.travellog.entity.TravelLogComment;
import me.kenux.travelog.base.BaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TravelLogCommentBaseRepositoryTest extends BaseRepositoryTest {

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

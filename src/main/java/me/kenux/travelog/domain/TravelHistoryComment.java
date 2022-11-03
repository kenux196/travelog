package me.kenux.travelog.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "travel_history_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TravelHistoryComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_history_id")
    private TravelHistory travelHistory;

    @Column(name = "parent_id")
    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", insertable = false, updatable = false)
    private TravelHistoryComment parentComment;

    @OneToMany(mappedBy = "parentComment", fetch = FetchType.LAZY)
    private List<TravelHistoryComment> childComments = new ArrayList<>();

    private OffsetDateTime createdTime;

    private OffsetDateTime updatedTime;
}

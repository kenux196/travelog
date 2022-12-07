package me.kenux.travelog.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "travel_log_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class TravelLogComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_log_id")
    private TravelLog travelLog;

    @Column(name = "parent_id")
    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private TravelLogComment parentComment;

    @OneToMany(mappedBy = "parentComment", fetch = FetchType.LAZY)
    @Builder.Default
    private List<TravelLogComment> childComments = new ArrayList<>();

    public void addCommentToHistory(TravelLog history) {
        travelLog = history;
        history.getComments().add(this);
    }
}

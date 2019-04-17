package ru.nsu.fit.bd.g16203.hotelInformationSystem.entities.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "review")
public class Review {
    private Long reviewId;
    private Room room;
    private Client client;
    private Integer score;
    private String comment;

    public Review() {
    }

    public Review(Room room, Client client, Integer score, String comment) {
        this.room = room;
        this.client = client;
        this.score = score;
        this.comment = comment;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "REVIEW_ID", unique = true, nullable = false)
    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "BUILDING", nullable = false),
            @JoinColumn(name = "FLOOR", nullable = false),
            @JoinColumn(name = "ROOM", nullable = false)
    }
    )
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT")
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Column(name = "SCORE", nullable = false)
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Column(name = "COMMENT")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

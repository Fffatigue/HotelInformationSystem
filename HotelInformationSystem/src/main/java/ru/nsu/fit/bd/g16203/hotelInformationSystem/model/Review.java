package ru.nsu.fit.bd.g16203.hotelInformationSystem.model;

public class Review implements Entity<Integer>{
    private Integer reviewId;
    private int score;
    private String comment;
    private int reservationId;

    public Review() {
    }

    public Review(Integer reviewId, RoomId roomId, Integer clientId, int score, String comment) {
        this.reviewId = reviewId;
        this.score = score;
        this.comment = comment;
    }

    public Review(int score, String comment) {
        this.score = score;
        this.comment = comment;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    @Override
    public Integer getPK() {
        return reviewId;
    }

    @Override
    public void setPK(Integer primaryKey) {
        reviewId = primaryKey;
    }
}

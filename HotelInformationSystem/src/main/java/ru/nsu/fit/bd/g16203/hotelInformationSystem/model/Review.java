package ru.nsu.fit.bd.g16203.hotelInformationSystem.model;

public class Review implements Entity<Integer>{
    private Integer reviewId;
    private RoomId roomId;
    private Integer clientId;
    private int score;
    private String comment;

    public Review() {
    }

    public Review(Integer reviewId, RoomId roomId, Integer clientId, int score, String comment) {
        this.reviewId = reviewId;
        this.roomId = roomId;
        this.clientId = clientId;
        this.score = score;
        this.comment = comment;
    }

    public Review(RoomId roomId, Integer clientId, int score, String comment) {
        this.roomId = roomId;
        this.clientId = clientId;
        this.score = score;
        this.comment = comment;
    }

    public RoomId getRoomId() {
        return roomId;
    }

    public void setRoomId(RoomId roomId) {
        this.roomId = roomId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
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

    @Override
    public Integer getPK() {
        return reviewId;
    }

    @Override
    public void setPK(Integer primaryKey) {
        reviewId = primaryKey;
    }
}

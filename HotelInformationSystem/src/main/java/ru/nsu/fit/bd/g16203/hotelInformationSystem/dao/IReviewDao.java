package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Review;

import java.sql.SQLException;
import java.util.List;

public interface IReviewDao extends GenericDao<Review, Integer> {
    public List<Review> getAllSort(String sortBy, Boolean sortAsc, int page) throws SQLException;
    public List<Review> getAllFilter(String sortBy, Boolean sortAsc, String comment, Integer score) throws SQLException;
}

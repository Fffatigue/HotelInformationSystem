package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;

import org.springframework.stereotype.Service;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Review;

import java.sql.SQLException;
import java.util.List;

@Service
public interface IReviewService extends GenericService<Review, Integer> {
    public List<Review> getAllSort(String sortBy, Boolean sortAsc, int page) throws SQLException;
    public List<Review> getAllFilter(String sortBy, Boolean sortAsc, String comment, Integer score) throws SQLException;
}

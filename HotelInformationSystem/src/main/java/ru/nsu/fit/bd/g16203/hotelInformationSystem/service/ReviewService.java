package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.IReviewDao;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.WrongDataException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Review;

import java.sql.SQLException;
import java.util.List;

@Service
public class ReviewService implements IReviewService {
    @Autowired
    private IReviewDao reviewDao;

    @Override
    public Review getByPK(Integer primaryKey) throws PersistException {
        return reviewDao.getByPK(primaryKey);
    }

    @Override
    public int getPageNum() throws SQLException {
        return reviewDao.getPageNum();
    }

    @Override
    public void update(Review obj) throws PersistException, SQLException, WrongDataException {
        reviewDao.update(obj);
    }

    @Override
    public void delete(Integer primaryKey) throws PersistException, WrongDataException {
        reviewDao.delete(primaryKey);
    }

    @Override
    public void create(Review obj) throws PersistException, SQLException, WrongDataException {
        reviewDao.create(obj);
    }

    @Override
    public List<Review> getAll(int page) throws PersistException {
        return reviewDao.getAll(page);
    }

    public List<Review> getAllSort(String sortBy, Boolean sortAsc, int page) throws SQLException {
        return reviewDao.getAllSort(sortBy, sortAsc, page);
    }

    @Override
    public List<Review> getAllFilter(String sortBy, Boolean sortAsc, String comment, Integer score) throws SQLException {
        return reviewDao.getAllFilter(sortBy, sortAsc, comment, score);
    }
}

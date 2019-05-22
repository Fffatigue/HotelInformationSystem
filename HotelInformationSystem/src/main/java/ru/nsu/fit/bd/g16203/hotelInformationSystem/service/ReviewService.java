package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.IReviewDao;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Review;

import java.util.List;

public class ReviewService implements IReviewService {
    @Autowired
    private IReviewDao reviewDao;

    @Override
    public Review getByPK(Integer primaryKey) throws PersistException {
        return reviewDao.getByPK(primaryKey);
    }

    @Override
    public void update(Review obj) throws PersistException {
        reviewDao.update(obj);
    }

    @Override
    public void delete(Integer primaryKey) throws PersistException {
        reviewDao.delete(primaryKey);
    }

    @Override
    public Review create(Review obj) throws PersistException {
        return reviewDao.create(obj);
    }

    @Override
    public List<Review> getAll() throws PersistException {
        return reviewDao.getAll();
    }
}

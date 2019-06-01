package ru.nsu.fit.bd.g16203.hotelInformationSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.WrongDataException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Review;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.service.IReviewService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    IReviewService reviewService;

    @RequestMapping("/id/{reviewId}")
    public Review getReview(@PathVariable int reviewId) throws PersistException {
        return reviewService.getByPK(reviewId);
    }

    @DeleteMapping("/id/{reviewId}")
    public void deleteReview(@PathVariable int reviewId) throws PersistException, WrongDataException {
        reviewService.delete(reviewId);
    }

    @PutMapping("/id/{reviewId}")
    public void updateReview (@PathVariable int reviewId, @RequestBody Review review) throws PersistException, SQLException, WrongDataException {
        review.setPK( reviewId );
        reviewService.update( review );
    }

    @PostMapping("/id/{reviewId}")
    public Review createReview(@PathVariable int reviewId, @RequestBody Review review) throws PersistException, SQLException, WrongDataException {
        review.setPK( reviewId );
        reviewService.create( review );
        return review;
    }

    @RequestMapping("page/{page}")
    public List<Review> getReviews(@PathVariable int page) throws PersistException {
        return reviewService.getAll(page);
    }
}

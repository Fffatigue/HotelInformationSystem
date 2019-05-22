package ru.nsu.fit.bd.g16203.hotelInformationSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Review;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.service.IReviewService;

@RestController
@RequestMapping("review{reviewId}")
public class ReviewController {
    @Autowired
    IReviewService reviewService;

    @RequestMapping
    public Review getReview(@PathVariable int reviewId) throws PersistException {
        return reviewService.getByPK(reviewId);
    }

    @DeleteMapping
    public void deleteReview(@PathVariable int reviewId) throws PersistException {
        reviewService.delete(reviewId);
    }

    @PutMapping
    public void updateReview (@PathVariable int reviewId, @RequestBody Review review) throws PersistException {
        review.setPK( reviewId );
        reviewService.update( review );
    }

    @PostMapping
    public Review createReview(@PathVariable int reviewId, @RequestBody Review review) throws PersistException {
        review.setPK( reviewId );
        review = reviewService.create( review );
        return review;
    }

    //TODO сделоть
}

package ru.nsu.fit.bd.g16203.hotelInformationSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Review;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.service.IReviewService;

import java.util.List;

@RestController
@RequestMapping("/reviews/{reviewId}")
public class ReviewController {
    @Autowired
    IReviewService reviewService;

    @RequestMapping("/{reviewId}")
    public Review getReview(@PathVariable int reviewId) throws PersistException {
        return reviewService.getByPK(reviewId);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable int reviewId) throws PersistException {
        reviewService.delete(reviewId);
    }

    @PutMapping("/{reviewId}")
    public void updateReview (@PathVariable int reviewId, @RequestBody Review review) throws PersistException {
        review.setPK( reviewId );
        reviewService.update( review );
    }

    @PostMapping("/{reviewId}")
    public Review createReview(@PathVariable int reviewId, @RequestBody Review review) throws PersistException {
        review.setPK( reviewId );
        reviewService.create( review );
        return review;
    }

    @RequestMapping
    public List<Review> getReviews(int page) throws PersistException {
        return reviewService.getAll(page);
    }
}

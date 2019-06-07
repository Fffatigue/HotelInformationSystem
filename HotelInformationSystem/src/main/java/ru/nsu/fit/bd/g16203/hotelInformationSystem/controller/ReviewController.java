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
    public int deleteReview(@PathVariable int reviewId) throws PersistException, WrongDataException, SQLException {
        reviewService.delete(reviewId);
        return  reviewService.getPageNum();
    }

    @PutMapping("/id/{reviewId}")
    public void updateReview (@PathVariable int reviewId, @RequestBody Review review) throws PersistException, SQLException, WrongDataException {
        review.setPK( reviewId );
        reviewService.update( review );
    }

    @PostMapping("/id")
    public Review createReview(@RequestBody Review review) throws PersistException, SQLException, WrongDataException {
        reviewService.create(review);
        return review;
    }

    @RequestMapping("page/{page}")
    public List<Review> getReviews(@RequestParam String sortBy, @RequestParam Boolean sortAsc, @PathVariable int page) throws SQLException {
        return reviewService.getAllSort(sortBy, sortAsc, page);
    }

    @RequestMapping("comment/{comment}/score/{score}")
    public List<Review> getReviewsAll(@RequestParam String sortBy, @RequestParam Boolean sortAsc, @PathVariable String comment, @PathVariable Integer score) throws SQLException {
        return reviewService.getAllFilter(sortBy, sortAsc, comment, score);
    }

    @RequestMapping("/page")
    public int getPageNum() throws SQLException {
        return reviewService.getPageNum();
    }

}

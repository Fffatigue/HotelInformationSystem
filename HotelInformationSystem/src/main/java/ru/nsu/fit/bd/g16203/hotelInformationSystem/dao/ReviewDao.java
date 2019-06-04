package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Review;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class ReviewDao extends AbstractJDBCDao<Review, Integer> implements IReviewDao {
    @Override
    public String getSelectQuery() {
        return "SELECT * FROM review ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO review (review_id, score, comment, reservation_id) \n" +
                "VALUES (DEFAULT, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE review SET score = ? comment = ? reservation_id = ? WHERE review_id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM review WHERE review_id = ?;";
    }

    @Override
    protected String getIdComparisionStatementPart() {
        return " WHERE review_id = ?;";
    }

    @Override
    protected void prepareStatementForGetByPK(PreparedStatement statement, Integer primaryKey) throws SQLException {
        statement.setInt(1, primaryKey);
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Review obj) throws SQLException {
        statement.setInt(1, obj.getScore());
        statement.setString(2, obj.getComment());
        statement.setInt(3, obj.getReservationId());
        statement.setInt(4, obj.getPK());
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Review obj) throws SQLException {
        statement.setInt(1, obj.getScore());
        statement.setString(2, obj.getComment());
        statement.setInt(3, obj.getReservationId());
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, Integer primaryKey) throws SQLException {
        statement.setInt(1, primaryKey);
    }

    @Override
    protected List<Review> parseResultSet(ResultSet rs) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        while (rs.next()) {
            Review review = new Review();
            review.setPK(rs.getInt("review_id"));
            review.setComment(rs.getString("comment"));
            review.setScore(rs.getInt("score"));
            review.setReservationId(rs.getInt("reservation_id"));
            reviews.add(review);
        }
        return reviews;
    }
}

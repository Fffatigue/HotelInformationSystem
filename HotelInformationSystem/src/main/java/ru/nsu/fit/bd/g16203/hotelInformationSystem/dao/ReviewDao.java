package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewDao extends AbstractJDBCDao<Review, Integer> implements IReviewDao {
    @Override
    public String getSelectQuery() {
        return "SELECT * FROM review ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO review (score, comment, reservation_id) \n" +
                "VALUES (?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE review SET score = ?, comment = ?, reservation_id = ? WHERE review_id = ?;";
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
    protected void checkDataCreate(Review obj) throws SQLException, WrongDataException {
        checkDataUpdate( obj );
    }

    @Override
    protected void checkDataUpdate(Review obj) throws SQLException, WrongDataException {
        if(obj.getScore()< 0 || obj.getScore()>10){
            throw new WrongDataException( "Score must be in 0-10 range" );
        }
        String sql = "SELECT * from reservation where reservation_id = ?";
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setInt( 1,obj.getReservationId() );
                ResultSet rs = statement.executeQuery();
                if(!rs.next()){
                    throw new WrongDataException( "Can't find reservation" );
                }
            }
        }
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

    public List<Review> getAllSort(String sortBy, Boolean sortAsc, int page) throws SQLException {
        String order = sortAsc ? "asc" : "desc";
        String sort = sortBy.equals("score") ? "score": "comment";
        String sql = "SELECT * from review\n" +
                " order by " + sort + " " + order + " LIMIT " + ROWS_PER_PAGE + " OFFSET " + String.valueOf( (page - 1) * ROWS_PER_PAGE );
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                ResultSet rs = statement.executeQuery();
                return parseResultSet(rs);
            }
        }
    }

    public List<Review> getAllFilter(String sortBy, Boolean sortAsc, String comment, Integer score) throws SQLException {
        String order = sortAsc ? "asc" : "desc";
        String sort = sortBy.equals("score") ? "score": "comment";
        String comm = "%" + comment + "%";
        String sql;
        if (comment.equals("lol") && score.equals(0)) {
            sql = "SELECT * from review\n" +
                    " order by " + sort + " " + order;try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
                try (PreparedStatement statement = c.prepareStatement( sql )) {
                    ResultSet rs = statement.executeQuery();
                    return parseResultSet(rs);
                }
            }
        } else if (comment.equals("lol")) {
            sql = "SELECT * from review\n" +
                    " where score = ? " +
                    " order by " + sort + " " + order;
            try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
                try (PreparedStatement statement = c.prepareStatement( sql )) {
                    statement.setInt(1, score);
                    ResultSet rs = statement.executeQuery();
                    return parseResultSet(rs);
                }
            }
        } else if (score.equals(0)) {
            sql = "SELECT * from review\n" +
                    " where comment like '" + comm + "' " +
                    " order by " + sort + " " + order;
            try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
                try (PreparedStatement statement = c.prepareStatement( sql )) {
                    ResultSet rs = statement.executeQuery();
                    return parseResultSet(rs);
                }
            }
        } else {
            sql = "SELECT * from review\n" +
                    " where score = ? and comment like '" + comm + "' " +
                    " order by "  + sort + " " + order;
            try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
                try (PreparedStatement statement = c.prepareStatement( sql )) {
                    statement.setInt(1, score);
                    ResultSet rs = statement.executeQuery();
                    return parseResultSet(rs);
                }
            }
        }
    }
}

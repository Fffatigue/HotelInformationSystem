package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import org.springframework.stereotype.Repository;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.UsedService;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.UsedServiceId;

import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class UsedServiceDao extends AbstractJDBCDao<UsedService, UsedServiceId> implements IUsedServiceDao{
    @Override
    public String getSelectQuery() {
        return "SELECT * FROM used_service ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO used_service (used_service_id) \n" +
                "VALUES (?);";
    }

    @Override
    public String getUpdateQuery() {
        return null;    //нечего обновлять, не айди же
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM used_service WHERE used_service_id = ?;";
    }

    @Override
    protected String getIdComparisionStatementPart() {
        return " WHERE used_service_id = ?;";
    }

    @Override
    protected void prepareStatementForGetByPK(PreparedStatement statement, UsedServiceId primaryKey) throws SQLException {
        statement.setInt(1, primaryKey.getReservationId());
        statement.setInt(2, primaryKey.getServiceId());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, UsedService obj) throws SQLException {
        statement.setInt(1, obj.getPK().getReservationId());
        statement.setInt(2, obj.getPK().getServiceId());
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, UsedService obj) throws SQLException {
        statement.setInt( 1, obj.getPK().getServiceId());
        statement.setInt( 2, obj.getPK().getReservationId());
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, UsedServiceId primaryKey) throws SQLException {
        statement.setInt( 1, primaryKey.getServiceId());
        statement.setInt( 2, primaryKey.getReservationId());
    }

    @Override
    protected List<UsedService> parseResultSet(ResultSet rs) throws SQLException {
        List<UsedService> services = new ArrayList<>();
        while (rs.next()) {
            UsedService service = new UsedService();
            UsedServiceId usedServiceId = new UsedServiceId();
            usedServiceId = (UsedServiceId) rs.getObject("used_service_id");
            service.setPK(usedServiceId);
            services.add(service);
        }
        return services;
    }
}

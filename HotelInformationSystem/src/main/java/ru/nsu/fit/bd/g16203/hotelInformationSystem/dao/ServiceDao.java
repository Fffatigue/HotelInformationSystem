package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import org.springframework.stereotype.Repository;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Service;

import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class ServiceDao extends AbstractJDBCDao<Service, Integer> implements IServiceDao {
    @Override
    public String getSelectQuery() {
        return "SELECT * FROM service ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO service (service_id, name, price) \n" +
                "VALUES (?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE service SET price = ? name = ? WHERE service_id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM service WHERE service_id = ?;";
    }

    @Override
    protected String getIdComparisionStatementPart() {
        return "WHERE service_id = ?;";
    }

    @Override
    protected void prepareStatementForGetByPK(PreparedStatement statement, Integer primaryKey) throws SQLException {
        //No get in Integer class in PK
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Service obj) throws SQLException {
        statement.setInt(1, obj.getPrice());
        statement.setString(2, obj.getName());
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Service obj) throws SQLException {
        statement.setInt(1, obj.getPrice());
        statement.setString(2, obj.getName());
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, Integer primaryKey) throws SQLException {
        //No get in Integer class for PK
    }

    @Override
    protected List<Service> parseResultSet(ResultSet rs) throws SQLException {
        List<Service> services = new ArrayList<>();
        while (rs.next()) {
            Service service = new Service();
            Integer serviceId = null;
            service.setPK(serviceId);
            services.add(service);
            service.setPrice(rs.getInt("price"));
            service.setName(rs.getString("name"));
        }
        return services;
    }
}
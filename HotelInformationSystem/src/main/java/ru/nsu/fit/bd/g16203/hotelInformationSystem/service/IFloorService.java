package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;

import org.springframework.stereotype.Service;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Floor;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.FloorId;

import java.sql.SQLException;
import java.util.List;

@Service
public interface IFloorService extends GenericService<Floor, FloorId> {
    public List<Floor> getAllByBuilding(int buildingId) throws SQLException;
}

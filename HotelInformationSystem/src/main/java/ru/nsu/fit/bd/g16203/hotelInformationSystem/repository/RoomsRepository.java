package ru.nsu.fit.bd.g16203.hotelInformationSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.entities.model.Room;

@Repository
public interface RoomsRepository extends JpaRepository<Room,Long>{
}

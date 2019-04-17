package ru.nsu.fit.bd.g16203.hotelInformationSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.entities.model.Floor;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.entities.model.Room;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.repository.RoomsRepository;

@RestController
public class MainController {

    @Autowired
    private RoomsRepository roomsRepository;

    @RequestMapping("/rooms/{id}/price")
    public Long getPrice(@PathVariable Long id) {
  //      return roomsRepository.getOne( id ).getPrice();
        return 5L;
    }

    @RequestMapping("/rooms/{id}/capacityByFloor")
    public Long getFloorCapacityByRoom(@PathVariable Long id){
 //       Floor floor = roomsRepository.getOne( id ).getFloor();
        long capacity = 0;
    //    for(Room room : floor.getRooms()){
    //        capacity+=room.getCapacity();
    //    }
        return capacity;
    }
}

package main.java.service;


import main.java.dao.MarkerRepository;
import main.java.entity.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkerService {

    @Autowired
    private MarkerRepository markerRepository;

    public List<Marker> getAllMarkers(){
        return markerRepository.getAllMarkers();
    }
}

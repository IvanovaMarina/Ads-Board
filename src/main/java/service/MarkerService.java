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

    public Marker add(Marker marker) {
        Marker existingMarker = markerRepository.getOne(marker.getName());
        if (existingMarker != null) {
            throw new MarkerAlreadyExistingException("Marker already exists.");
        }
        return markerRepository.add(marker);
    }

    public Marker remove(int markerId) {
        checkExisting(markerId);
        int avertsCount = markerRepository.getAdvertsCount(markerId);
        if (avertsCount > 0) {
            throw new DeleteMarkerException("Adverts with this marker exist.");
        }
        return markerRepository.remove(markerId);
    }

    public Marker update(Marker marker) {
        checkExisting(marker.getId());
        return markerRepository.update(marker);
    }

    private void checkExisting(int id) {
        Marker existingMarker = markerRepository.getOne(id);
        if (existingMarker == null) {
            throw new MarkerNotFoundException("Marker is not found");
        }
    }

}

package main.java.service;


import main.java.dao.GeolocationRepository;
import main.java.entity.Country;
import main.java.entity.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeolocationService {

    @Autowired
    private GeolocationRepository geolocationRepository;

    public List<Country> getAllCountries(){
        return geolocationRepository.getAllCountries();
    }

    public List<Region> getRegions(Integer countryId){
        return geolocationRepository.getRegions(countryId);
    }
}

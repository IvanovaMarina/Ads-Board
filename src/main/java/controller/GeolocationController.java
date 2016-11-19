package main.java.controller;


import main.java.service.GeolocationService;
import main.java.view.CountryView;
import main.java.view.RegionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/geolocation")
public class GeolocationController {

    @Autowired
    private GeolocationService geolocationService;

    @RequestMapping(value = "/countries")
    public Resources<CountryView> getAllCountries(){

        List<CountryView> countryViewList = geolocationService.getAllCountries().stream()
                .map(country -> {
                    CountryView countryView = new CountryView(country);
                    countryView.add(ControllerLinkBuilder
                            .linkTo(ControllerLinkBuilder
                                    .methodOn(GeolocationController.class)
                                    .deleteCountry(new HttpHeaders(), country.getId()))
                            .withSelfRel());
                    return countryView;
                })
                .collect(Collectors.toList());
        Resources<CountryView> countryViewResources = new Resources<>(countryViewList);
        countryViewResources.add(ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(GeolocationController.class)
                        .getAllCountries())
                .withSelfRel());
        return countryViewResources;
    }

    /*@RequestMapping(value = "/countries/{id}", method = RequestMethod.DELETE)
    public void deleteCountry(@RequestHeader HttpHeaders httpHeaders, @PathVariable Integer id){
        System.out.println(httpHeaders.get(HttpHeaders.AUTHORIZATION));
    }*/
    @RequestMapping(value = "/countries/{id}", method = RequestMethod.DELETE)
    public CountryView deleteCountry(@RequestHeader HttpHeaders httpHeaders, @PathVariable Integer id){
        return null;
    }

    @RequestMapping(value = "/countries", method = RequestMethod.POST)
    public CountryView addCountry(@RequestHeader HttpHeaders httpHeaders, @PathVariable Integer id){
        return null;
    }

    @RequestMapping(value = "/countries/{countryId}/regions")
    public Resources<RegionView> getRegions(@PathVariable Integer countryId){
        List<RegionView> regions = geolocationService.getRegions(countryId).stream()
                .map(region->{
                    return new RegionView(region);
                }).collect(Collectors.toList());
        Resources<RegionView> regionViews = new Resources<>(regions);
        return regionViews;
    }
}

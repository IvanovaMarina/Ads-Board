package main.java.controller;


import main.java.entity.Region;
import main.java.service.GeolocationService;
import main.java.view.CountryView;
import main.java.view.RegionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/geolocation")
public class GeolocationController extends AbstractController {

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


    @RequestMapping(value = "/regions/{id}", method = RequestMethod.DELETE)
    public RegionView deleteRegion(@RequestHeader HttpHeaders httpHeaders, @PathVariable Integer id) {
        adminAuthorize(httpHeaders);
        RegionView regionView = new RegionView(geolocationService.deleteRegion(id));
        return regionView;
    }

    @RequestMapping(value = "/regions", method = RequestMethod.POST)
    public RegionView addRegion(@RequestHeader HttpHeaders httpHeaders, @RequestBody RegionView regionView) {
        adminAuthorize(httpHeaders);
        Region addedRegion = geolocationService.addRegion(regionView.toRegion());
        return new RegionView(addedRegion);
    }

    @RequestMapping(value = "/regions/{id}", method = RequestMethod.PUT)
    public RegionView updateRegion(@RequestHeader HttpHeaders httpHeaders,
                                   @PathVariable Integer id,
                                   @RequestBody RegionView regionView) {
        adminAuthorize(httpHeaders);

        regionView.setRegionId(id);

        return null;
    }

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
                    RegionView regionView = new RegionView(region);
                    Link link = ControllerLinkBuilder
                            .linkTo(ControllerLinkBuilder
                                    .methodOn(GeolocationController.class).deleteRegion(null, region.getId()))
                            .withSelfRel();
                    regionView.add(link);
                    return regionView;
                }).collect(Collectors.toList());
        Resources<RegionView> regionViews = new Resources<>(regions);
        Link addRegionLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(GeolocationController.class).addRegion(null, null))
                .withRel("linkToAdd");
        regionViews.add(addRegionLink);
        return regionViews;
    }
}

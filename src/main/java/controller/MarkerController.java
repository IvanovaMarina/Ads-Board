package main.java.controller;

import main.java.entity.Marker;
import main.java.service.MarkerService;
import main.java.view.MarkerView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/markers")
public class MarkerController extends AbstractController {

    @Autowired
    MarkerService markerService;

    @RequestMapping(method = RequestMethod.GET)
    Resources<MarkerView> getAllMarkers(){
        Link markersLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(MarkerController.class).getAllMarkers())
                .withSelfRel();

        List<MarkerView> markerViewList = markerService.getAllMarkers().stream()
                .map(marker -> {
                    MarkerView markerView = new MarkerView(marker);
                    Link markerLink = new Link(markersLink.getHref() + "/" + marker.getId(), "self");
                    markerView.add(markerLink);
                    return markerView;
                })
                .collect(Collectors.toList());
        Resources<MarkerView> markerViews = new Resources<>(markerViewList, markersLink);
        return markerViews;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public MarkerView removeMarker(@PathVariable Integer id, @RequestHeader HttpHeaders headers) {
        adminAuthorize(headers);
        Marker marker = markerService.remove(id);
        return new MarkerView(marker);
    }

    @RequestMapping(method = RequestMethod.POST)
    public MarkerView addMarker(@RequestBody MarkerView markerView, @RequestHeader HttpHeaders headers) {
        adminAuthorize(headers);
        Marker marker = markerService.add(markerView.toMarker());
        return new MarkerView(marker);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public MarkerView updateMarker(@PathVariable Integer id,
                                   @RequestBody MarkerView markerView,
                                   @RequestHeader HttpHeaders headers) {
        adminAuthorize(headers);
        markerView.setMarkerId(id);
        Marker marker = markerService.update(markerView.toMarker());
        return new MarkerView(marker);
    }
}

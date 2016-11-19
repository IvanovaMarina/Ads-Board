package main.java.controller;

import main.java.service.MarkerService;
import main.java.view.MarkerView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/markers")
public class MarkerController {

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
}

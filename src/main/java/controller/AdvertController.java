package main.java.controller;


import main.java.entity.Advert;
import main.java.service.AdvertService;
import main.java.view.AdvertView;
import main.java.view.TagView;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/adverts")
public class AdvertController extends AbstractController{

    @Autowired
    @Qualifier("advertService")
    private AdvertService advertService;

    @RequestMapping(value = "/{advertId}")
    public AdvertView getOneAdvert(@PathVariable Integer advertId){
        AdvertView advertView = new AdvertView(advertService.getOne(advertId));
        return advertView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public AdvertView addAdvert(@RequestBody AdvertView advertView, @RequestHeader HttpHeaders headers){
        authorize(headers);

        Advert newAdvert = advertService.add(advertView.toAdvert());

        String[] imageBase64parts = advertView.getImageBase64().split("base64,");
        if(imageBase64parts.length != 2){
            throw new WrongImageFormatException("Image must be in base64 format.");
        }
        byte[] imageBytes = Base64.decodeBase64(imageBase64parts[1]);
        advertService.saveImage(newAdvert.getId(), imageBytes);

        AdvertView responseAdvertView = new AdvertView(newAdvert);

        Link advertLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(AdvertController.class).getOneAdvert(newAdvert.getId()))
                .withSelfRel();
        Link imageLink = new Link(advertLink.getHref() + "/image", "image");
        responseAdvertView.add(advertLink);
        responseAdvertView.add(imageLink);

        return responseAdvertView;
    }

    @RequestMapping(params = {"page", "size"}, method = RequestMethod.GET)
    public Resources<AdvertView> getAdverts(@RequestParam("page") int page, @RequestParam("size") int size){
        List<AdvertView> advertViews = advertService.getAdverts(page, size).stream()
                .map(advert -> {
                    AdvertView advertView = new AdvertView(advert);
                    Link advertLink = ControllerLinkBuilder
                            .linkTo(ControllerLinkBuilder
                                    .methodOn(AdvertController.class).getOneAdvert(advert.getId()))
                            .withSelfRel();
                    Link imageLink = new Link(advertLink.getHref() + "/image", "image");
                    advertView.add(advertLink);
                    advertView.add(imageLink);
                    return advertView;
                })
                .collect(Collectors.toList());
        Link firstPageLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(AdvertController.class)
                        .getAdverts(1, size))
                .withRel("firstPage");
        Link curPageLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(AdvertController.class)
                        .getAdverts(page, size))
                .withRel("currentPage");
        Link lastPageLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(AdvertController.class)
                        .getAdverts(advertService.getLastPage(size), size))
                .withRel("lastPage");
        Resources<AdvertView> advertViewResources = new Resources<>(advertViews);
        advertViewResources.add(firstPageLink);
        advertViewResources.add(curPageLink);
        advertViewResources.add(lastPageLink);
        return advertViewResources;
    }

    @RequestMapping(value = "/{id}/image", method = RequestMethod.GET, produces = "image/jpg")
    public byte[] getAdvertImage(@PathVariable Integer id){
        return advertService.getImage(id);
    }

    public void setAdvertService(AdvertService advertService) {
        this.advertService = advertService;
    }
}

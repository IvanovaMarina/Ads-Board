package main.java.controller;


import main.java.entity.Advert;
import main.java.entity.User;
import main.java.service.AdvertNotFoundException;
import main.java.service.AdvertService;
import main.java.view.AdvertView;
import main.java.view.ListElementTagView;
import main.java.view.TagView;
import main.java.view.UserView;
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
        Link advertLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(AdvertController.class).getOneAdvert(advertId))
                .withSelfRel();
        Link incrementViewsLink = new Link(advertLink.getHref() + "/incrementViews", "incrementViews");
        Link imageLink = new Link(advertLink.getHref() + "/image", "image");
        UserView owner = advertView.getUserView();
        Link ownerLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(UserController.class).getOneUser(owner.getUserId()))
                .withSelfRel();
        Link ownerImageLink = new Link(ownerLink.getHref() + "/image", "image");
        owner.add(ownerImageLink);
        advertView.add(advertLink);
        advertView.add(imageLink);
        advertView.add(incrementViewsLink);
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
        Link incrementViewsLink = new Link(advertLink.getHref() + "/incrementViews", "incrementViews");
        responseAdvertView.add(advertLink);
        responseAdvertView.add(imageLink);
        responseAdvertView.add(incrementViewsLink);
        return responseAdvertView;
    }

    @RequestMapping(params = {"page", "size"}, method = RequestMethod.GET)
    public Resources<AdvertView> getAdverts(@RequestParam("page") Integer page, @RequestParam("size") Integer size){
        List<AdvertView> advertViews = advertService.getAdverts(page, size).stream()
                .map(advert -> convertAdvertToView(advert))
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

    @RequestMapping(params = {"page", "size", "tagName"}, method = RequestMethod.GET)
    public Resources<AdvertView> getAdvertsByTag(@RequestParam("page") Integer page,
                                            @RequestParam("size") Integer size,
                                            @RequestParam( "tagName") String tagName){
        List<AdvertView> advertViews = advertService.getAdvertsByTag(page, size, tagName).stream()
                .map(advert -> convertAdvertToView(advert))
                .collect(Collectors.toList());
        Link firstPageLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(AdvertController.class)
                        .getAdvertsByTag(1, size, tagName))
                .withRel("firstPage");
        Link curPageLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(AdvertController.class)
                        .getAdvertsByTag(page, size, tagName))
                .withRel("currentPage");
        Link lastPageLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(AdvertController.class)
                        .getAdvertsByTag(advertService.getTagLastPage(size, tagName), size, tagName))
                .withRel("lastPage");
        Resources<AdvertView> advertViewResources = new Resources<>(advertViews);
        advertViewResources.add(firstPageLink);
        advertViewResources.add(curPageLink);
        advertViewResources.add(lastPageLink);
        return advertViewResources;
    }

    @RequestMapping(params = {"page", "size", "categoryId"}, method = RequestMethod.GET)
    public Resources<AdvertView> getAdvertsByCategory(@RequestParam("page") Integer page,
                                                 @RequestParam("size") Integer size,
                                                 @RequestParam( "categoryId") Integer categoryId){
        List<AdvertView> advertViews = advertService.getAdvertsByCategory(page, size, categoryId).stream()
                .map(advert -> convertAdvertToView(advert))
                .collect(Collectors.toList());
        Link firstPageLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(AdvertController.class)
                        .getAdvertsByCategory(1, size, categoryId))
                .withRel("firstPage");
        Link curPageLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(AdvertController.class)
                        .getAdvertsByCategory(page, size, categoryId))
                .withRel("currentPage");
        Link lastPageLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(AdvertController.class)
                        .getAdvertsByCategory(advertService.getCategoryLastPage(size, categoryId), size, categoryId))
                .withRel("lastPage");
        Resources<AdvertView> advertViewResources = new Resources<>(advertViews);
        advertViewResources.add(firstPageLink);
        advertViewResources.add(curPageLink);
        advertViewResources.add(lastPageLink);
        return advertViewResources;
    }

    @RequestMapping(params = {"page", "size", "subcategoryId"}, method = RequestMethod.GET)
    public Resources<AdvertView> getAdvertsBySubcategory(@RequestParam("page") Integer page,
                                                      @RequestParam("size") Integer size,
                                                      @RequestParam( "subcategoryId") Integer subcategoryId){
        List<AdvertView> advertViews = advertService.getAdvertsBySubcategory(page, size, subcategoryId).stream()
                .map(advert -> convertAdvertToView(advert))
                .collect(Collectors.toList());
        Link firstPageLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(AdvertController.class)
                        .getAdvertsBySubcategory(1, size, subcategoryId))
                .withRel("firstPage");
        Link curPageLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(AdvertController.class)
                        .getAdvertsBySubcategory(page, size, subcategoryId))
                .withRel("currentPage");
        Link lastPageLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(AdvertController.class)
                        .getAdvertsBySubcategory(advertService.getSubcategoryLastPage(size, subcategoryId),
                                size, subcategoryId))
                .withRel("lastPage");
        Resources<AdvertView> advertViewResources = new Resources<>(advertViews);
        advertViewResources.add(firstPageLink);
        advertViewResources.add(curPageLink);
        advertViewResources.add(lastPageLink);
        return advertViewResources;
    }

    @RequestMapping(params = {"page", "size", "title"}, method = RequestMethod.GET)
    public Resources<AdvertView> getAdvertsByTitle(@RequestParam("page") Integer page,
                                                         @RequestParam("size") Integer size,
                                                         @RequestParam( "title") String title){
        List<AdvertView> advertViews = advertService.getAdvertsByTitle(page, size, title).stream()
                .map(advert -> convertAdvertToView(advert))
                .collect(Collectors.toList());
        Link firstPageLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(AdvertController.class)
                        .getAdvertsByTitle(1, size, title))
                .withRel("firstPage");
        Link curPageLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(AdvertController.class)
                        .getAdvertsByTitle(page, size, title))
                .withRel("currentPage");
        Link lastPageLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(AdvertController.class)
                        .getAdvertsByTitle(advertService.getTitleLastPage(size, title),
                                size, title))
                .withRel("lastPage");
        Resources<AdvertView> advertViewResources = new Resources<>(advertViews);
        advertViewResources.add(firstPageLink);
        advertViewResources.add(curPageLink);
        advertViewResources.add(lastPageLink);
        return advertViewResources;
    }

    @RequestMapping(value = "/{advertId}/incrementViews", method = RequestMethod.PUT)
    public void incrementAdvertViews(@PathVariable  Integer advertId){
        advertService.incrementAdvertViews(advertId);
    }

    @RequestMapping(value = "/{id}/image", method = RequestMethod.GET, produces = "image/jpg")
    public byte[] getAdvertImage(@PathVariable Integer id){
        return advertService.getImage(id);
    }

    @RequestMapping(value = "/{advertId}", method = RequestMethod.DELETE)
    public AdvertView deleteAdvert(@PathVariable Integer advertId, @RequestHeader HttpHeaders headers){
        User user = authorize(headers);
        Advert advert = advertService.getOne(advertId);
        if(advert == null){
            throw new AdvertNotFoundException("Advert not found.");
        }
        if(user.isAdmin() || user.getId() == advert.getOwner().getId()){
            return new AdvertView(advertService.remove(advertId));
        }
        else{
            throw new UnauthorizedUserException("Only admin and owner can delete this advert.");
        }
    }

    @RequestMapping(value = "/{advertId}", method = RequestMethod.PUT)
    public AdvertView updateAdvert(@PathVariable Integer advertId,
                                   @RequestBody AdvertView advertView,
                                   @RequestHeader HttpHeaders headers){
        authorize(headers);
        advertView.setAdvertId(advertId);
        Advert updatedAdvert = advertService.update(advertView.toAdvert());
        AdvertView updatedAdvertView = new AdvertView(updatedAdvert);

        return updatedAdvertView;
    }

    @RequestMapping(value = "/randomTags", params = {"amount"}, method = RequestMethod.GET)
    public List<TagView> getRandomTags(@RequestParam("amount") Integer amount){
        return advertService.getRandomTags(amount).stream()
                .map(tag -> {
                    TagView tagView = new ListElementTagView(tag);
                    Link advertsLink = ControllerLinkBuilder
                            .linkTo(ControllerLinkBuilder
                                    .methodOn(AdvertController.class).getAdvertsByTag(1, 2, tag.getName()))
                            .withRel("adverts");
                    tagView.add(advertsLink);
                    return tagView;
                }).collect(Collectors.toList());
    }

    @RequestMapping(value = "/mostAdvertsTags", method = RequestMethod.GET)
    public List<TagView> getTagsWithMostAdverts(@RequestHeader HttpHeaders headers) {
        adminAuthorize(headers);
        return advertService.getTagsWithMostAdverts().stream()
                .map(tag -> {
                    TagView tagView = new ListElementTagView(tag);
                    return tagView;
                }).collect(Collectors.toList());
    }

    public void setAdvertService(AdvertService advertService) {
        this.advertService = advertService;
    }

}

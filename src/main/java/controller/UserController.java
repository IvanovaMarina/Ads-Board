package main.java.controller;


import main.java.entity.User;
import main.java.service.UserNotFoundException;
import main.java.service.UserService;
import main.java.view.AdvertView;
import main.java.view.RequestUserView;
import main.java.view.ResponseUserView;
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
@RequestMapping(value = "/users")
public class UserController extends AbstractController{

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public UserView register(@RequestBody RequestUserView userView){
        User newUser = userService.add(userView.toUser());

        String[] imageBase64parts = userView.getImageBase64().split("base64,");
        if(imageBase64parts.length != 2){
            throw new WrongImageFormatException("Image must be in base64 format.");
        }
        byte[] imageBytes = Base64.decodeBase64(imageBase64parts[1]);
        userService.saveImage(newUser.getId(), imageBytes);

        UserView responseUserView = new ResponseUserView(newUser);

        Link userLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(UserController.class).getOneUser(newUser.getId()))
                .withSelfRel();

        Link imageLink = new Link(userLink.getHref() + "/image", "image");

        responseUserView.add(userLink);
        responseUserView.add(imageLink);

        return responseUserView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public UserView authenticate(@RequestHeader HttpHeaders headers){
        User user = authorize(headers);

        UserView userView = new ResponseUserView(user);
        Link userLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(UserController.class).getOneUser(user.getId()))
                .withSelfRel();

        Link imageLink = new Link(userLink.getHref() + "/image", "image");
        Link advertsLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(UserController.class).getAdverts(user.getId()))
                .withRel("adverts");
        userView.add(userLink);
        userView.add(imageLink);
        userView.add(advertsLink);
        return userView;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public UserView getOneUser(@PathVariable Integer id){
        return new UserView(userService.getUser(id));
    }

    @RequestMapping(value="/{id}/adverts", method = RequestMethod.GET)
    public Resources<AdvertView> getAdverts(@PathVariable Integer id){
        List<AdvertView> advertViews = userService.getAdvertsByUser(id).stream()
                .map(advert -> {
                    AdvertView advertView = new AdvertView(advert);
                    Link advertLink = ControllerLinkBuilder
                            .linkTo(ControllerLinkBuilder
                                    .methodOn(AdvertController.class).getOneAdvert(advert.getId()))
                            .withSelfRel();
                    Link imageLink = new Link(advertLink.getHref() + "/image", "image");
                    Link incrementViewsLink = new Link(advertLink.getHref() + "/incrementViews", "incrementViews");
                    advertView.add(advertLink);
                    advertView.add(imageLink);
                    advertView.add(incrementViewsLink);
                    return advertView;
                })
                .collect(Collectors.toList());
        Resources<AdvertView> advertViewResources = new Resources<>(advertViews);
        return advertViewResources;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public UserView updateUser(@PathVariable Integer id, @RequestBody RequestUserView userView){
        User user = userService.update(userView.toUser());
        ResponseUserView responseUserView = new ResponseUserView(user);
        return responseUserView;
    }
    @RequestMapping(value = "/{id}/image", method = RequestMethod.GET, produces = "image/jpg")
    public byte[] getUserImage(@PathVariable Integer id){
        return userService.getImage(id);
    }
}

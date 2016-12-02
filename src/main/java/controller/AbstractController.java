package main.java.controller;


import main.java.entity.Advert;
import main.java.entity.User;
import main.java.service.UserService;
import main.java.view.AdvertView;
import main.java.view.ResponseUserView;
import main.java.view.TagView;
import main.java.view.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class AbstractController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    protected User authorize(HttpHeaders headers)
    {
        List<String> authHeaderValues = headers.get(HttpHeaders.AUTHORIZATION);
        if(authHeaderValues == null){
            throw new UnauthorizedUserException("Header Authorization is not existing");
        }
        if(authHeaderValues.size() != 1){
            throw new UnauthorizedUserException("Wrong authentication data.");
        }
        String[] authData = authHeaderValues.get(0).split(":");
        if(authData.length < 2){
            throw new UnauthorizedUserException("Wrong authentication data.");
        }
        String username = authData[0].replaceFirst("Basic ", "");
        String password = authData[1];

        User user = userService.getUser(username, password);
        if(user == null){
            throw new UnauthorizedUserException("Wrong authentication data.");
        }
        return user;
    }

    protected User adminAuthorize(HttpHeaders headers){
        User user = authorize(headers);
        if(user.isAdmin() == false){
            throw new UnauthorizedUserException("Only admin is allowed to perform this action.");
        }
        return user;
    }

    protected AdvertView convertAdvertToView(Advert advert){
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

        for(TagView tagView : advertView.getTags()){
            Link tagAdvertsLink = ControllerLinkBuilder
                    .linkTo(ControllerLinkBuilder
                            .methodOn(AdvertController.class).getAdvertsByTag(1, 2, tagView.getName()))
                    .withRel("adverts");
            tagView.add(tagAdvertsLink);
        }
        return advertView;
    }

    protected UserView convertUserToView(User user){
        UserView responseUserView = new ResponseUserView(user);

        Link userLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(UserController.class).getOneUser(user.getId()))
                .withSelfRel();

        Link imageLink = new Link(userLink.getHref() + "/image", "image");
        Link advertsLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(UserController.class).getAdverts(user.getId()))
                .withRel("adverts");
        responseUserView.add(userLink);
        responseUserView.add(imageLink);
        responseUserView.add(advertsLink);
        return responseUserView;
    }
}

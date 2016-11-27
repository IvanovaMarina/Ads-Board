package main.java.controller;


import main.java.entity.User;
import main.java.service.UserNotFoundException;
import main.java.service.UserService;
import main.java.view.RequestUserView;
import main.java.view.ResponseUserView;
import main.java.view.UserView;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

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
        List<String> authHeaderValues = headers.get(HttpHeaders.AUTHORIZATION);
        if(authHeaderValues.size() != 1){
            throw new UserNotFoundException("Wrong authentication data.");
        }
        String[] authData = authHeaderValues.get(0).split(":");
        if(authData.length < 2){
            throw new UserNotFoundException("Wrong authentication data.");
        }
        String username = authData[0].replaceFirst("Basic ", "");
        String password = authData[1];

        User user = userService.getUser(username, password);
        if(user == null){
            throw new UserNotFoundException("Wrong authentication data.");
        }

        UserView userView = new ResponseUserView(user);
        Link userLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(UserController.class).getOneUser(user.getId()))
                .withSelfRel();

        Link imageLink = new Link(userLink.getHref() + "/image", "image");

        userView.add(userLink);
        userView.add(imageLink);
        return userView;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public UserView getOneUser(@PathVariable Integer id){
        return new UserView(userService.getUser(id));
    }

    @RequestMapping(value = "/{id}/image", method = RequestMethod.GET, produces = "image/jpg")
    public byte[] getUserImage(@PathVariable Integer id){
        return userService.getImage(id);
    }
}

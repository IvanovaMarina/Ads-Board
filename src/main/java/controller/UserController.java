package main.java.controller;


import main.java.entity.User;
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

        UserView responseUserView = convertUserToView(newUser);

        return responseUserView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public UserView authenticate(@RequestHeader HttpHeaders headers){
        User user = authorize(headers);

        UserView userView = convertUserToView(user);

        return userView;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public UserView getOneUser(@PathVariable Integer id){
        return new UserView(userService.getUser(id));
    }

    @RequestMapping(value="/{id}/adverts", method = RequestMethod.GET)
    public Resources<AdvertView> getAdverts(@PathVariable Integer id){
        List<AdvertView> advertViews = userService.getAdvertsByUser(id).stream()
                .map(advert -> convertAdvertToView(advert))
                .collect(Collectors.toList());
        Resources<AdvertView> advertViewResources = new Resources<>(advertViews);
        return advertViewResources;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public UserView updateUser(@PathVariable Integer id,
                               @RequestBody RequestUserView userView,
                               @RequestHeader HttpHeaders headers){
        authorize(headers);
        userView.setUserId(id);
        User user = userService.update(userView.toUser());
        UserView responseUserView = convertUserToView(user);
        return responseUserView;
    }
    @RequestMapping(value = "/{id}/image", method = RequestMethod.GET, produces = "image/jpg")
    public byte[] getUserImage(@PathVariable Integer id){
        return userService.getImage(id);
    }
}

package main.java.controller;


import main.java.entity.User;
import main.java.service.UserService;
import main.java.view.DetailedUserView;
import main.java.view.UserView;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    @Qualifier("userService")
    UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public UserView register(@RequestBody DetailedUserView userView){
        User newUser = userService.add(userView.toUser());

        userService.saveImage(newUser.getId(), userView.getImageBytesText().getBytes());

        UserView responseUserView = new UserView(newUser);
        responseUserView.add(ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(UserController.class).getUserImage(newUser.getId()))
                .withRel("image"));
        return responseUserView;
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

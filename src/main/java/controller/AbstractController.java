package main.java.controller;


import main.java.entity.User;
import main.java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Component
public abstract class AbstractController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    protected User authorize(@RequestHeader HttpHeaders headers)
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
}

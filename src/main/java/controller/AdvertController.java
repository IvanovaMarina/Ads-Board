package main.java.controller;


import main.java.service.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/adverts")
public class AdvertController {

    @Autowired
    @Qualifier("advertService")
    private AdvertService advertService;

    //TODO: добавление advert

    public void setAdvertService(AdvertService advertService) {
        this.advertService = advertService;
    }
}

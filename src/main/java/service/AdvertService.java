package main.java.service;


import main.java.entity.Advert;

public interface AdvertService {
    Advert add(Advert advert);
    Advert getOne(Integer id);
    void saveImage(Integer advertId, byte[] bytes);
    byte[] getImage(Integer advertId);
}

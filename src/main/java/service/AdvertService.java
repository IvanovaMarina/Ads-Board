package main.java.service;


import main.java.entity.Advert;

import java.util.List;

public interface AdvertService {
    Advert add(Advert advert);
    Advert getOne(Integer id);
    void saveImage(Integer advertId, byte[] bytes);
    byte[] getImage(Integer advertId);
    List<Advert> getAdverts(int page, int size);
    int getLastPage(int pageSize);
    void incrementAdvertViews(Integer id);
    Advert update(Advert advert);
}

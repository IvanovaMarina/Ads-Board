package main.java.service;


import main.java.entity.Advert;
import main.java.entity.Tag;

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
    List<Advert> getAdvertsByTag(int page, int size, String tagName);
    List<Advert> getAdvertsByCategory(int page, int size, Integer categoryId);
    List<Advert> getAdvertsBySubcategory(int page, int size, Integer subcategoryId);
    List<Advert> getAdvertsByName(int page, int size, String name);
    int getTagLastPage(int pageSize, String tagName);
    int getCategoryLastPage(int pageSize, Integer categoryId);
    int getSubcategoryLastPage(int pageSize, Integer subcategoryId);
    int getNameLastTagPage(int pageSize, String name);
    List<Tag> getRandomTags(int amount);
}

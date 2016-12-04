package main.java.dao;


import main.java.entity.Advert;

import java.util.List;

public interface AdvertRepository {
    Advert add(Advert advert);
    Advert update(Advert advert);
    Advert getOne(Integer id);
    Advert remove(Integer id);
    //TODO: при удалении advert удалять записи из adver_tag
    List<Advert> getAdverts(int limit, int offset);
    List<Advert> getAdvertsByUser(int userId);
    int count();
    void incrementAdvertViews(Integer id);
    List<Advert> getAdvertsByTag(int limit, int offset, String tagName);
    List<Advert> getAdvertsByCategory(int limit, int offset, Integer categoryId);
    List<Advert> getAdvertsBySubcategory(int limit, int offset, Integer subcategoryId);
    List<Advert> getAdvertsByTitle(int limit, int offset, String title);
    int countWithTag(String tagName);
    int countWithCategory(Integer categoryId);
    int countWithSubcategory(Integer subcategoryId);
    int countWithTitle(String title);
}

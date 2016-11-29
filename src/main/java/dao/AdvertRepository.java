package main.java.dao;


import main.java.entity.Advert;

import java.util.List;

public interface AdvertRepository {
    Advert add(Advert advert);
    Advert update(Advert advert);
    Advert getOne(Integer id);
    //TODO: при удалении advert удалять записи из adver_tag
    List<Advert> getAdverts(int limit, int offset);
    int count();
}

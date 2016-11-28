package main.java.dao;


import main.java.entity.Advert;

public interface AdvertRepository {
    Advert add(Advert advert);
    Advert update(Advert advert);
    Advert getOne(Integer id);
    //TODO: при удалении advert удалять записи из adver_tag
}

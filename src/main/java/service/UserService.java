package main.java.service;


import main.java.dao.UserRepository;
import main.java.entity.Advert;
import main.java.entity.User;

import java.util.List;

public interface UserService extends UserRepository{
    boolean validateToRegister(User user);
    void saveImage(Integer userId, byte[] bytes);
    byte[] getImage(Integer userId);
    List<Advert> getAdvertsByUser(int userId);
    User update(User user);
}

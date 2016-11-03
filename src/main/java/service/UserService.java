package main.java.service;


import main.java.dao.UserRepository;
import main.java.entity.User;

public interface UserService extends UserRepository{
    boolean validateToRegister(User user);
    boolean authorize(User user);
    boolean hasBasicLevelAccess(User user);
    boolean hasAdminLevelAccess(User user);
    void saveImage(Integer userId, byte[] bytes);
    byte[] getImage(Integer userId);
}

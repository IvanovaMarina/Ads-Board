package main.java.dao;


import main.java.entity.User;

import java.util.List;

public interface UserRepository {
    User add(User user);
    User removeUser(Integer id);
    User update(User user);
    List<User> getUsers(int page, int size);
    User getUser(Integer id);
    User getUser(String login, String password);
}

package main.java.service;


import main.java.dao.ImageUtil;
import main.java.dao.UserRepository;
import main.java.entity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService{

    private static String IMAGE_PATH = "C:/user_images/";

    @Autowired
    ImageUtil imageUtil;

    @Autowired
    @Qualifier("userRepository")
    UserRepository userRepository;

    @Override
    public User add(User user) {
        validateToRegister(user);
        avoidUserDuplication(user.getLogin(), user.getPassword());

        String encryptedLogin = encodeSHA1(user.getLogin());
        String encryptedPassword = encodeSHA1(user.getPassword());
        user.setLogin(encryptedLogin);
        user.setPassword(encryptedPassword);

        user.setRegistrationDate(LocalDateTime.now());

        userRepository.add(user);

        return user;
    }

    @Override
    public User removeUser(Integer id) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public List<User> getUsers(int page, int size) {
        return null;
    }

    @Override
    public User getUser(Integer id) {
        return userRepository.getUser(id);
    }

    @Override
    public User getUser(String login, String password) {
        String encryptedLogin = encodeSHA1(login);
        String encryptedPassword = encodeSHA1(password);
        return userRepository.getUser(encryptedLogin, encryptedPassword);
    }

    @Override
    public boolean validateToRegister(User user) {
        return true;
    }

    @Override
    public boolean authorize(User user) {
        return false;
    }

    @Override
    public boolean hasBasicLevelAccess(User user) {
        return false;
    }

    @Override
    public boolean hasAdminLevelAccess(User user) {
        return false;
    }

    @Override
    public void saveImage(Integer userId, byte[] imageBytes) {
        checkUserExisting(userId);
        User user = userRepository.getUser(userId);

        user.setPhotoPath(IMAGE_PATH + user.getId() + ".png");
        userRepository.update(user);

        try{
            imageUtil.savePicture(imageBytes, user.getPhotoPath());
        }catch(IOException ex){
            //TODO: обработать
            ex.printStackTrace();
        }
    }

    @Override
    public byte[] getImage(Integer userId) {
        checkUserExisting(userId);
        User user = userRepository.getUser(userId);
        byte[] imageBytes =  null;
        try {
            imageBytes = imageUtil.getImage(user.getPhotoPath());
        }catch(IOException ex){
            //TODO: обработать
            ex.printStackTrace();
        }
        return imageBytes;
    }

    private void checkUserExisting(Integer userId){
        User user = userRepository.getUser(userId);
        if(user == null){
            throw new UserNotFoundException();
        }
    }

    private void avoidUserDuplication(String login, String password){
        String encryptedLogin = encodeSHA1(login);
        String encryptedPassword = encodeSHA1(password);
        User user = userRepository.getUser(encryptedLogin, encryptedPassword);
        if(user != null){
            throw new UserAlreadyExistingException();
        }
    }

    private String encodeSHA1(String text){

            return new String(DigestUtils.sha1Hex(text));

    }
}

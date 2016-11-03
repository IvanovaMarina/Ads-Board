package main.java.entity;


import java.time.LocalDateTime;

public class User {

    private Integer id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private String photoPath;
    private LocalDateTime registrationDate;
    private boolean isAdmin;
    private String phone;
    private String email;
    private Region region;

    public User(Integer id, String name, String surname,
                String login, String password, String photoPath,
                LocalDateTime registrationDate, boolean isAdmin,
                String phone, String email, Region region) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.photoPath = photoPath;
        this.registrationDate = registrationDate;
        this.isAdmin = isAdmin;
        this.phone = phone;
        this.email = email;
        this.region = region;
    }

    public User(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", photoPath='" + photoPath + '\'' +
                ", registrationDate=" + registrationDate +
                ", isAdmin=" + isAdmin +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", region=" + region +
                '}';
    }
}

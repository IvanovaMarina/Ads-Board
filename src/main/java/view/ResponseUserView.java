package main.java.view;


import main.java.entity.User;

import java.time.LocalDateTime;

public class ResponseUserView extends UserView{

    private LocalDateTime registrationDate;
    private boolean isAdmin;
    private String regionName;
    private String countryName;

    public ResponseUserView(){}

    public ResponseUserView(User user){
        super(user);
        this.registrationDate = user.getRegistrationDate();
        this.isAdmin = user.isAdmin();
        this.regionName = user.getRegion().getName();
        this.countryName = user.getRegion().getCountry().getName();
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

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public User toUser(){
        User user =  super.toUser();
        user.setRegistrationDate(this.registrationDate);
        user.setAdmin(this.isAdmin);

        return user;
    }
}

package main.java.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.java.entity.Country;
import main.java.entity.Region;
import main.java.entity.User;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.time.LocalDateTime;

@Relation(collectionRelation = "users")
public class UserView extends ResourceSupport{
    @JsonProperty(value = "id")
    private Integer userId;
    private String name;
    private String surname;
    private LocalDateTime registrationDate;
    private String phone;
    private String email;
    @JsonProperty(value = "region")
    private Integer regionId;
    @JsonProperty(value = "image")
    private String imageBase64;
    private boolean isAdmin;


    public UserView(){}

    public UserView(User user){
        this.userId = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.registrationDate = user.getRegistrationDate();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.regionId = user.getRegion().getId();
        this.isAdmin = user.isAdmin();
    }

    public Integer getUserId(){
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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


    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
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

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public User toUser(){
        User user = new User();
        Region region = new Region();
        Country country  = new Country();
        region.setCountry(country);
        user.setRegion(region);

        user.setId(this.userId);
        user.setName(this.name);
        user.setSurname(this.surname);
        user.setRegistrationDate(this.registrationDate);
        user.setPhone(this.phone);
        user.setEmail(this.email);
        user.setAdmin(this.isAdmin);

        region.setId(this.regionId);

        return user;
    }

}

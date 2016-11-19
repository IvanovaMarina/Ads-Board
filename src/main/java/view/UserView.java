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

    private String phone;
    private String email;



    public UserView(){}

    public UserView(User user){
        this.userId = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.phone = user.getPhone();
        this.email = user.getEmail();
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

    public User toUser(){
        User user = new User();


        user.setId(this.userId);
        user.setName(this.name);
        user.setSurname(this.surname);
        user.setPhone(this.phone);
        user.setEmail(this.email);

        return user;
    }

}

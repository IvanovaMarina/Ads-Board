package main.java.view;


import com.fasterxml.jackson.annotation.JsonProperty;
import main.java.entity.Country;
import main.java.entity.Region;
import main.java.entity.User;

public class RequestUserView extends UserView{
    private String login;
    private String password;
    @JsonProperty(value = "image")
    private String imageBase64;
    @JsonProperty(value = "region")
    private Integer regionId;

    public RequestUserView(){}
    public RequestUserView(User user) {
        super(user);
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.regionId = user.getRegion().getId();
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



    @Override
    public User toUser() {
        User user =  super.toUser();

        Region region = new Region();
        Country country  = new Country();
        region.setCountry(country);
        user.setRegion(region);

        user.setLogin(this.getLogin());
        user.setPassword(this.getPassword());
        region.setId(this.regionId);
        return user;
    }

}

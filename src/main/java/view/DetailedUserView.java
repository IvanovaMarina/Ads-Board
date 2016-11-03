package main.java.view;


import main.java.entity.User;

public class DetailedUserView extends UserView{
    private String login;
    private String password;

    public DetailedUserView(){}
    public DetailedUserView(User user) {
        super(user);
        this.login = user.getLogin();
        this.password = user.getPassword();
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

    @Override
    public User toUser() {
        User user =  super.toUser();
        user.setLogin(this.getLogin());
        user.setPassword(this.getPassword());
        return user;
    }
}

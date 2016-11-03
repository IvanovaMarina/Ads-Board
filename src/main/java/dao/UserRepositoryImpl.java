package main.java.dao;


import main.java.entity.Country;
import main.java.entity.Region;
import main.java.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository{

    @Autowired
    private Connection connection;

    @Override
    public User add(User user) {
        //TODO:сделать проверку на наличие такого логина
        try{
            final String query =
                    "insert into user(name, surname, password, login, path_to_photo, " +
                            "register_date, is_admin, phone, email, id_region)\n" +
                    "value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getLogin());
            statement.setString(5, user.getPhotoPath());
            Long registerDate = user.getRegistrationDate().atZone(ZoneId.systemDefault()).toEpochSecond();
            statement.setLong(6, registerDate);
            statement.setBoolean(7, user.isAdmin());
            statement.setString(8, user.getPhone());
            statement.setString(9, user.getEmail());
            statement.setInt(10, user.getRegion().getId());

            statement.execute();

            Statement selectIdstatement = connection.createStatement();
            String selectIdquery = "SELECT id_user FROM user " +
                    "WHERE login = \'" + user.getLogin() +"\'";
            ResultSet userIdResultSet = selectIdstatement.executeQuery(selectIdquery);
            userIdResultSet.next();
            user.setId(userIdResultSet.getInt("id_user"));
        }catch(SQLException ex){
            //TODO: handle
            System.out.println(ex.getMessage());
            System.out.println(ex.getErrorCode());
            System.out.println(ex.getLocalizedMessage());
        }
        return user;
    }

    @Override
    public User removeUser(Integer id) {
        return null;
    }

    /**
     * @param user must contain all public information
     * @return updated user
     * updates only public information
     */
    @Override
    public User update(User user) {
        try{
            final String query =
                    "UPDATE user\n" +
                    "SET name = ?, surname = ?, path_to_photo = ?, \n" +
                    "phone = ?, email = ?, id_region = ?\n" +
                    "WHERE id_user = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getPhotoPath());
            statement.setString(4, user.getPhone());
            statement.setString(5, user.getEmail());
            statement.setInt(6, user.getRegion().getId());
            statement.setInt(7, user.getId());

            statement.execute();
        }catch(SQLException ex){
            //TODO: обработать
        }
        return user;
    }

    @Override
    public List<User> getUsers(int page, int size) {
        return null;
    }

    @Override
    public User getUser(Integer id) {
        try{
            final String query =
                    "SELECT user.id_user AS id, user.name AS name, user.surname AS surname, \n" +
                    "\tuser.password AS password, user.login AS login,\n" +
                    "    user.path_to_photo AS path_to_photo, user.register_date AS register_date,\n" +
                    "    user.is_admin AS is_admin, user.phone AS phone, \n" +
                    "    user.email AS email,region.id_region AS id_region, \n" +
                    "    region.name AS region,country.id_country AS id_country, \n" +
                    "    country.name AS country\n" +
                    "FROM user\n" +
                    "INNER JOIN region\n" +
                    "INNER JOIN country\n" +
                    "ON user.id_region = region.id_region\n" +
                    "    AND region.id_country = country.id_country\n" +
                    "WHERE user.id_user = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();


            while(resultSet.next()){
                User user = new User();
                Region region = new Region();
                Country country = new Country();

                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setPhotoPath(resultSet.getString("path_to_photo"));
                Long registrationTimestamp = resultSet.getLong("register_date");
                user.setRegistrationDate(LocalDateTime
                        .ofInstant(Instant.ofEpochSecond(registrationTimestamp), ZoneId.systemDefault()));
                user.setAdmin(resultSet.getBoolean("is_admin"));
                user.setPhone(resultSet.getString("phone"));
                user.setEmail(resultSet.getString("email"));
                region.setId(resultSet.getInt("id_region"));
                region.setName(resultSet.getString("region"));
                country.setId(resultSet.getInt("id_country"));
                country.setName(resultSet.getString("country"));

                region.setCountry(country);
                user.setRegion(region);
                return user;
            }
        }catch(SQLException ex){
            //TODO: обработать
            System.out.println(ex.getMessage());
            System.out.println(ex.getErrorCode());
            System.out.println(ex.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public User getUser(String login, String password) {
        try{
            final String query =
                    "SELECT user.id_user AS id, user.name AS name, user.surname AS surname, \n" +
                            "\tuser.password AS password, user.login AS login,\n" +
                            "    user.path_to_photo AS path_to_photo, user.register_date AS register_date,\n" +
                            "    user.is_admin AS is_admin, user.phone AS phone, \n" +
                            "    user.email AS email,region.id_region AS id_region, \n" +
                            "    region.name AS region,country.id_country AS id_country, \n" +
                            "    country.name AS country\n" +
                            "FROM user\n" +
                            "INNER JOIN region\n" +
                            "INNER JOIN country\n" +
                            "ON user.id_region = region.id_region\n" +
                            "    AND region.id_country = country.id_country\n" +
                            "WHERE user.login = ? AND user.password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();


            while(resultSet.next()){
                User user = new User();
                Region region = new Region();
                Country country = new Country();

                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setPhotoPath(resultSet.getString("path_to_photo"));
                Long registrationTimestamp = resultSet.getLong("register_date");
                user.setRegistrationDate(LocalDateTime
                        .ofInstant(Instant.ofEpochSecond(registrationTimestamp), ZoneId.systemDefault()));
                user.setAdmin(resultSet.getBoolean("is_admin"));
                user.setPhone(resultSet.getString("phone"));
                user.setEmail(resultSet.getString("email"));
                region.setId(resultSet.getInt("id_region"));
                region.setName(resultSet.getString("region"));
                country.setId(resultSet.getInt("id_country"));
                country.setName(resultSet.getString("country"));

                region.setCountry(country);
                user.setRegion(region);
                return user;
            }
        }catch(SQLException ex){
            //TODO: обработать
            System.out.println(ex.getMessage());
            System.out.println(ex.getErrorCode());
            System.out.println(ex.getLocalizedMessage());
        }
        return null;
    }

}

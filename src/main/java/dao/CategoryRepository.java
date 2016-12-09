package main.java.dao;


import main.java.entity.Category;
import main.java.entity.Subcategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryRepository {

    @Autowired
    private Connection connection;

    public List<Category> getAllCategories(){
        List<Category> categories = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT id_cat, name FROM category");
            while(resultSet.next()){
                Category category = new Category();
                category.setId(resultSet.getInt("id_cat"));
                category.setName(resultSet.getString("name"));
                categories.add(category);
            }
        }catch (SQLException exception){
            exception.printStackTrace();
            throw new DBAccessException(exception.getMessage());
        }
        return categories;
    }

    public List<Subcategory> getSubcategories(Integer categoryId){
        List<Subcategory> subcategories = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT id_scat, name FROM subcategory " + "WHERE id_cat = ?");
            statement.setInt(1, categoryId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Subcategory subcategory = new Subcategory();
                subcategory.setId(resultSet.getInt("id_scat"));
                subcategory.setName(resultSet.getString("name"));
                subcategories.add(subcategory);
            }
        }catch(SQLException exception){
            exception.printStackTrace();
            throw new DBAccessException(exception.getMessage());
        }
        return subcategories;
    }

    public List<Subcategory> getSubcategoriesWithMostAdverts() {
        List<Subcategory> subcategories = new ArrayList<>();
        String query = "SELECT subcategory.id_scat AS id_scat, \n" +
                "\tsubcategory.name AS name,\n" +
                "    COUNT(id_ad) AS count\n" +
                "FROM subcategory\n" +
                "LEFT JOIN advert\n" +
                "ON subcategory.id_scat = advert.id_scat\n" +
                "GROUP BY subcategory.name\n" +
                "HAVING count >= ALL(\n" +
                "    SELECT COUNT(id_ad) \n" +
                "    FROM subcategory\n" +
                "\tLEFT JOIN advert\n" +
                "\tON subcategory.id_scat = advert.id_scat\n" +
                "\tGROUP BY subcategory.name\n" +
                ")";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Subcategory subcategory = new Subcategory();
                subcategory.setId(resultSet.getInt("id_scat"));
                subcategory.setName(resultSet.getString("name"));
                subcategories.add(subcategory);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new DBAccessException(exception.getMessage());
        }
        return subcategories;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}

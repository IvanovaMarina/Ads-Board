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
}

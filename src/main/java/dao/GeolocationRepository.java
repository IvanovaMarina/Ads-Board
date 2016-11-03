package main.java.dao;


import main.java.entity.Country;
import main.java.entity.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GeolocationRepository {
    @Autowired
    private Connection connection;

    public List<Country> getAllCountries(){
        List<Country> countries = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT id_country, name FROM country");
            while(resultSet.next()){
                Country country = new Country();
                country.setId(resultSet.getInt("id_country"));
                country.setName(resultSet.getString("name"));
                countries.add(country);
            }
        }catch (SQLException exception){
            //TODO: handle SQLException in GeolocationRepository
        }
        return countries;
    }

    public List<Region> getRegions(Integer id){
        List<Region> regions = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT id_region, name FROM region " +
                    "WHERE id_country = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Region region = new Region();
                region.setId(resultSet.getInt("id_region"));
                region.setName(resultSet.getString("name"));
                regions.add(region);
            }
        }catch(SQLException ex){
            //TODO: handle SQLException in GeolocationRepository
        }
        return regions;
    }
}

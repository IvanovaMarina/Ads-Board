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
            exception.printStackTrace();
            throw new DBAccessException(exception.getMessage());
        }
        return countries;
    }

    public List<Region> getRegions(Integer countryId){
        List<Region> regions = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT id_region, name FROM region " +
                    "WHERE id_country = ?");
            statement.setInt(1, countryId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Region region = new Region();
                region.setId(resultSet.getInt("id_region"));
                region.setName(resultSet.getString("name"));
                regions.add(region);
            }
        }catch(SQLException exception){
            exception.printStackTrace();
            throw new DBAccessException(exception.getMessage());
        }
        return regions;
    }

    public Region getOneRegion(Integer id){
        Region region = new Region();
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT id_region, name FROM region " +
                            "WHERE id_region = ?\n" +
                            "LIMIT 1");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            region.setId(resultSet.getInt("id_region"));
            region.setName(resultSet.getString("name"));
        }catch(SQLException exception){
            exception.printStackTrace();
            throw new DBAccessException(exception.getMessage());
        }
        return region;
    }

    public Region getOneRegion(String name) {
        Region region = new Region();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT id_region, name FROM region " +
                            "WHERE name = ?\n" +
                            "LIMIT 1");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            region.setId(resultSet.getInt("id_region"));
            region.setName(resultSet.getString("name"));
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new DBAccessException(exception.getMessage());
        }
        return region;
    }

    public Region deleteRegion(Integer id) {
        Region region = getOneRegion(id);
        String query = "DELETE FROM region\n" +
                "WHERE id_region = ?";
        if (region != null) {
            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, id);
                statement.execute();
            } catch (SQLException exception) {
                exception.printStackTrace();
                throw new DBAccessException(exception.getMessage());
            }
        }
        return region;
    }

    public Region addRegion(Region region) {
        String insertQuery =
                "INSERT INTO region(name, id_country)\n" +
                        "VALUE(?, ?)";
        String selectIdQuery =
                "SELECT id_region FROM region\n" +
                        "WHERE name = ?";
        try {
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, region.getName());
            insertStatement.setInt(2, region.getCountry().getId());
            insertStatement.execute();

            PreparedStatement selectIdStatement = connection.prepareStatement(selectIdQuery);
            selectIdStatement.setString(1, region.getName());
            ResultSet idResultSet = selectIdStatement.executeQuery();
            idResultSet.next();
            return getOneRegion(idResultSet.getInt("id_region"));
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new DBAccessException(exception.getMessage());
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}

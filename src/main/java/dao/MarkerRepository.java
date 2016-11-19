package main.java.dao;


import main.java.entity.Currency;
import main.java.entity.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MarkerRepository {

    @Autowired
    private Connection connection;

    public List<Marker> getAllMarkers(){
        List<Marker> markers = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT id_m, name FROM marker");
            while(resultSet.next()){
                Marker marker = new Marker();
                marker.setId(resultSet.getInt("id_m"));
                marker.setName(resultSet.getString("name"));
                markers.add(marker);
            }
        }catch (SQLException exception){
            exception.printStackTrace();
            throw new DBAccessException(exception.getMessage());
        }
        return markers;
    }
}

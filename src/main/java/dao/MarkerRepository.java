package main.java.dao;


import main.java.entity.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
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

    public Marker add(Marker marker) {
        String insertQuery =
                "INSERT INTO marker(name)\n" +
                        "VALUE(?)";
        String selectIdQuery =
                "SELECT id_m FROM marker\n" +
                        "WHERE name = ?";
        try {
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, marker.getName());
            insertStatement.execute();

            PreparedStatement selectIdStatement = connection.prepareStatement(selectIdQuery);
            selectIdStatement.setString(1, marker.getName());
            ResultSet idResultSet = selectIdStatement.executeQuery();
            idResultSet.next();
            return getOne(idResultSet.getInt("id_m"));
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new DBAccessException(exception.getMessage());
        }
    }

    public Marker remove(int markerId) {
        Marker marker = getOne(markerId);
        String query = "DELETE FROM marker\n" +
                "WHERE id_m = ?";
        if (marker != null) {
            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, markerId);
                statement.execute();
            } catch (SQLException exception) {
                exception.printStackTrace();
                throw new DBAccessException(exception.getMessage());
            }
        }
        return marker;
    }

    public Marker update(Marker marker) {
        Marker existingMarker = getOne(marker.getId());
        if (existingMarker == null) {
            return null;
        }
        String query =
                "UPDATE marker\n" +
                        "SET name = ?\n" +
                        "WHERE id_m = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, marker.getName());
            statement.setInt(2, marker.getId());
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new DBAccessException(exception.getMessage());
        }
        return marker;
    }

    public Marker getOne(int markerId) {
        Marker marker = new Marker();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT id_m, name FROM marker\n" +
                            "WHERE id_m = ?\n" +
                            "LIMIT 1");
            statement.setInt(1, markerId);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            marker.setId(resultSet.getInt("id_m"));
            marker.setName(resultSet.getString("name"));
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new DBAccessException(exception.getMessage());
        }
        return marker;
    }

    public Marker getOne(String name) {
        Marker marker = new Marker();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT id_m, name FROM marker\n" +
                            "WHERE name = ?\n" +
                            "LIMIT 1");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            marker.setId(resultSet.getInt("id_m"));
            marker.setName(resultSet.getString("name"));
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new DBAccessException(exception.getMessage());
        }
        return marker;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public int getAdvertsCount(Integer markerId) {
        String query =
                "SELECT COUNT(advert.id_ad) AS count FROM marker\n" +
                        "LEFT JOIN advert\n" +
                        "\tON advert.id_m = marker.id_m\n" +
                        "WHERE marker.id_m = ?\n" +
                        "GROUP BY marker.name";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, markerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return 0;
            }
            return resultSet.getInt("count");
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new DBAccessException(exception.getMessage());
        }
    }
}

package main.java.dao;

import main.java.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class TagRepository {

    @Autowired
    private Connection connection;

    public Tag getTag(String name){
        Tag tag = null;
        try {
            String selectTagQuery =
                    "SELECT tag.id_tag AS id_tag, tag.name AS name, count(advert_tag.id_tag) AS amount \n" +
                            "FROM tag\n" +
                            "LEFT JOIN advert_tag\n" +
                            "\tON tag.id_tag = advert_tag.id_tag\n" +
                            "WHERE name = ?\n" +
                            "GROUP BY(id_tag)\n" +
                            "LIMIT 1;";
            PreparedStatement statement = connection.prepareStatement(selectTagQuery);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                tag = new Tag(resultSet.getInt("id_tag"),
                        resultSet.getString("name"),
                        resultSet.getInt("amount"));
            }

        }catch(SQLException exception){
            exception.printStackTrace();
            throw new DBAccessException(exception.getMessage());
        }
        return tag;
    }

    public void addTag(Tag tag){
        try {
            String query = "insert into tag(name) values(?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, tag.getName());
            statement.execute();
        }catch(SQLException exception){
            exception.printStackTrace();
            throw new DBAccessException(exception.getMessage());
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}

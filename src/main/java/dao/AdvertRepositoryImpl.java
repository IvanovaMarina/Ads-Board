package main.java.dao;


import main.java.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Repository("advertRepository")
public class AdvertRepositoryImpl implements AdvertRepository{

    @Autowired
    private Connection connection;

    @Override
    public Advert add(Advert advert) {
        Advert addedAdvert = null;
        try{
            final String addProcedureQuery = "CALL add_advert(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(addProcedureQuery);
            statement.setString(1, advert.getTitle());
            statement.setString(2, advert.getDescription());
            statement.setString(3, advert.getPhotoPath());
            Long addTime = advert.getAddTime().atZone(ZoneId.systemDefault()).toEpochSecond();
            statement.setLong(4, addTime);
            statement.setInt(5, advert.getOwner().getId());
            statement.setInt(6, advert.getSubcategory().getId());
            statement.setInt(7, advert.getMarker().getId());
            statement.setDouble(8, advert.getPrice());
            statement.setInt(9, advert.getCurrency().getId());

            statement.execute();

            Statement selectIdStatement = connection.createStatement();
            String selectIdQuery =
                    "SELECT id_ad FROM advert\n" +
                            "WHERE title = \'" + advert.getTitle() + "\' AND add_time = " + addTime;
            ResultSet advertIdResultSet = selectIdStatement.executeQuery(selectIdQuery);
            advertIdResultSet.next();
            //advert.setId(advertIdResultSet.getInt("id_ad"));
            Integer addedAdvertId = advertIdResultSet.getInt("id_ad");

            final String tagBindingQuery = "insert into advert_tag(id_ad, id_tag) values(?, ?)";
            PreparedStatement tagBindingStatement = connection.prepareStatement(tagBindingQuery);
            for(Tag tag: advert.getTags()){
                tagBindingStatement.setInt(1, addedAdvertId);
                tagBindingStatement.setInt(2, tag.getId());
                tagBindingStatement.execute();
                tagBindingStatement.clearParameters();
            }

            addedAdvert = getOne(addedAdvertId);

        }catch(SQLException exception){
            exception.printStackTrace();
            throw new DBAccessException(exception.getMessage());
        }
        //return advert;
        return addedAdvert;
    }

    @Override
    public Advert update(Advert advert) {
        try{
            final String updateAdvertQuery =
                    "UPDATE advert\n" +
                            "SET title = ?, description = ?, id_scat = ?, path_to_photo = ?, id_m = ?\n" +
                            "WHERE id_ad = ?;";
            final String updatePriceQuery =
                    "UPDATE price\n" +
                            "SET amount = ?, id_cur = ?\n" +
                            "WHERE id_p = ?;";
            final String deleteTagsQuery =
                    "DELETE FROM advert_tag\n" +
                            "WHERE id_ad = ?;";
            final String insertTagQuery =
                    "INSERT INTO advert_tag(id_ad, id_tag)\n" +
                            "VALUES(?, ?);";

            PreparedStatement updateAdvertStatement = connection.prepareStatement(updateAdvertQuery);
            updateAdvertStatement.setString(1, advert.getTitle());
            updateAdvertStatement.setString(2, advert.getDescription());
            updateAdvertStatement.setInt(3, advert.getSubcategory().getId());
            updateAdvertStatement.setString(4, advert.getPhotoPath());
            updateAdvertStatement.setInt(5, advert.getMarker().getId());
            updateAdvertStatement.setInt(6, advert.getId());
            updateAdvertStatement.execute();

            PreparedStatement updatePriceStatement = connection.prepareStatement(updatePriceQuery);
            updatePriceStatement.setDouble(1, advert.getPrice());
            updatePriceStatement.setInt(2, advert.getCurrency().getId());
            updatePriceStatement.setInt(3, advert.getId());
            updatePriceStatement.execute();

            PreparedStatement deleteTagsStatement = connection.prepareStatement(deleteTagsQuery);
            deleteTagsStatement.setInt(1, advert.getId());
            deleteTagsStatement.execute();

            PreparedStatement tagBindingStatement = connection.prepareStatement(insertTagQuery);
            for(Tag tag: advert.getTags()){
                tagBindingStatement.setInt(1, advert.getId());
                tagBindingStatement.setInt(2, tag.getId());
                tagBindingStatement.execute();
                tagBindingStatement.clearParameters();
            }

        }catch(SQLException exception){
            exception.printStackTrace();
            throw new DBAccessException(exception.getMessage());
        }
        return advert;
    }

    @Override
    public Advert getOne(Integer id) {
        Advert advert = new Advert();
        User owner = new User();
        Subcategory subcategory = new Subcategory();
        Category category = new Category();
        Region region = new Region();
        Country country = new Country();
        Marker marker = new Marker();
        List<Tag> tags = new ArrayList<>();
        Currency currency = new Currency();

        advert.setOwner(owner);
        subcategory.setCategory(category);
        advert.setSubcategory(subcategory);
        region.setCountry(country);
        owner.setRegion(region);
        advert.setMarker(marker);
        advert.setTags(tags);
        advert.setCurrency(currency);
        try {
            String query =
                    "SELECT id_ad, title, description, advert.path_to_photo AS photo_path,\n" +
                            " add_time, views, user.id_user AS id_user, \n" +
                            " user.name AS user_name, user.surname AS user_surname,\n" +
                            " user.phone AS phone, user.email AS email,\n" +
                            " region.id_region AS id_region, region.name AS region_name,\n" +
                            " country.id_country AS id_country, country.name AS country_name,\n" +
                            " subcategory.id_scat AS id_scat, subcategory.name AS scat_name,\n" +
                            " category.id_cat AS id_cat, category.name AS cat_name,\n" +
                            " price.amount AS price, currency.id_cur AS id_cur,\n" +
                            " currency.abbreviation AS cur_abbrev, marker.id_m AS id_m,\n" +
                            " marker.name AS marker_name\n" +
                            "FROM advert\n" +
                            "INNER JOIN user\n" +
                            "INNER JOIN region\n" +
                            "INNER JOIN country\n" +
                            "INNER JOIN subcategory\n" +
                            "INNER JOIN category\n" +
                            "INNER JOIN price\n" +
                            "INNER JOIN currency\n" +
                            "INNER JOIN marker\n" +
                            "ON advert.id_user = user.id_user AND  user.id_region = region.id_region \n" +
                            "\tAND region.id_country = country.id_country\n" +
                            "    AND advert.id_scat = subcategory.id_scat\n" +
                            "    AND subcategory.id_cat = category.id_cat\n" +
                            "    AND advert.id_p = price.id_p\n" +
                            "    AND price.id_cur = currency.id_cur\n" +
                            "    AND advert.id_m = marker.id_m\n" +
                            "WHERE id_ad = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                advert.setId(resultSet.getInt("id_ad"));
                advert.setTitle(resultSet.getString("title"));
                advert.setDescription(resultSet.getString("description"));
                advert.setPhotoPath(resultSet.getString("photo_path"));
                Long addTime = resultSet.getLong("add_time");
                advert.setAddTime(LocalDateTime
                        .ofInstant(Instant.ofEpochSecond(addTime), ZoneId.systemDefault()));
                advert.setViews(resultSet.getInt("views"));

                owner.setId(resultSet.getInt("id_user"));
                owner.setName(resultSet.getString("user_name"));
                owner.setSurname(resultSet.getString("user_surname"));
                owner.setPhone(resultSet.getString("phone"));
                owner.setEmail(resultSet.getString("email"));

                region.setId(resultSet.getInt("id_region"));
                region.setName(resultSet.getString("region_name"));
                country.setId(resultSet.getInt("id_country"));
                country.setName(resultSet.getString("country_name"));

                subcategory.setId(resultSet.getInt("id_scat"));
                subcategory.setName(resultSet.getString("scat_name"));
                category.setId(resultSet.getInt("id_cat"));
                category.setName(resultSet.getString("cat_name"));

                advert.setPrice(resultSet.getDouble("price"));

                currency.setId(resultSet.getInt("id_cur"));
                currency.setAbbreviation(resultSet.getString("cur_abbrev"));

                marker.setId(resultSet.getInt("id_m"));
                marker.setName(resultSet.getString("marker_name"));
            }
            else return null;

            String selectTagsQuery =
                    "SELECT tag.id_tag AS id_tag, tag.name AS tag_name\n" +
                            "FROM advert_tag\n" +
                            "INNER JOIN tag\n" +
                            "\tON advert_tag.id_tag = tag.id_tag\n" +
                            "WHERE advert_tag.id_ad = ?";
            PreparedStatement selectTagsStatement = connection.prepareStatement(selectTagsQuery);
            selectTagsStatement.setInt(1, advert.getId());
            ResultSet tagsResultSet = selectTagsStatement.executeQuery();
            while(tagsResultSet.next()){
                Tag tag = new Tag();
                tag.setId(tagsResultSet.getInt("id_tag"));
                tag.setName(tagsResultSet.getString("tag_name"));
                tags.add(tag);
            }
        }catch(SQLException exception){
            exception.printStackTrace();
            throw new DBAccessException(exception.getMessage());
        }
        return advert;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}

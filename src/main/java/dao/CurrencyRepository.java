package main.java.dao;

import main.java.entity.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CurrencyRepository {

    @Autowired
    private Connection connection;

    public List<Currency> getAllCurrencies(){
        List<Currency> currencies = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT id_cur, abbreviation FROM currency");
            while(resultSet.next()){
                Currency currency = new Currency();
                currency.setId(resultSet.getInt("id_cur"));
                currency.setAbbreviation(resultSet.getString("abbreviation"));
                currencies.add(currency);
            }
        }catch (SQLException exception){
            exception.printStackTrace();
            throw new DBAccessException(exception.getMessage());
        }
        return currencies;
    }
}

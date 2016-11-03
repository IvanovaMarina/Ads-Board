package main.java.dao;


import org.springframework.context.annotation.Bean;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class ConnectionManager {

    private static final String configurationPath = "C:/configuration.txt";
    private static Connection connection;

    @Bean
    public Connection getConnection(){
        if(connection == null){
            try{
                connection = createConnection();
            }catch (SQLException ex){
                throw new ConfigurationException("Can not get connection. " + ex.getMessage());
            }
        }
        return connection;
    }

    private Connection createConnection() throws SQLException{
        Configuration configuration = parseConfigurationFile();
        return DriverManager.getConnection(configuration.getUrl(),
                configuration.getLogin(),
                configuration.getPassword());
    }

    private Configuration parseConfigurationFile() {
        Map<String,String> configurations = new HashMap<>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(configurationPath)))){
            String row;
            while((row = bufferedReader.readLine()) != null){
                String[] propertie = row.split(" ");
                if(propertie.length != 2){
                    continue;
                }
                configurations.put(propertie[0],propertie[1]);
            }
        }catch (FileNotFoundException ex){
            throw new ConfigurationException(ex.getMessage());
        }catch (IOException ex){
            throw new ConfigurationException("It is impossible to parse file. Try later.");
        }
        String login = configurations.get("login");
        if(login == null){
            throw new ConfigurationException("Login absents in DB configuration file");
        }
        String password = configurations.get("password");
        if(password == null){
            throw new ConfigurationException("Password absents in DB configuration file");
        }
        String url = configurations.get("url");
        if(url == null){
            throw new ConfigurationException("URL absents in DB configuration file");
        }
        return new Configuration(login, password, url);
    }

    private static class Configuration {
        private String login;
        private String password;
        private String url;

        public Configuration(String login, String password, String url) {
            this.login = login;
            this.password = password;
            this.url = url;
        }

        public Configuration(){}

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}

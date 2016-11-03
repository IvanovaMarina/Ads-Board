package main.java;

import main.java.dao.ConnectionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Connection;

@SpringBootApplication
public class AdvertsApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdvertsApplication.class, args);
    }

    @Bean
    public Connection connection(){
        return new ConnectionManager().getConnection();
    }
}

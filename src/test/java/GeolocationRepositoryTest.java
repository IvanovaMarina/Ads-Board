package test.java;


import main.java.dao.ConnectionManager;
import main.java.dao.GeolocationRepository;
import main.java.entity.Country;
import main.java.entity.Region;

public class GeolocationRepositoryTest {
    private static GeolocationRepository geolocationRepository = new GeolocationRepository();

    public static void main(String[] args) {
        geolocationRepository.setConnection(new ConnectionManager().getConnection());

        Region region = new Region();
        Country country = new Country();
        region.setCountry(country);

        region.setName("Asd");
        country.setId(1);

        System.out.println(geolocationRepository.addRegion(region));

    }
}

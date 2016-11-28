package test.java;


import main.java.dao.AdvertRepositoryImpl;
import main.java.dao.ConnectionManager;

public class AdvertRepositoryTest {
    public static void main(String[] args) {
        AdvertRepositoryImpl advertRepository = new AdvertRepositoryImpl();
        advertRepository.setConnection(new ConnectionManager().getConnection());

        /*Advert advert = new Advert();

        User user = new User(); user.setId(1);
        Subcategory subcategory = new Subcategory(); subcategory.setId(1);
        Marker marker = new Marker(); marker.setId(1);
        Currency currency = new Currency(); currency.setId(1);
        List<Tag> tags = new ArrayList<>(); tags.add(new Tag(1, "", 0)); tags.add(new Tag(2, "", 0));

        advert.setTitle("Second ad");
        advert.setDescription("My description");
        advert.setPhotoPath("random_path");
        advert.setAddTime(LocalDateTime.now());
        advert.setOwner(user);
        advert.setSubcategory(subcategory);
        advert.setMarker(marker);
        advert.setPrice(25.56);
        advert.setCurrency(currency);
        advert.setTags(tags);

        Advert advertResult = advertRepository.add(advert);
        System.out.println(advertResult.getId());*/
        System.out.println(advertRepository.getOne(4));
    }
}

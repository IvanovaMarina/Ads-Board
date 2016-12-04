package test.java;


import main.java.dao.AdvertRepositoryImpl;
import main.java.dao.ConnectionManager;

public class AdvertRepositoryTest {

    private static AdvertRepositoryImpl advertRepository = new AdvertRepositoryImpl();

    public static void main(String[] args) {
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
        //System.out.println(advertRepository.getOne(4));

        /*Advert advert = advertRepository.getOne(25);
        System.out.println(advert);

        advert.setPrice(601.0);
        advert.setCurrency(new Currency(3, null));

        advertRepository.update(advert);
        System.out.println(advertRepository.update(advert));*/

        advertRepository.getAdvertsByTitle(5, 0, " AD ").stream().forEach(System.out::println);
        System.out.println(advertRepository.countWithTitle(" пл "));
    }

    private static void selectPageTest(){
        advertRepository.getAdverts(2, 0).stream().forEach(System.out::println);
    }

    private static void advertsCountTest(){
        System.out.println(advertRepository.count());
    }

    private static void advertsByUserId(int id){
        System.out.println(advertRepository.getAdvertsByUser(id));
    }

    private static void selectTagPageTest(){
        advertRepository.getAdvertsByTag(5, 2, "б/у").stream().forEach(System.out::println);
    }
}

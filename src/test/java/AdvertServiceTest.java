package test.java;


import main.java.dao.AdvertRepository;
import main.java.dao.AdvertRepositoryImpl;
import main.java.dao.ConnectionManager;
import main.java.service.AdvertServiceImpl;

public class AdvertServiceTest {

    private static AdvertServiceImpl advertService = new AdvertServiceImpl();

    public static void main(String[] args) {
        AdvertRepositoryImpl advertRepository = new AdvertRepositoryImpl();
        advertRepository.setConnection(new ConnectionManager().getConnection());
        advertService.setAdvertRepository(advertRepository);

        /*
        getLastPageTest(2);
        getLastPageTest(3);

        getAdvertsTest(1, 3);*/
        advertService.getAdvertsByTag(1, 5, "new_tag").stream().forEach(System.out::println);
    }

    private static void getLastPageTest(int pageSize){
        System.out.println(advertService.getLastPage(pageSize));
    }

    private static void getAdvertsTest(int page, int size){
        advertService.getAdverts(page, size).stream().forEach(System.out::println);
    }
}

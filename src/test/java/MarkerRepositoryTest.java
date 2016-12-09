package test.java;


import main.java.dao.ConnectionManager;
import main.java.dao.MarkerRepository;

public class MarkerRepositoryTest {

    private static MarkerRepository markerRepository = new MarkerRepository();

    public static void main(String[] args) {
        markerRepository.setConnection(new ConnectionManager().getConnection());

        /*
        Marker markerToAdd = new Marker();
        markerToAdd.setName("New marker");

        Marker addedMarker = markerRepository.add(markerToAdd);

        addedMarker.setName(addedMarker.getName() + "1");
        Marker updatedMarker = markerRepository.update(addedMarker);

        System.out.println(updatedMarker);
        */

        //System.out.println(markerRepository.remove(4));

        //System.out.println(markerRepository.getOne("Срочно"));

        System.out.println(markerRepository.getAdvertsCount(1));
        System.out.println(markerRepository.getAdvertsCount(5));
        System.out.println(markerRepository.getAdvertsCount(6));
    }
}

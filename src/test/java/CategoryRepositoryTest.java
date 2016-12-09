package test.java;


import main.java.dao.CategoryRepository;
import main.java.dao.ConnectionManager;

public class CategoryRepositoryTest {

    private static CategoryRepository categoryRepository = new CategoryRepository();

    public static void main(String[] args) {
        categoryRepository.setConnection(new ConnectionManager().getConnection());

        categoryRepository.getSubcategoriesWithMostAdverts().stream().forEach(System.out::println);
    }
}

package test.java;


import main.java.dao.ConnectionManager;
import main.java.dao.TagRepository;
import main.java.entity.Tag;

public class TagRepositoryTest {

    public static void main(String[] args) {
        TagRepository tagRepository = new TagRepository();
        tagRepository.setConnection(new ConnectionManager().getConnection());
        String tagName = "новый тег1";
        Tag tag = new Tag();
        tag.setName(tagName);

        tagRepository.addTag(tag);
        Tag insertedTag = tagRepository.getTag(tagName);

        System.out.println("id: " + insertedTag.getId());
        System.out.println("name: " + insertedTag.getName());
        System.out.println("ads amount: " + insertedTag.getAdvertsAmount());
    }
}

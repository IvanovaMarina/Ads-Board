package main.java.view;


import com.fasterxml.jackson.annotation.JsonProperty;
import main.java.entity.Category;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;


@Relation(collectionRelation = "categories")
public class CategoryView extends ResourceSupport{

    @JsonProperty(value = "id")
    private Integer categoryId;
    private String name;

    public CategoryView(){}

    public CategoryView(Category category){
        this.categoryId = category.getId();
        this.name = category.getName();
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category toCategory(){
        Category category = new Category();
        category.setId(this.getCategoryId());
        category.setName(this.getName());
        return category;
    }
}

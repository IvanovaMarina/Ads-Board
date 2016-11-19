package main.java.view;


import com.fasterxml.jackson.annotation.JsonProperty;
import main.java.entity.Subcategory;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation = "subcategories")
public class SubcategoryView extends ResourceSupport{

    @JsonProperty(value = "id")
    private Integer subcategoryId;
    private String name;

    public SubcategoryView(){}

    public SubcategoryView(Subcategory subcategory){
        this.subcategoryId = subcategory.getId();
        this.name = subcategory.getName();
    }

    public Integer getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Integer subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subcategory toSubcategory(){
        Subcategory subcategory = new Subcategory();
        subcategory.setId(this.getSubcategoryId());
        subcategory.setName(this.getName());
        return subcategory;
    }
}

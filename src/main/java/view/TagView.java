package main.java.view;


import com.fasterxml.jackson.annotation.JsonProperty;
import main.java.entity.Tag;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation = "tags")
public class TagView extends ResourceSupport{
    @JsonProperty(value = "id")
    private Integer tagId;
    private String name;

    public TagView(){}

    public TagView(Tag tag){
        this.tagId = tag.getId();
        this.name = tag.getName();
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tag toTag(){
        Tag tag =  new Tag();
        tag.setId(this.getTagId());
        tag.setName(this.getName());
        return tag;
    }
}

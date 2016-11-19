package main.java.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.java.entity.Marker;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;


@Relation(collectionRelation = "markers")
public class MarkerView extends ResourceSupport{
    @JsonProperty(value = "id")
    private Integer markerId;
    private String name;

    public MarkerView(){}

    public MarkerView(Marker marker){
        this.markerId = marker.getId();
        this.name = marker.getName();
    }

    public Integer getMarkerId() {
        return markerId;
    }

    public void setMarkerId(Integer markerId) {
        this.markerId = markerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Marker toMarker(){
        return new Marker(this.getMarkerId(), this.getName());
    }
}

package main.java.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.java.entity.Region;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation = "regions")
public class RegionView extends ResourceSupport{

    @JsonProperty("id")
    private Integer regionId;
    private String name;

    public RegionView(){}
    public RegionView(Region region) {
        this.regionId = region.getId();
        this.name = region.getName();
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Region toRegion(){
        Region region = new Region();
        region.setId(this.getRegionId());
        region.setName(this.getName());
        return region;
    }
}

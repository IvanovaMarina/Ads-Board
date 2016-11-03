package main.java.view;


import com.fasterxml.jackson.annotation.JsonProperty;
import main.java.entity.Country;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation = "countries")
public class CountryView extends ResourceSupport{

    @JsonProperty(value = "id")
    private Integer countryId;

    private String name;

    public CountryView(){}

    public CountryView(Country country){
        this.countryId = country.getId();
        this.name = country.getName();
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country toCountry(){
        return new Country(this.getCountryId(), this.getName());
    }
}

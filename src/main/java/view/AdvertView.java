package main.java.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.java.entity.*;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Relation(collectionRelation = "adverts")
public class AdvertView extends ResourceSupport{

    @JsonProperty(value = "id")
    private Integer advertId;
    private String title;
    private String description;
    @JsonProperty(value = "image", access = JsonProperty.Access.WRITE_ONLY)
    private String imageBase64;
    private LocalDateTime addTime;
    private Integer views;

    @JsonProperty(value = "owner")
    private UserView userView;

    @JsonProperty(value = "region")
    private RegionView regionView;
    @JsonProperty(value = "country")
    private CountryView countryView;


    @JsonProperty(value = "category")
    private CategoryView categoryView;
    @JsonProperty(value = "subcategory")
    private SubcategoryView subcategoryView;

    @JsonProperty(value = "marker")
    private MarkerView markerView;

    private List<TagView> tags = new ArrayList<>();
    private Double price;

    @JsonProperty(value = "currency")
    private CurrencyView currencyView;
    public AdvertView(){}

    public AdvertView(Advert advert){
        this.advertId = advert.getId();
        this.title = advert.getTitle();
        this.description = advert.getDescription();
        this.addTime = advert.getAddTime();
        this.views = advert.getViews();
        this.userView = new UserView(advert.getOwner());
        this.regionView = new RegionView(advert.getOwner().getRegion());
        this.countryView = new CountryView(advert.getOwner().getRegion().getCountry());
        this.categoryView = new CategoryView(advert.getSubcategory().getCategory());
        this.subcategoryView = new SubcategoryView(advert.getSubcategory());
        this.markerView = new MarkerView(advert.getMarker());
        for(Tag tag : advert.getTags()){
            tags.add(new TagView(tag));
        }
        this.price = advert.getPrice();
        this.currencyView = new CurrencyView(advert.getCurrency());
    }

    public Integer getAdvertId() {
        return advertId;
    }

    public void setAdvertId(Integer advertId) {
        this.advertId = advertId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public UserView getUserView() {
        return userView;
    }

    public void setUserView(UserView userView) {
        this.userView = userView;
    }

    public RegionView getRegionView() {
        return regionView;
    }

    public void setRegionView(RegionView regionView) {
        this.regionView = regionView;
    }

    public CountryView getCountryView() {
        return countryView;
    }

    public void setCountryView(CountryView countryView) {
        this.countryView = countryView;
    }

    public CategoryView getCategoryView() {
        return categoryView;
    }

    public void setCategoryView(CategoryView categoryView) {
        this.categoryView = categoryView;
    }

    public SubcategoryView getSubcategoryView() {
        return subcategoryView;
    }

    public void setSubcategoryView(SubcategoryView subcategoryView) {
        this.subcategoryView = subcategoryView;
    }

    public MarkerView getMarkerView() {
        return markerView;
    }

    public void setMarkerView(MarkerView markerView) {
        this.markerView = markerView;
    }

    public List<TagView> getTags() {
        return tags;
    }

    public void setTags(List<TagView> tags) {
        this.tags = tags;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public CurrencyView getCurrencyView() {
        return currencyView;
    }

    public void setCurrencyView(CurrencyView currencyView) {
        this.currencyView = currencyView;
    }

    public Advert toAdvert(){
        Advert advert = new Advert();
        User owner = new User();
        Subcategory subcategory = new Subcategory();
        Category category = new Category();
        //Region region = new Region();
        //Country country = new Country();
        Marker marker = new Marker();
        List<Tag> tags = new ArrayList<>();
        Currency currency = new Currency();

        advert.setOwner(owner);
        subcategory.setCategory(category);
        advert.setSubcategory(subcategory);
        //region.setCountry(country);
        //owner.setRegion(region);
        advert.setMarker(marker);
        advert.setTags(tags);
        advert.setCurrency(currency);


        advert.setId(this.getAdvertId());
        advert.setTitle(this.getTitle());
        advert.setDescription(this.getDescription());
        owner.setId(this.getUserView().getUserId());
        //region.setId(this.getRegionView().getRegionId());
        subcategory.setId(this.getSubcategoryView().getSubcategoryId());
        marker.setId(this.getMarkerView().getMarkerId());
        for(TagView tagView: this.tags){
            Tag tag = new Tag();
            tag.setName(tagView.getName());
            tags.add(tag);
        }
        advert.setPrice(this.getPrice());
        currency.setId(this.getCurrencyView().getCurrencyId());
        return advert;
    }
}

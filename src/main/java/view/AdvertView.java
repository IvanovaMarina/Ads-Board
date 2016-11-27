package main.java.view;

import main.java.entity.*;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.time.LocalDateTime;
import java.util.List;


@Relation(collectionRelation = "adverts")
public class AdvertView extends ResourceSupport{

    private Integer advertId;
    private String title;
    private String description;

    private LocalDateTime addTime;
    private Integer views;
    private UserView owner;
    private SubcategoryView subcategory;
    private CategoryView category;
    private MarkerView marker;
    private List<TagView> tags;
    private Double price;
    private CurrencyView currency;

    public AdvertView(){}

    public AdvertView(Advert advert){

    }


}

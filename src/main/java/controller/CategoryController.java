package main.java.controller;


import main.java.service.CategoryService;
import main.java.view.CategoryView;
import main.java.view.SubcategoryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController extends AbstractController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET)
    Resources<CategoryView> getAllCategories(){
        Link categoriesLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(CategoryController.class).getAllCategories())
                .withSelfRel();

        List<CategoryView> categoryViewList = categoryService.getAllCategories().stream()
                .map(category -> {
                    CategoryView categoryView = new CategoryView(category);
                    Link categoryLink = new Link(categoriesLink.getHref() + "/" + category.getId(), "self");
                    Link advertsLink = ControllerLinkBuilder
                            .linkTo(ControllerLinkBuilder
                                    .methodOn(AdvertController.class)
                                    .getAdvertsByCategory(1, 2, category.getId()))
                            .withRel("adverts");
                    categoryView.add(categoryLink);
                    categoryView.add(advertsLink);
                    return categoryView;
                })
                .collect(Collectors.toList());
        Resources<CategoryView> categoryViews = new Resources<>(categoryViewList, categoriesLink);
        return categoryViews;
    }

    @RequestMapping(value = "/{categoryId}/subcategories", method = RequestMethod.GET)
    Resources<SubcategoryView> getSubcategories(@PathVariable Integer categoryId){
        Link subcategoriesLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(CategoryController.class).getSubcategories(categoryId))
                .withSelfRel();
        List<SubcategoryView> subcategoryList = categoryService.getSubcategories(categoryId).stream()
                .map(subcategory -> {
                    SubcategoryView subcategoryView = new SubcategoryView();
                    subcategoryView.setName(subcategory.getName());
                    subcategoryView.setSubcategoryId(subcategory.getId());
                    Link subcategoryLink = new Link(subcategoriesLink.getHref() + "/" + subcategory.getId(), "self");
                    Link advertsLink = ControllerLinkBuilder
                            .linkTo(ControllerLinkBuilder
                                    .methodOn(AdvertController.class)
                                    .getAdvertsBySubcategory(1, 2, subcategory.getId()))
                            .withRel("adverts");
                    subcategoryView.add(subcategoryLink);
                    subcategoryView.add(advertsLink);
                    return subcategoryView;
                })
                .collect(Collectors.toList());

        Resources<SubcategoryView> subcategoryResources = new Resources<>(subcategoryList, subcategoriesLink);
        return subcategoryResources;
    }

    @RequestMapping(value = "/subcategories/mostAdverts", method = RequestMethod.GET)
    Resources<SubcategoryView> getSubcategoriesWithMostAdverts(@RequestHeader HttpHeaders headers) {
        adminAuthorize(headers);
        List<SubcategoryView> subcategoryList = categoryService.getSubcategoriesWithMostAdverts().stream()
                .map(subcategory -> {
                    SubcategoryView subcategoryView = new SubcategoryView();
                    subcategoryView.setName(subcategory.getName());
                    subcategoryView.setSubcategoryId(subcategory.getId());
                    return subcategoryView;
                })
                .sorted((c1, c2) -> c1.getSubcategoryId() - c2.getSubcategoryId())
                .collect(Collectors.toList());

        Resources<SubcategoryView> subcategoryResources = new Resources<>(subcategoryList);
        return subcategoryResources;
    }
}

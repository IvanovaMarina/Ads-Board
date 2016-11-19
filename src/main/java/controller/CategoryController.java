package main.java.controller;


import main.java.entity.Subcategory;
import main.java.service.CategoryService;
import main.java.view.CategoryView;
import main.java.view.SubcategoryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

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
                    categoryView.add(categoryLink);
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
                    subcategoryView.add(subcategoryLink);
                    return subcategoryView;
                })
                .collect(Collectors.toList());

        Resources<SubcategoryView> subcategoryResources = new Resources<>(subcategoryList, subcategoriesLink);
        return subcategoryResources;
    }
}

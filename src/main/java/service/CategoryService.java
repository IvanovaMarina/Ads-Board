package main.java.service;

import main.java.dao.CategoryRepository;
import main.java.entity.Category;
import main.java.entity.Subcategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories(){
        return categoryRepository.getAllCategories();
    }

    public List<Subcategory> getSubcategories(Integer categoryId){
        return categoryRepository.getSubcategories(categoryId);
    }
}

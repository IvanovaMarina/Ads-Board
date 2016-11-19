package main.java.entity;


public class Subcategory {

    private Integer id;
    private String name;
    private Category category;

    public Subcategory(Integer id, String name, Category category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public Subcategory(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Subcategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                '}';
    }
}

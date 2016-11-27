package main.java.entity;




public class Tag {
    private Integer id;
    private String name;
    private Integer advertsAmount;

    public Tag(){}

    public Tag(Integer id, String name, Integer advertsAmount) {
        this.id = id;
        this.name = name;
        this.advertsAmount = advertsAmount;
    }

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

    public Integer getAdvertsAmount() {
        return advertsAmount;
    }

    public void setAdvertsAmount(Integer advertsAmount) {
        this.advertsAmount = advertsAmount;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", advertsAmount=" + advertsAmount +
                '}';
    }
}

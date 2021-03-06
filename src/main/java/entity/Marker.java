package main.java.entity;


public class Marker {

    private Integer id;
    private String name;

    public Marker(){}

    public Marker(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "Marker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

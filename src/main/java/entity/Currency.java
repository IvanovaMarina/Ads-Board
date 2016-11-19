package main.java.entity;


public class Currency {
    private Integer id;
    private String abbreviation;

    public Currency(){}

    public Currency(Integer id, String abbreviation) {
        this.id = id;
        this.abbreviation = abbreviation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}

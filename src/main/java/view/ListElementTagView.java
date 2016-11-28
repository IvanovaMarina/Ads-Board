package main.java.view;


import main.java.entity.Tag;

public class ListElementTagView extends TagView {
    private Integer advertsAmount;

    public ListElementTagView(){}

    public ListElementTagView(Tag tag) {
        super(tag);
        this.advertsAmount = tag.getAdvertsAmount();
    }

    public Integer getAdvertsAmount() {
        return advertsAmount;
    }

    public void setAdvertsAmount(Integer advertsAmount) {
        this.advertsAmount = advertsAmount;
    }

    public Tag toTag(){
        Tag tag = super.toTag();
        tag.setAdvertsAmount(this.getAdvertsAmount());
        return tag;
    }
}

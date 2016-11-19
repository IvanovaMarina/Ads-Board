package main.java.view;


import com.fasterxml.jackson.annotation.JsonProperty;
import main.java.entity.Currency;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation = "currencies")
public class CurrencyView extends ResourceSupport{
    @JsonProperty(value = "id")
    private Integer currencyId;
    private String abbreviation;

    public CurrencyView(){}

    public CurrencyView(Currency currency){
        this.currencyId = currency.getId();
        this.abbreviation = currency.getAbbreviation();
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public Currency toCurrency(){
        Currency currency = new Currency();
        currency.setId(this.getCurrencyId());
        currency.setAbbreviation(this.getAbbreviation());
        return currency;
    }
}

package main.java.controller;


import main.java.service.CurrencyService;
import main.java.view.CategoryView;
import main.java.view.CurrencyView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/currencies")
public class CurrencyController {

    @Autowired
    CurrencyService currencyService;

    @RequestMapping(method = RequestMethod.GET)
    Resources<CurrencyView> getAllCurrencies(){
        Link currenciesLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(CurrencyController.class).getAllCurrencies())
                .withSelfRel();

        List<CurrencyView> currencyViewList = currencyService.getAllCurrencies().stream()
                .map(currency -> {
                    CurrencyView currencyView = new CurrencyView(currency);
                    Link currencyLink = new Link(currenciesLink.getHref() + "/" + currency.getId(), "self");
                    currencyView.add(currencyLink);
                    return currencyView;
                })
                .collect(Collectors.toList());
        Resources<CurrencyView> categoryViews = new Resources<>(currencyViewList, currenciesLink);
        return categoryViews;
    }
}

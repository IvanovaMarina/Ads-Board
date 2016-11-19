package main.java.service;

import main.java.dao.CurrencyRepository;
import main.java.entity.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    public List<Currency> getAllCurrencies(){
        return currencyRepository.getAllCurrencies();
    }
}

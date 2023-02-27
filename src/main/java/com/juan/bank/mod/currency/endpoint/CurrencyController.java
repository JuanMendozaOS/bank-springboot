package com.juan.bank.mod.currency.endpoint;

import com.juan.bank.mod.currency.model.Currency;
import com.juan.bank.mod.currency.model.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Juan Mendoza 15/2/2023
 */
@RestController
@RequestMapping("/currencies")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/id")
    public Currency findById(@PathVariable("id") Long id){
        return currencyService.findById(id);
    }

    @GetMapping
    public List<Currency> findAll(){
        return currencyService.findAll();
    }

    @PostMapping(consumes = "*/*")
    public ResponseEntity<Currency> addCurrency(Currency currency){
        if (containsNullOrEmpty(currency)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (currencyExists(currency)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(createCurrency(currency), HttpStatus.OK);
    }

    private Currency createCurrency(Currency currency) {
        Currency entity = new Currency();
        entity.setName(currency.getName());
        entity.setIsoCode(currency.getIsoCode());
        entity.setSymbol(currency.getSymbol());
        entity.setLocal(currency.isLocal());
        entity.setEnabled(true);
        return currencyService.create(entity);
    }

    private boolean currencyExists(Currency currency) {
        if(currencyService.existsByName(currency.getName())){
            return true;
        }
        if (currencyService.existsByIsoCode(currency.getIsoCode())){
            return true;
        }
        return false;
    }

    private boolean containsNullOrEmpty(Currency currency) {
        if (currency == null || currency.getName() == null
                || currency.getIsoCode() == null || currency.getSymbol() == null){
            return true;
        }
        if (currency.getName().isEmpty() || currency.getIsoCode().isEmpty()
                || currency.getSymbol().isEmpty()){
            return true;
        }
        return false;
    }

}

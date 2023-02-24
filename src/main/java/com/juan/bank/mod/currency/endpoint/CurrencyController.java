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

    // TODO añadir validaciones
    @GetMapping("/id")
    public Currency findById(@PathVariable("id") Long id){
        return currencyService.findById(id);
    }

    @GetMapping
    public List<Currency> findAll(){
        return currencyService.findAll();
    }

    @PostMapping
    public ResponseEntity<Currency> addCurrency(@RequestBody Currency currency){
        Currency entity = new Currency();
        entity.setName(currency.getName());
        entity.setIsoCode(currency.getIsoCode());
        entity.setSymbol(currency.getSymbol());
        entity.setLocal(currency.isLocal());
        entity.setEnabled(true);
        entity = currencyService.create(entity);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

}

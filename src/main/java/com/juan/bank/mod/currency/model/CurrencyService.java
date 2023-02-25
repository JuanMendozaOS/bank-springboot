package com.juan.bank.mod.currency.model;

import com.juan.bank.app.model.EntityServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Juan Mendoza 15/2/2023
 */

@Service
public class CurrencyService implements EntityServiceInterface<Currency> {

    @Autowired
    private CurrencyRepository currencyRepo;

    @Override
    public Currency create(Currency entity) {
        return currencyRepo.save(entity);
    }

    @Override
    public Currency update(Currency entity) {
        return currencyRepo.save(entity);
    }

    @Override
    public Currency findById(Long id) {
        Optional<Currency> currencyOptional;
        currencyOptional = currencyRepo.findById(id);
        return currencyOptional.orElse(null);
    }

    @Override
    public List<Currency> findAll() {
        return currencyRepo.findAll();
    }

    public boolean existsByIsoCode(String isoCode){
        return currencyRepo.existsByIsoCode(isoCode);
    }

    public Currency findByIsoCode(String currencyIsoCode) {
        return currencyRepo.findByIsoCode(currencyIsoCode);
    }

    public boolean existsByName(String name) {
        return currencyRepo.existsByName(name);
    }
}

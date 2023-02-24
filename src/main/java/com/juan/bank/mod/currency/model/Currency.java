package com.juan.bank.mod.currency.model;

import jakarta.persistence.*;

/**
 * @author Juan Mendoza 15/2/2023
 */
@Entity
@Table
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String symbol;
    private String isoCode;
    private boolean enabled;
    private boolean isLocal;

    public Currency(){}

    public Currency(Long id, String name, String symbol, String isoCode, boolean enabled, boolean isLocal) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.isoCode = isoCode;
        this.enabled = enabled;
        this.isLocal = isLocal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", code='" + isoCode + '\'' +
                ", enabled=" + enabled +
                ", isLocal=" + isLocal +
                '}';
    }
}

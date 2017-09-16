package com.youyou.conf;

/**
 * Created by vyouyou on 17-9-3.
 */
public enum SymbolEnums {
    A("A"), B("B"), C("C"), D("D"), E("E");;

    private String symbol;

    SymbolEnums(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}

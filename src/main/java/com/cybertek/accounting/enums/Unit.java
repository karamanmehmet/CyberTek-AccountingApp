package com.cybertek.accounting.enums;

public enum Unit {

    PIECE("Piece"), LB("Pounds(lb)"), KILOS("Kilos(kg)"), METER("Meter(m)"), CM("Centimeter(cm)"), LITER("Liter(l)"), GALLON("Gallon(gal)");

    private final String value;

    private Unit(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}

package com.cybertek.accounting.enums;

public enum InvoiceType {
    SALES("Sales"),PURCHASE("Purchase");
    private final String value;
    private InvoiceType(String value){
        this.value = value;
    }
    public String getValue(){
        return value;
    }

}


package com.cybertek.accounting.enums;

public enum InvoiceStatus {

    OPEN("Open"),
    APPROVED("Approved"),
    ARCHIVED("Archived");

    private final String value;

    private InvoiceStatus(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}

package com.cybertek.accounting.enums;

public enum ClientVendorType {

    CLIENT("Client"),
    VENDOR("Vendor");

    private final String value;
    private ClientVendorType(String value){
        this.value = value;
    }
    public String getValue(){
        return value;
    }
}

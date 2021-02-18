package com.cybertek.accounting.enums;

public enum Status {
    ACTIVE("Active"), PASSIVE("Passive"), CLOSED("Closed");

    private final String value;

    private Status(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}

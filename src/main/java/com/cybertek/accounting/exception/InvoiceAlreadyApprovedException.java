package com.cybertek.accounting.exception;

public class InvoiceAlreadyApprovedException extends Exception {

    public InvoiceAlreadyApprovedException(String message) {
        super(message);
    }
}

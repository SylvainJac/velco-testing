package com.velco.velcotesting.dto;

public enum ErrorMessage {
    NUM_REFERENCE("Incorrect value for num reference"),
    TYPE("Incorrect value for type"),
    PRICE("Incorrect value for price"),
    SIZE("Incorrect value for size"),
    LENGTH_LINE("Incorrect length line");

    public final String label;

    ErrorMessage(String label) {
        this.label = label;
    }
}

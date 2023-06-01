package com.velco.velcotesting.dto;

public enum Field {
    NUM_REFERENCE("numReference"),
    PRICE("price"),
    TYPE("type"),
    SIZE("size");

    public final String label;

    Field(String label) {
        this.label = label;
    }
}

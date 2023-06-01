package com.velco.velcotesting.dto;

public enum Sort {
    ASC("asc"),
    DESC("desc");

    public final String label;

    Sort(String label) {
        this.label = label;
    }
}

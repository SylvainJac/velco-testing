package com.velco.velcotesting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Reference {

    private String numReference;

    private Integer size;

    private Double price;

    private Type type;
}

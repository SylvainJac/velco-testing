package com.velco.velcotesting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorReference {

    private Integer line;

    private ErrorMessage message;

    private String value;
}

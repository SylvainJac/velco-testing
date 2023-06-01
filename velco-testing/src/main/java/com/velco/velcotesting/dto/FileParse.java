package com.velco.velcotesting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class FileParse {
    private String inputFile;

    private List<Reference> references;

    private List<ErrorReference> errors;

    private Metadata metadata;
}

package com.velco.velcotesting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Metadata {

    private Integer referenceCount;

    private Integer errorCount;

    private Integer lineCount;

}

package com.velco.velcotesting.exception;

import com.velco.velcotesting.dto.ErrorMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReferenceErrorException extends Exception {

    private ErrorMessage errorMessage;

    public ReferenceErrorException(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}

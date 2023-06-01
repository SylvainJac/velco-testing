package com.velco.velcotesting.service;

import com.velco.velcotesting.dto.ErrorMessage;
import com.velco.velcotesting.dto.ErrorReference;
import com.velco.velcotesting.dto.Reference;
import com.velco.velcotesting.dto.Type;
import com.velco.velcotesting.exception.ReferenceErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ReferenceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReferenceService.class);

    @Value("${velco.index.numReference:0}")
    private Integer indexNumReference;

    @Value("${velco.index.type:1}")
    private Integer indexType;

    @Value("${velco.index.price:2}")
    private Integer indexPrice;

    @Value("${velco.index.size:3}")
    private Integer indexSize;

    public Reference getReference(String[] lineVariables) throws ReferenceErrorException {
        return Reference.builder()
                .numReference(getNumReference(lineVariables))
                .type(getType(lineVariables))
                .price(getPrice(lineVariables))
                .size(getSize(lineVariables))
                .build();
    }

    public ErrorReference getLengthLineError(Integer line, String value) {
        LOGGER.error("Error length line on format for value {} at line {}", value, line);
        return ErrorReference.builder()
                .line(line)
                .value(value)
                .message(ErrorMessage.LENGTH_LINE)
                .build();
    }

    private String getNumReference(String[] lineVariables) throws ReferenceErrorException {
        String numReference = lineVariables[indexNumReference];

        if (!numReference.matches("^\\d{10}$")) {
            LOGGER.error("Error num reference on format for value {}", String.join(";", lineVariables));
            throw new ReferenceErrorException(ErrorMessage.NUM_REFERENCE);
        }

        return numReference;
    }

    private Type getType(String[] lineVariables) throws ReferenceErrorException {
        Type type;
        try {
            type = Type.valueOf(lineVariables[indexType]);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error type on format for value {}", String.join(";", lineVariables), e);
            throw new ReferenceErrorException(ErrorMessage.TYPE);
        }
        return type;
    }

    private Double getPrice(String[] lineVariables) throws ReferenceErrorException {
        double price;
        try {
            price = Double.parseDouble(lineVariables[indexPrice]);
        } catch (NumberFormatException e) {
            LOGGER.error("Error price on format for value {}", String.join(";", lineVariables), e);
            throw new ReferenceErrorException(ErrorMessage.PRICE);
        }
        return price;
    }

    private Integer getSize(String[] lineVariables) throws ReferenceErrorException {
        int size;
        try {
            size = Integer.parseInt(lineVariables[indexSize]);
        } catch (NumberFormatException e) {
            LOGGER.error("Error size on format for value {}", String.join(";", lineVariables), e);
            throw new ReferenceErrorException(ErrorMessage.SIZE);
        }
        return size;
    }
}

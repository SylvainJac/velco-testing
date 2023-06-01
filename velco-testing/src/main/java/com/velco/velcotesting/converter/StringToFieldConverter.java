package com.velco.velcotesting.converter;

import com.velco.velcotesting.dto.Field;
import org.springframework.core.convert.converter.Converter;

import java.util.Arrays;

public class StringToFieldConverter implements Converter<String, Field> {
    @Override
    public Field convert(String source) {
        return Arrays.stream(Field.values()).filter(f -> source.equals(f.label)).findFirst().orElse(null);
    }
}

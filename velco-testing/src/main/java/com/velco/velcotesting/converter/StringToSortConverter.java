package com.velco.velcotesting.converter;

import com.velco.velcotesting.dto.Sort;
import org.springframework.core.convert.converter.Converter;

import java.util.Arrays;

public class StringToSortConverter implements Converter<String, Sort> {
    @Override
    public Sort convert(String source) {
        return Arrays.stream(Sort.values()).filter(s -> source.equals(s.label)).findFirst().orElse(null);
    }
}

package com.velco.velcotesting.util;

import com.velco.velcotesting.dto.Field;
import com.velco.velcotesting.dto.Reference;
import com.velco.velcotesting.dto.Sort;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ReferenceUtil {

    private ReferenceUtil() {
        throw new IllegalArgumentException("Util");
    }

    public static List<Reference> sortReferences(List<Reference> references, final Sort sort, final Field field) {
        if (sort != null && field != null) {
            switch (field) {
                case NUM_REFERENCE:
                    return sortReferencesByNumReference(references, sort);
                case PRICE:
                    return sortReferencesByPrice(references, sort);
                case TYPE:
                    return sortReferencesByType(references, sort);
                case SIZE:
                    return sortReferencesBySize(references, sort);
                default:
                    return references;
            }
        }
        return references;
    }

    private static List<Reference> sortReferencesByNumReference(List<Reference> references, final Sort sort) {
        return references.stream().sorted((reference1, reference2) -> {
            if (Sort.ASC.equals(sort)) {
                return reference1.getNumReference().compareToIgnoreCase(reference2.getNumReference());
            }
            return reference2.getNumReference().compareToIgnoreCase(reference1.getNumReference());
        }).collect(Collectors.toList());
    }

    private static List<Reference> sortReferencesByPrice(List<Reference> references, final Sort sort) {
        return references.stream().sorted((reference1, reference2) -> {
            if (Sort.ASC.equals(sort)) {
                return reference1.getPrice().compareTo(reference2.getPrice());
            }
            return reference2.getPrice().compareTo(reference1.getPrice());
        }).collect(Collectors.toList());
    }

    private static List<Reference> sortReferencesByType(List<Reference> references, final Sort sort) {
        return references.stream().sorted((reference1, reference2) -> {
            if (Sort.ASC.equals(sort)) {
                return reference1.getType().name().compareToIgnoreCase(reference2.getType().name());
            }
            return reference2.getType().name().compareToIgnoreCase(reference1.getType().name());
        }).collect(Collectors.toList());
    }

    private static List<Reference> sortReferencesBySize(List<Reference> references, final Sort sort) {
        return references.stream().sorted((reference1, reference2) -> {
            if (Sort.ASC.equals(sort)) {
                return reference1.getSize() - reference2.getSize();
            }
            return reference2.getSize() - reference1.getSize();
        }).collect(Collectors.toList());
    }
}

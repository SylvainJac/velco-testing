package com.velco.velcotesting.util;

import com.velco.velcotesting.dto.Field;
import com.velco.velcotesting.dto.Reference;
import com.velco.velcotesting.dto.Sort;
import com.velco.velcotesting.dto.Type;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class ReferenceUtilTest {


    @Test
    public void testSortReferencesByNumReferenceAsc() {
        Reference reference1 = Reference.builder()
                .numReference("2222222222")
                .type(Type.R)
                .price(12.0)
                .size(1)
                .build();
        Reference reference2 = Reference.builder()
                .numReference("1111111111")
                .type(Type.B)
                .price(24.0)
                .size(2)
                .build();

        List<Reference> references = new ArrayList<>();
        references.add(reference1);
        references.add(reference2);

        List<Reference> result = ReferenceUtil.sortReferences(references, Sort.ASC, Field.NUM_REFERENCE);

        Assert.assertEquals("1111111111", result.get(0).getNumReference());
    }

    @Test
    public void testSortReferencesByNumReferenceDesc() {
        Reference reference1 = Reference.builder()
                .numReference("1111111111")
                .type(Type.R)
                .price(12.0)
                .size(1)
                .build();
        Reference reference2 = Reference.builder()
                .numReference("2222222222")
                .type(Type.B)
                .price(24.0)
                .size(2)
                .build();

        List<Reference> references = new ArrayList<>();
        references.add(reference1);
        references.add(reference2);

        List<Reference> result = ReferenceUtil.sortReferences(references, Sort.DESC, Field.NUM_REFERENCE);

        Assert.assertEquals("1111111111", result.get(1).getNumReference());
    }

    @Test
    public void testSortReferencesByTypeAsc() {
        Reference reference1 = Reference.builder()
                .numReference("2222222222")
                .type(Type.R)
                .price(12.0)
                .size(1)
                .build();
        Reference reference2 = Reference.builder()
                .numReference("1111111111")
                .type(Type.B)
                .price(24.0)
                .size(2)
                .build();

        List<Reference> references = new ArrayList<>();
        references.add(reference1);
        references.add(reference2);

        List<Reference> result = ReferenceUtil.sortReferences(references, Sort.ASC, Field.TYPE);

        Assert.assertEquals("1111111111", result.get(0).getNumReference());
    }

    @Test
    public void testSortReferencesByTypeDesc() {
        Reference reference1 = Reference.builder()
                .numReference("2222222222")
                .type(Type.B)
                .price(12.0)
                .size(1)
                .build();
        Reference reference2 = Reference.builder()
                .numReference("1111111111")
                .type(Type.R)
                .price(24.0)
                .size(2)
                .build();

        List<Reference> references = new ArrayList<>();
        references.add(reference1);
        references.add(reference2);

        List<Reference> result = ReferenceUtil.sortReferences(references, Sort.DESC, Field.TYPE);

        Assert.assertEquals("1111111111", result.get(0).getNumReference());
    }

    @Test
    public void testSortReferencesByPriceAsc() {
        Reference reference1 = Reference.builder()
                .numReference("2222222222")
                .type(Type.B)
                .price(12.0)
                .size(1)
                .build();
        Reference reference2 = Reference.builder()
                .numReference("1111111111")
                .type(Type.R)
                .price(4.0)
                .size(2)
                .build();

        List<Reference> references = new ArrayList<>();
        references.add(reference1);
        references.add(reference2);

        List<Reference> result = ReferenceUtil.sortReferences(references, Sort.ASC, Field.PRICE);

        Assert.assertEquals("1111111111", result.get(0).getNumReference());
    }

    @Test
    public void testSortReferencesByPriceDesc() {
        Reference reference1 = Reference.builder()
                .numReference("2222222222")
                .type(Type.B)
                .price(12.0)
                .size(1)
                .build();
        Reference reference2 = Reference.builder()
                .numReference("1111111111")
                .type(Type.R)
                .price(40.0)
                .size(2)
                .build();

        List<Reference> references = new ArrayList<>();
        references.add(reference1);
        references.add(reference2);

        List<Reference> result = ReferenceUtil.sortReferences(references, Sort.DESC, Field.PRICE);

        Assert.assertEquals("1111111111", result.get(0).getNumReference());
    }

    @Test
    public void testSortReferencesBySizeAsc() {
        Reference reference1 = Reference.builder()
                .numReference("2222222222")
                .type(Type.B)
                .price(12.0)
                .size(12)
                .build();
        Reference reference2 = Reference.builder()
                .numReference("1111111111")
                .type(Type.R)
                .price(40.0)
                .size(2)
                .build();

        List<Reference> references = new ArrayList<>();
        references.add(reference1);
        references.add(reference2);

        List<Reference> result = ReferenceUtil.sortReferences(references, Sort.ASC, Field.SIZE);

        Assert.assertEquals("1111111111", result.get(0).getNumReference());
    }

    @Test
    public void testSortReferencesBySizeDesc() {
        Reference reference1 = Reference.builder()
                .numReference("2222222222")
                .type(Type.B)
                .price(12.0)
                .size(1)
                .build();
        Reference reference2 = Reference.builder()
                .numReference("1111111111")
                .type(Type.R)
                .price(40.0)
                .size(2)
                .build();

        List<Reference> references = new ArrayList<>();
        references.add(reference1);
        references.add(reference2);

        List<Reference> result = ReferenceUtil.sortReferences(references, Sort.DESC, Field.SIZE);

        Assert.assertEquals("1111111111", result.get(0).getNumReference());
    }

}

package com.velco.velcotesting.service;

import com.velco.velcotesting.dto.ErrorMessage;
import com.velco.velcotesting.dto.ErrorReference;
import com.velco.velcotesting.dto.Reference;
import com.velco.velcotesting.dto.Type;
import com.velco.velcotesting.exception.ReferenceErrorException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class ReferenceServiceTest {

    @InjectMocks
    private ReferenceService referenceService;

    @Before
    public void setConfig() {
        ReflectionTestUtils.setField(referenceService, "indexNumReference", 0);
        ReflectionTestUtils.setField(referenceService, "indexType", 1);
        ReflectionTestUtils.setField(referenceService, "indexPrice", 2);
        ReflectionTestUtils.setField(referenceService, "indexSize", 3);

    }

    @Test
    public void testGetReferenceOk() throws ReferenceErrorException {

        String[] lineVariables = new String[4];
        lineVariables[0] = "1234567890";
        lineVariables[1] = "R";
        lineVariables[2] = "12.0";
        lineVariables[3] = "4";

        Reference reference = referenceService.getReference(lineVariables);

        Double price = 12.0;
        Integer size = 4;

        Assert.assertNotNull(reference);
        Assert.assertEquals("1234567890", reference.getNumReference());
        Assert.assertEquals(Type.R, reference.getType());
        Assert.assertEquals(price, reference.getPrice());
        Assert.assertEquals(size, reference.getSize());
    }

    @Test
    public void testGetReferenceKoNumReference() {
        String[] lineVariables = new String[4];
        lineVariables[0] = "123456789012";
        lineVariables[1] = "R";
        lineVariables[2] = "12.0";
        lineVariables[3] = "4";

        try {
            Reference reference = referenceService.getReference(lineVariables);
            Assert.fail();
        } catch (ReferenceErrorException e) {
            Assert.assertNotNull(e);
            Assert.assertEquals(ErrorMessage.NUM_REFERENCE, e.getErrorMessage());
        }
    }

    @Test
    public void testGetReferenceKoType() {

        String[] lineVariables = new String[4];
        lineVariables[0] = "1234567890";
        lineVariables[1] = "A";
        lineVariables[2] = "12.0";
        lineVariables[3] = "4";

        try {
            Reference reference = referenceService.getReference(lineVariables);
            Assert.fail();
        } catch (ReferenceErrorException e) {
            Assert.assertNotNull(e);
            Assert.assertEquals(ErrorMessage.TYPE, e.getErrorMessage());
        }
    }

    @Test
    public void testGetReferenceKoPrice() {

        String[] lineVariables = new String[4];
        lineVariables[0] = "1234567890";
        lineVariables[1] = "R";
        lineVariables[2] = "A";
        lineVariables[3] = "4";

        try {
            Reference reference = referenceService.getReference(lineVariables);
            Assert.fail();
        } catch (ReferenceErrorException e) {
            Assert.assertNotNull(e);
            Assert.assertEquals(ErrorMessage.PRICE, e.getErrorMessage());
        }
    }

    @Test
    public void testGetReferenceKoSize() {

        String[] lineVariables = new String[4];
        lineVariables[0] = "1234567890";
        lineVariables[1] = "R";
        lineVariables[2] = "12.0";
        lineVariables[3] = "A";

        try {
            Reference reference = referenceService.getReference(lineVariables);
            Assert.fail();
        } catch (ReferenceErrorException e) {
            Assert.assertNotNull(e);
            Assert.assertEquals(ErrorMessage.SIZE, e.getErrorMessage());
        }
    }


    @Test
    public void testGetLengthLineError() {
        final Integer line = 1;
        final String value = "value";

        ErrorReference errorReference = referenceService.getLengthLineError(line, value);

        Assert.assertNotNull(errorReference);
        Assert.assertEquals(ErrorMessage.LENGTH_LINE, errorReference.getMessage());
        Assert.assertEquals(line, errorReference.getLine());
        Assert.assertEquals(value, errorReference.getValue());
    }

}

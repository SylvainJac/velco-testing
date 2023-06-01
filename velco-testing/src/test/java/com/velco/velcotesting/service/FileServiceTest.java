package com.velco.velcotesting.service;


import com.velco.velcotesting.dto.ErrorMessage;
import com.velco.velcotesting.dto.ErrorReference;
import com.velco.velcotesting.dto.Field;
import com.velco.velcotesting.dto.FileParse;
import com.velco.velcotesting.dto.Reference;
import com.velco.velcotesting.dto.Sort;
import com.velco.velcotesting.dto.Type;
import com.velco.velcotesting.exception.ExtensionErrorException;
import com.velco.velcotesting.exception.ReferenceErrorException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileServiceTest {

    @InjectMocks
    private FileService fileService;

    @Mock
    private ReferenceService referenceService;

    @Before
    public void setConfig() {
        ReflectionTestUtils.setField(fileService, "extension", ".txt");
        ReflectionTestUtils.setField(fileService, "separator", ";");
    }

    @Test
    public void testTransformFileOk() throws ExtensionErrorException, ReferenceErrorException {

        final String name = "file.txt";
        final String originalName = "file.txt";
        final String contentType = "text/plain";
        final String content = "1450100040;R;45.12;27\n" +
                "1450100048;G;12.0;145";
        final MockMultipartFile file = new MockMultipartFile(name, originalName, contentType, content.getBytes());
        final Sort sort = Sort.ASC;
        final Field field = Field.NUM_REFERENCE;

        final Reference reference1 = Reference.builder()
                .numReference("1111111111")
                .type(Type.R)
                .price(1.0)
                .size(1)
                .build();

        final Reference reference2 = Reference.builder()
                .numReference("2222222222")
                .type(Type.B)
                .price(2.0)
                .size(2)
                .build();

        when(referenceService.getReference(any())).thenReturn(reference1, reference2);

        FileParse fileParse = fileService.transformFile(file, sort, field);

        Assert.assertNotNull(fileParse);
        Assert.assertEquals(2, fileParse.getReferences().size());
        Assert.assertEquals("1111111111", fileParse.getReferences().get(0).getNumReference());
        Assert.assertEquals("2222222222", fileParse.getReferences().get(1).getNumReference());
        Assert.assertEquals(0, fileParse.getErrors().size());
        Assert.assertEquals(2, fileParse.getMetadata().getReferenceCount().intValue());
        Assert.assertEquals(0, fileParse.getMetadata().getErrorCount().intValue());
        Assert.assertEquals(2, fileParse.getMetadata().getLineCount().intValue());
    }

    @Test
    public void testTransformFileKoFileExtension() {

        final String name = "file.txt";
        final String originalName = "file.tx";
        final String contentType = "text/plain";
        final String content = "1450100040;R;45.12;27\n" +
                "1450100048;G;12.0;145";
        final MockMultipartFile file = new MockMultipartFile(name, originalName, contentType, content.getBytes());
        final Sort sort = Sort.ASC;
        final Field field = Field.NUM_REFERENCE;

        try {
            FileParse fileParse = fileService.transformFile(file, sort, field);
            Assert.fail();
        } catch (ExtensionErrorException e) {
            Assert.assertNotNull(e);
        }
    }

    @Test
    public void testTransformFileKoCheckSizeLineVariable() throws ExtensionErrorException, ReferenceErrorException {

        final String name = "file.txt";
        final String originalName = "file.txt";
        final String contentType = "text/plain";
        final String content = "1450100040;R;45.12;27\n" +
                "1450100048;G;12.0";
        final MockMultipartFile file = new MockMultipartFile(name, originalName, contentType, content.getBytes());
        final Sort sort = Sort.ASC;
        final Field field = Field.NUM_REFERENCE;

        final Reference reference1 = Reference.builder()
                .numReference("1111111111")
                .type(Type.R)
                .price(1.0)
                .size(1)
                .build();

        final Reference reference2 = Reference.builder()
                .numReference("2222222222")
                .type(Type.B)
                .price(2.0)
                .size(2)
                .build();

        when(referenceService.getReference(any())).thenReturn(reference1, reference2);
        when(referenceService.getLengthLineError(any(), any())).thenReturn(ErrorReference.builder().build());

        FileParse fileParse = fileService.transformFile(file, sort, field);

        Assert.assertNotNull(fileParse);
        Assert.assertEquals(1, fileParse.getReferences().size());
        Assert.assertEquals("1111111111", fileParse.getReferences().get(0).getNumReference());
        Assert.assertEquals(1, fileParse.getErrors().size());
        Assert.assertNotNull(fileParse.getErrors().get(0));
        Assert.assertEquals(1, fileParse.getMetadata().getReferenceCount().intValue());
        Assert.assertEquals(1, fileParse.getMetadata().getErrorCount().intValue());
        Assert.assertEquals(2, fileParse.getMetadata().getLineCount().intValue());
    }

    @Test
    public void testTransformFileKo() throws ExtensionErrorException, ReferenceErrorException {

        final String name = "file.txt";
        final String originalName = "file.txt";
        final String contentType = "text/plain";
        final String content = "1450100040;R;45.12;27\n" +
                "1450100048;G;12.0;145";
        final MockMultipartFile file = new MockMultipartFile(name, originalName, contentType, content.getBytes());
        final Sort sort = Sort.ASC;
        final Field field = Field.NUM_REFERENCE;

        final Reference reference1 = Reference.builder()
                .numReference("1111111111")
                .type(Type.R)
                .price(1.0)
                .size(1)
                .build();

        final ReferenceErrorException referenceErrorException = new ReferenceErrorException(ErrorMessage.NUM_REFERENCE);

        when(referenceService.getReference(any())).thenReturn(reference1).thenThrow(referenceErrorException);

        FileParse fileParse = fileService.transformFile(file, sort, field);

        Assert.assertNotNull(fileParse);
        Assert.assertEquals(1, fileParse.getReferences().size());
        Assert.assertEquals("1111111111", fileParse.getReferences().get(0).getNumReference());
        Assert.assertEquals(1, fileParse.getErrors().size());
        Assert.assertEquals("1450100048;G;12.0;145", fileParse.getErrors().get(0).getValue());
        Assert.assertEquals(1, fileParse.getMetadata().getReferenceCount().intValue());
        Assert.assertEquals(1, fileParse.getMetadata().getErrorCount().intValue());
        Assert.assertEquals(2, fileParse.getMetadata().getLineCount().intValue());
    }

}

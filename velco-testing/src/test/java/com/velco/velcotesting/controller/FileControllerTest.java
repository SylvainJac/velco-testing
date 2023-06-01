package com.velco.velcotesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velco.velcotesting.dto.Field;
import com.velco.velcotesting.dto.FileParse;
import com.velco.velcotesting.dto.Sort;
import com.velco.velcotesting.service.FileService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
public class FileControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private FileController fileController;

    @Mock
    private FileService fileService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(fileController)
                .setValidator(Mockito.mock(Validator.class))
                .build();
    }

    @Test
    public void testTransformFile() throws Exception {

        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());

        when(fileService.transformFile(any(), any(), any())).thenReturn(FileParse.builder().build());

        mockMvc.perform(multipart("/files")
                        .file(file)
                        .param("sort", "ASC")
                        .param("field", "TYPE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());

        verify(fileService, times(1)).transformFile(file, Sort.ASC, Field.TYPE);

    }
}

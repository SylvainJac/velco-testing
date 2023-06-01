package com.velco.velcotesting.controller;

import com.velco.velcotesting.dto.Field;
import com.velco.velcotesting.dto.FileParse;
import com.velco.velcotesting.dto.Sort;
import com.velco.velcotesting.exception.ExtensionErrorException;
import com.velco.velcotesting.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FileParse> transformFile(@RequestPart(value = "file") MultipartFile file,
                                                   @RequestParam(value = "sort", required = false) Sort sort,
                                                   @RequestParam(value = "field", required = false) Field field) throws ExtensionErrorException {
        LOGGER.info("Transform file with parameters sort: {}, field: {}", sort, field);
        return ResponseEntity.ok(fileService.transformFile(file, sort, field));
    }
}

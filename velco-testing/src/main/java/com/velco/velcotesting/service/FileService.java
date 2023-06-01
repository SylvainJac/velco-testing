package com.velco.velcotesting.service;

import com.velco.velcotesting.dto.ErrorReference;
import com.velco.velcotesting.dto.Field;
import com.velco.velcotesting.dto.FileParse;
import com.velco.velcotesting.dto.Metadata;
import com.velco.velcotesting.dto.Reference;
import com.velco.velcotesting.dto.Sort;
import com.velco.velcotesting.exception.ExtensionErrorException;
import com.velco.velcotesting.exception.ReferenceErrorException;
import com.velco.velcotesting.util.ReferenceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);

    private static final Integer LINE_VARIABLE_LENGTH = 4;

    @Value("${velco.extension:.txt}")
    private String extension;

    @Value("${velco.sperator:;}")
    private String separator;

    private ReferenceService referenceService;

    public FileService(ReferenceService referenceService) {
        this.referenceService = referenceService;
    }

    public FileParse transformFile(MultipartFile file, final Sort sort, final Field field) throws ExtensionErrorException {

        final String name = file.getResource().getFilename();
        final String contentType = file.getContentType();

        checkFileExtension(name);

        LOGGER.info("File info, name: {}, content type: {}", name, contentType);
        List<Reference> references = new ArrayList<>();
        List<ErrorReference> errorReferences = new ArrayList<>();

        Integer lineIndex = 0;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));

            String line;
            while ((line = br.readLine()) != null) {
                String[] lineVariables = line.split(separator);

                extractReferences(references, errorReferences, lineIndex, line, lineVariables);

                lineIndex++;
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return FileParse.builder()
                .references(sortedReferences(references, sort, field))
                .errors(errorReferences)
                .metadata(getMetadata(references, errorReferences, lineIndex))
                .inputFile(name)
                .build();
    }

    private void extractReferences(List<Reference> references, List<ErrorReference> errorReferences, Integer lineIndex, String line, String[] lineVariables) {
        if (checkSizeLineVariable(lineVariables)) {
            try {
                references.add(referenceService.getReference(lineVariables));
            } catch (ReferenceErrorException e) {
                errorReferences.add(ErrorReference.builder()
                        .line(lineIndex)
                        .value(line)
                        .message(e.getErrorMessage())
                        .build());
            }
        } else {
            errorReferences.add(referenceService.getLengthLineError(lineIndex, line));
        }
    }

    private void checkFileExtension(final String fileName) throws ExtensionErrorException {
        if (fileName != null && !fileName.contains(extension)) {
            LOGGER.error("Error on filename {}, bad extension {}", fileName, extension);
            throw new ExtensionErrorException(fileName);
        }
    }
    private boolean checkSizeLineVariable(String[] lineVariables) {
        return lineVariables.length == LINE_VARIABLE_LENGTH;
    }

    private Metadata getMetadata(List<Reference> references, List<ErrorReference> errorReferences, Integer line) {
        return Metadata.builder()
                .referenceCount(references.size())
                .errorCount(errorReferences.size())
                .lineCount(line)
                .build();
    }

    private List<Reference> sortedReferences(List<Reference> references, final Sort sort, final Field field) {
        return ReferenceUtil.sortReferences(references, sort, field);
    }
}

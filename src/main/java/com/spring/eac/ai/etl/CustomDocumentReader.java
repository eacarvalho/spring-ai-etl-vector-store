package com.spring.eac.ai.etl;

import com.spring.eac.ai.property.ApplicationProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@AllArgsConstructor
@Slf4j
public class CustomDocumentReader implements DocumentReader {

    private final ApplicationProperties applicationProperties;

    private static final String VERSION_METADATA = "v1";

    @Override
    public List<Document> get() {
        log.debug("Reading documents from {}", applicationProperties.getDocumentsToLoad());

        List<Document> documents = new ArrayList<>();

        PdfDocumentReaderConfig pdfDocumentReaderConfig = PdfDocumentReaderConfig.builder()
                .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                        .withNumberOfBottomTextLinesToDelete(3)
                        .withNumberOfTopPagesToSkipBeforeDelete(1)
                        .build())
                .withPagesPerDocument(1)
                .build();

        applicationProperties.getDocumentsToLoad().forEach(document -> {
            log.debug("Loading document: {} ", document.getFilename());

            String version = extractVersion(document);
            PagePdfDocumentReader pagePdfDocumentReader = new PagePdfDocumentReader(document, pdfDocumentReaderConfig);

            pagePdfDocumentReader.get().forEach(pdf -> {
                // Add metadata: version and file name
                pdf.getMetadata().put("version", version);
                pdf.getMetadata().put("fileName", normalizeContent(document.getFilename()));

                documents.add(pdf);

                log.debug("Read page document from file: {}", document.getFilename());
            });
        });

        log.debug("Finished to reading documents from {}", applicationProperties.getDocumentsToLoad());

        return documents;
    }

    public static String normalizeContent(String input) {
        if (input == null) {
            return null;
        }
        // Convert to lowercase and remove all space characters
        return input.toLowerCase().replaceAll("\\s+", "");
    }

    public static String extractVersion(Resource resource) {
        try {
            String path = resource.getFile().getPath();
            Matcher matcher = Pattern.compile("/(v\\d+)").matcher(path);
            if (matcher.find()) {
                return matcher.group(1); // returns "v1" without the slash
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to get file path from resource", e);
        }
        return "v0"; // default version if none found
    }
}
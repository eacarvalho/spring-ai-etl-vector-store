package com.spring.eac.ai.service;

import com.spring.eac.ai.etl.CustomDocumentReader;
import com.spring.eac.ai.etl.CustomDocumentTransformer;
import com.spring.eac.ai.etl.CustomDocumentWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class EtlPipelineService {

    private final CustomDocumentReader documentReader;
    private final CustomDocumentTransformer documentTransformer;
    private final CustomDocumentWriter documentWriter;

    public void run() {
//        List<Document> splitDocuments = documentTransformer.apply(documentReader.get());
//
//        for (Document doc : splitDocuments) {
//            log.info("Chunk: {}", doc.getFormattedContent());
//            log.info("Metadata: {}", doc.getMetadata());
//        }

        documentWriter.accept(documentTransformer.apply(documentReader.get()));
    }
}
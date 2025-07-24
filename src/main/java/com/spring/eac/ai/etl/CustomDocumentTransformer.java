package com.spring.eac.ai.etl;

import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentTransformer;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomDocumentTransformer implements DocumentTransformer {

    @Override
    public List<Document> apply(List<Document> documents) {
        TextSplitter textSplitter = TokenTextSplitter.builder()
                .withChunkSize(7000)
                .withMinChunkSizeChars(500)
                .withMinChunkLengthToEmbed(5)
                .withMaxNumChunks(10000)
                .withKeepSeparator(true)
                .build();

        return textSplitter.apply(documents);
    }
}

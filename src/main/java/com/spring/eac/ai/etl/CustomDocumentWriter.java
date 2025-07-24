package com.spring.eac.ai.etl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentWriter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
@Slf4j
public class CustomDocumentWriter implements DocumentWriter {

    private final VectorStore vectorStore;

    @Override
    public void accept(List<Document> documents) {
        Set<String> existingKeys = findExistingDocumentKeys(documents);
        List<Document> documentsToAdd = new ArrayList<>();

        for (Document document : documents) {
            String version = document.getMetadata().get("version").toString();
            String fileName = document.getMetadata().get("fileName").toString();
            String key = createKey(fileName, version);

            if (existingKeys.contains(key)) {
                log.info("Skipping chunk for file '{}' with version '{}' because it already exists in vector store", fileName, version);
                continue;
            }

            documentsToAdd.add(document);

            log.info("Written chunk for file '{}' with version '{}' to vector store", fileName, version);
        }

        // Write chunk to the vector store because it doesn't exist yet
        vectorStore.add(documentsToAdd);

        log.info("The number of documents added to vector store: {}", documentsToAdd.size());
    }

    /**
     * Returns a set of keys (fileName::version) for documents that already exist in the vector store.
     */
    private Set<String> findExistingDocumentKeys(List<Document> documents) {
        Set<String> existingKeys = new HashSet<>();

        // Use a temporary set to avoid duplicate queries in case documents have repeated keys
        Set<String> checkedKeys = new HashSet<>();

        for (Document document : documents) {
            String version = document.getMetadata().get("version").toString();
            String fileName = document.getMetadata().get("fileName").toString();
            String key = createKey(fileName, version);

            if (checkedKeys.contains(key)) {
                continue; // Already checked existence for this key
            }

            checkedKeys.add(key);

            SearchRequest searchRequest = SearchRequest.builder()
                    .query("a") // non-empty query to avoid API issues
                    .filterExpression("version == '" + version + "' && fileName == '" + fileName + "'")
                    .topK(1)
                    .build();

            boolean exists = !vectorStore.similaritySearch(searchRequest).isEmpty();
            if (exists) {
                existingKeys.add(key);
            }
        }

        return existingKeys;
    }

    /**
     * Creates a unique key combining fileName and version.
     */
    private String createKey(String fileName, String version) {
        return fileName + "::" + version;
    }
}

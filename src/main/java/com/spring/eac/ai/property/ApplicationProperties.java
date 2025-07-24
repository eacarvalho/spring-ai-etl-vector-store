package com.spring.eac.ai.property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "etl.vector-store")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationProperties {

    @Builder.Default
    private List<Resource> documentsToLoad = new ArrayList<>();
}
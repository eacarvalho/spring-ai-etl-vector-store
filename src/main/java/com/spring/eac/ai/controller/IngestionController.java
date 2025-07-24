package com.spring.eac.ai.controller;

import com.spring.eac.ai.service.EtlPipelineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/etl")
@RequiredArgsConstructor
@Slf4j
public class IngestionController {

    private final EtlPipelineService etlPipelineService;

    @GetMapping("run-ingestion")
    public ResponseEntity<?> run(){
        etlPipelineService.run();
        return ResponseEntity.accepted().build();
    }
}
package com.example.demo.controller;

import com.example.demo.model.CodeAnalysis;
import com.example.demo.model.FileMetrics;
import com.example.demo.service.AnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")  // Allow requests from Python app
public class AnalyzerApiController {

    @Autowired
    private AnalyzerService analyzerService;

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Java Code Analyzer Microservice");
        response.put("timestamp", new Date().toString());
        return ResponseEntity.ok(response);
    }

    /**
     * Analyze Java file - returns metrics only (no DB save)
     */
    @PostMapping("/analyze")
    public ResponseEntity<?> analyzeFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "File is empty"));
            }

            String fileName = file.getOriginalFilename();
            String content = new String(file.getBytes(), StandardCharsets.UTF_8);
            
            FileMetrics metrics = analyzerService.analyze(fileName, content);
            
            return ResponseEntity.ok(metrics);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to read file: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Analysis failed: " + e.getMessage()));
        }
    }

    /**
     * Analyze Java file and save to database
     */
    @PostMapping("/analyze-and-save")
    public ResponseEntity<?> analyzeAndSaveFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Integer userId,
            @RequestParam(value = "projectId", required = false) Integer projectId) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "File is empty"));
            }

            String fileName = file.getOriginalFilename();
            String content = new String(file.getBytes(), StandardCharsets.UTF_8);
            
            CodeAnalysis analysis = analyzerService.analyzeAndSave(fileName, content, userId, projectId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "File analyzed and saved successfully");
            response.put("analysisId", analysis.getId());
            response.put("fileName", analysis.getFileName());
            response.put("userId", analysis.getUserId());
            response.put("projectId", analysis.getProjectId());
            response.put("metrics", Map.of(
                "totalLines", analysis.getTotalLines(),
                "blankLines", analysis.getBlankLines(),
                "commentLines", analysis.getCommentLines(),
                "cyclomaticComplexity", analysis.getCyclomaticComplexity(),
                "complexityPercentage", analysis.getComplexityPercentage()
            ));
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to read file: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Analysis failed: " + e.getMessage()));
        }
    }

    /**
     * Analyze multiple Java files and save to database
     */
    @PostMapping("/analyze-batch")
    public ResponseEntity<?> analyzeBatchFiles(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("userId") Integer userId,
            @RequestParam(value = "projectId", required = false) Integer projectId) {
        
        List<Map<String, Object>> results = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        
        for (MultipartFile file : files) {
            try {
                if (file.isEmpty()) {
                    errors.add(file.getOriginalFilename() + ": File is empty");
                    continue;
                }

                String fileName = file.getOriginalFilename();
                String content = new String(file.getBytes(), StandardCharsets.UTF_8);
                
                CodeAnalysis analysis = analyzerService.analyzeAndSave(fileName, content, userId, projectId);
                
                Map<String, Object> result = new HashMap<>();
                result.put("fileName", analysis.getFileName());
                result.put("analysisId", analysis.getId());
                result.put("success", true);
                results.add(result);
                
            } catch (Exception e) {
                errors.add(file.getOriginalFilename() + ": " + e.getMessage());
            }
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("totalFiles", files.length);
        response.put("successCount", results.size());
        response.put("errorCount", errors.size());
        response.put("results", results);
        response.put("errors", errors);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get analysis results for a user
     */
    @GetMapping("/analyses/user/{userId}")
    public ResponseEntity<?> getAnalysesByUser(@PathVariable Integer userId) {
        try {
            List<CodeAnalysis> analyses = analyzerService.getAnalysesByUser(userId);
            return ResponseEntity.ok(analyses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Get analysis results for a project
     */
    @GetMapping("/analyses/project/{projectId}")
    public ResponseEntity<?> getAnalysesByProject(@PathVariable Integer projectId) {
        try {
            List<CodeAnalysis> analyses = analyzerService.getAnalysesByProject(projectId);
            return ResponseEntity.ok(analyses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}

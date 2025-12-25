package com.example.demo.service;

import com.example.demo.model.CodeAnalysis;
import com.example.demo.model.FileMetrics;
import com.example.demo.repository.CodeAnalysisRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AnalyzerService {

    @Autowired
    private CodeAnalysisRepository codeAnalysisRepository;
    
    @Autowired
    private ObjectMapper objectMapper;  // For JSON serialization

    /**
     * Analyze Java file and return metrics (without saving to DB)
     */
    public FileMetrics analyze(String fileName, String content) {

        List<String> lines = Arrays.asList(content.split("\n"));

        int totalLines = lines.size();
        int blankLines = 0;
        int commentLines = 0;
        int importCount = 0;
        int variableCount = 0;
        int tryCount = 0, catchCount = 0, finallyCount = 0;
        int inheritanceCount = 0;
        int encapsulationCount = 0;
        int overridingCount = 0;
        int overloadingCount = 0;

        int cyclomatic = 1;  

        for (String line : lines) {
            String trimmed = line.trim();

            if (trimmed.isBlank()) blankLines++;

            if (trimmed.startsWith("//") || trimmed.startsWith("/*") || trimmed.endsWith("*/"))
                commentLines++;

            if (trimmed.startsWith("import")) importCount++;

            if (trimmed.matches(".*(String|int|double|float|boolean|char|long|var).*=.*;"))
                variableCount++;

            if (trimmed.contains("extends") || trimmed.contains("implements"))
                inheritanceCount++;

            if (trimmed.contains("private") || trimmed.contains("public") || trimmed.contains("protected"))
                encapsulationCount++;

            if (trimmed.contains("@Override"))
                overridingCount++;

            if (trimmed.matches(".*\\(.*\\).*\\{.*"))
                overloadingCount++;

            if (trimmed.contains("if") || trimmed.contains("for") || trimmed.contains("while") ||
                trimmed.contains("case") || trimmed.contains("&&") || trimmed.contains("||"))
                cyclomatic++;

            if (trimmed.contains("try")) tryCount++;
            if (trimmed.contains("catch")) catchCount++;
            if (trimmed.contains("finally")) finallyCount++;
        }

        // *Complexity Percentage Example: (Cyclomatic / Total Lines) * 100*
        double complexityPercentage = ((double) cyclomatic / totalLines) * 100;

        // Character wise analysis
        int vowels = 0, consonants = 0, special = 0;
        for (char c : content.toCharArray()) {
            if (Character.isAlphabetic(c)) {
                if ("AEIOUaeiou".indexOf(c) >= 0) vowels++;
                else consonants++;
            } else if (!Character.isWhitespace(c) && !Character.isDigit(c)) {
                special++;
            }
        }

        return new FileMetrics(
                fileName,
                totalLines,
                blankLines,
                commentLines,
                importCount,
                variableCount,
                inheritanceCount,
                encapsulationCount,
                overridingCount,
                overloadingCount,
                cyclomatic,
                complexityPercentage,
                tryCount,
                catchCount,
                finallyCount,
                vowels,
                consonants,
                special,
                content.length()
        );
    }

    /**
     * Analyze Java file and save results to database
     */
    public CodeAnalysis analyzeAndSave(String fileName, String content, Integer userId, Integer projectId) {
        // First, analyze the file
        FileMetrics metrics = analyze(fileName, content);
        
        // Create CodeAnalysis entity
        CodeAnalysis analysis = new CodeAnalysis(userId, projectId, fileName, metrics);
        
        // Create JSON data with full analysis details
        try {
            Map<String, Object> analysisJson = new HashMap<>();
            analysisJson.put("fileName", fileName);
            analysisJson.put("userId", userId);
            analysisJson.put("projectId", projectId);
            analysisJson.put("fileType", "java");
            
            // Add all metrics
            Map<String, Object> metricsMap = new HashMap<>();
            metricsMap.put("totalLines", metrics.getTotalLines());
            metricsMap.put("blankLines", metrics.getBlankLines());
            metricsMap.put("commentLines", metrics.getCommentLines());
            metricsMap.put("importCount", metrics.getImportCount());
            metricsMap.put("variableCount", metrics.getVariableCount());
            metricsMap.put("inheritanceCount", metrics.getInheritanceCount());
            metricsMap.put("encapsulationCount", metrics.getEncapsulationCount());
            metricsMap.put("overrideMethods", metrics.getOverrideMethods());
            metricsMap.put("overloadedMethods", metrics.getOverloadedMethods());
            metricsMap.put("cyclomaticComplexity", metrics.getCyclomaticComplexity());
            metricsMap.put("complexityPercentage", metrics.getComplexityPercentage());
            metricsMap.put("tryCount", metrics.getTryCount());
            metricsMap.put("catchCount", metrics.getCatchCount());
            metricsMap.put("finallyCount", metrics.getFinallyCount());
            metricsMap.put("vowels", metrics.getVowels());
            metricsMap.put("consonants", metrics.getConsonants());
            metricsMap.put("specialChars", metrics.getSpecialChars());
            metricsMap.put("totalCharacters", metrics.getTotalCharacters());
            
            analysisJson.put("metrics", metricsMap);
            analysisJson.put("timestamp", System.currentTimeMillis());
            
            // Convert to JSON string
            String jsonData = objectMapper.writeValueAsString(analysisJson);
            analysis.setAnalysisData(jsonData);
            
        } catch (Exception e) {
            System.err.println("Failed to create JSON data: " + e.getMessage());
            // Continue without JSON data
        }
        
        return codeAnalysisRepository.save(analysis);
    }

    /**
     * Get all analyses for a user
     */
    public List<CodeAnalysis> getAnalysesByUser(Integer userId) {
        return codeAnalysisRepository.findByUserId(userId);
    }

    /**
     * Get all analyses for a project
     */
    public List<CodeAnalysis> getAnalysesByProject(Integer projectId) {
        return codeAnalysisRepository.findByProjectId(projectId);
    }
}

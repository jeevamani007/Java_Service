package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "code_analysis")
public class CodeAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_type")
    private String fileType = "java";

    // Analysis metrics
    @Column(name = "total_lines")
    private Integer totalLines = 0;

    @Column(name = "blank_lines")
    private Integer blankLines = 0;

    @Column(name = "comment_lines")
    private Integer commentLines = 0;

    @Column(name = "import_count")
    private Integer importCount = 0;

    @Column(name = "variable_count")
    private Integer variableCount = 0;

    @Column(name = "inheritance_count")
    private Integer inheritanceCount = 0;

    @Column(name = "encapsulation_count")
    private Integer encapsulationCount = 0;

    @Column(name = "override_methods")
    private Integer overrideMethods = 0;

    @Column(name = "overloaded_methods")
    private Integer overloadedMethods = 0;

    @Column(name = "cyclomatic_complexity")
    private Integer cyclomaticComplexity = 0;

    @Column(name = "complexity_percentage")
    private Double complexityPercentage = 0.0;

    // Exception handling
    @Column(name = "try_count")
    private Integer tryCount = 0;

    @Column(name = "catch_count")
    private Integer catchCount = 0;

    @Column(name = "finally_count")
    private Integer finallyCount = 0;

    // Character analysis
    @Column(name = "vowels")
    private Integer vowels = 0;

    @Column(name = "consonants")
    private Integer consonants = 0;

    @Column(name = "special_chars")
    private Integer specialChars = 0;

    @Column(name = "total_characters")
    private Integer totalCharacters = 0;

    @Column(name = "analysis_data", columnDefinition = "jsonb")
    private String analysisData;  // Store full analysis as JSON

    @Column(name = "analyzed_at")
    private LocalDateTime analyzedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.analyzedAt = LocalDateTime.now();
    }

    // Constructors
    public CodeAnalysis() {}

    public CodeAnalysis(Integer userId, Integer projectId, String fileName, FileMetrics metrics) {
        this.userId = userId;
        this.projectId = projectId;
        this.fileName = fileName;
        this.fileType = "java";
        this.totalLines = metrics.getTotalLines();
        this.blankLines = metrics.getBlankLines();
        this.commentLines = metrics.getCommentLines();
        this.importCount = metrics.getImportCount();
        this.variableCount = metrics.getVariableCount();
        this.inheritanceCount = metrics.getInheritanceCount();
        this.encapsulationCount = metrics.getEncapsulationCount();
        this.overrideMethods = metrics.getOverrideMethods();
        this.overloadedMethods = metrics.getOverloadedMethods();
        this.cyclomaticComplexity = metrics.getCyclomaticComplexity();
        this.complexityPercentage = metrics.getComplexityPercentage();
        this.tryCount = metrics.getTryCount();
        this.catchCount = metrics.getCatchCount();
        this.finallyCount = metrics.getFinallyCount();
        this.vowels = metrics.getVowels();
        this.consonants = metrics.getConsonants();
        this.specialChars = metrics.getSpecialChars();
        this.totalCharacters = metrics.getTotalCharacters();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public Integer getProjectId() { return projectId; }
    public void setProjectId(Integer projectId) { this.projectId = projectId; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }

    public Integer getTotalLines() { return totalLines; }
    public void setTotalLines(Integer totalLines) { this.totalLines = totalLines; }

    public Integer getBlankLines() { return blankLines; }
    public void setBlankLines(Integer blankLines) { this.blankLines = blankLines; }

    public Integer getCommentLines() { return commentLines; }
    public void setCommentLines(Integer commentLines) { this.commentLines = commentLines; }

    public Integer getImportCount() { return importCount; }
    public void setImportCount(Integer importCount) { this.importCount = importCount; }

    public Integer getVariableCount() { return variableCount; }
    public void setVariableCount(Integer variableCount) { this.variableCount = variableCount; }

    public Integer getInheritanceCount() { return inheritanceCount; }
    public void setInheritanceCount(Integer inheritanceCount) { this.inheritanceCount = inheritanceCount; }

    public Integer getEncapsulationCount() { return encapsulationCount; }
    public void setEncapsulationCount(Integer encapsulationCount) { this.encapsulationCount = encapsulationCount; }

    public Integer getOverrideMethods() { return overrideMethods; }
    public void setOverrideMethods(Integer overrideMethods) { this.overrideMethods = overrideMethods; }

    public Integer getOverloadedMethods() { return overloadedMethods; }
    public void setOverloadedMethods(Integer overloadedMethods) { this.overloadedMethods = overloadedMethods; }

    public Integer getCyclomaticComplexity() { return cyclomaticComplexity; }
    public void setCyclomaticComplexity(Integer cyclomaticComplexity) { this.cyclomaticComplexity = cyclomaticComplexity; }

    public Double getComplexityPercentage() { return complexityPercentage; }
    public void setComplexityPercentage(Double complexityPercentage) { this.complexityPercentage = complexityPercentage; }

    public Integer getTryCount() { return tryCount; }
    public void setTryCount(Integer tryCount) { this.tryCount = tryCount; }

    public Integer getCatchCount() { return catchCount; }
    public void setCatchCount(Integer catchCount) { this.catchCount = catchCount; }

    public Integer getFinallyCount() { return finallyCount; }
    public void setFinallyCount(Integer finallyCount) { this.finallyCount = finallyCount; }

    public Integer getVowels() { return vowels; }
    public void setVowels(Integer vowels) { this.vowels = vowels; }

    public Integer getConsonants() { return consonants; }
    public void setConsonants(Integer consonants) { this.consonants = consonants; }

    public Integer getSpecialChars() { return specialChars; }
    public void setSpecialChars(Integer specialChars) { this.specialChars = specialChars; }

    public Integer getTotalCharacters() { return totalCharacters; }
    public void setTotalCharacters(Integer totalCharacters) { this.totalCharacters = totalCharacters; }

    public String getAnalysisData() { return analysisData; }
    public void setAnalysisData(String analysisData) { this.analysisData = analysisData; }

    public LocalDateTime getAnalyzedAt() { return analyzedAt; }
    public void setAnalyzedAt(LocalDateTime analyzedAt) { this.analyzedAt = analyzedAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

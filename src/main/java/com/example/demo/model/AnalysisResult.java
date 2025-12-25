package com.example.demo.model;



import java.util.List;

public class AnalysisResult {
    private List<FileMetrics> results;

    public AnalysisResult(List<FileMetrics> results) {
        this.results = results;
    }

    public List<FileMetrics> getResults() {
        return results;
    }
}


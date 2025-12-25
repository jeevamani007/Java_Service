package com.example.demo.repository;

import com.example.demo.model.CodeAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeAnalysisRepository extends JpaRepository<CodeAnalysis, Long> {
    
    // Find all analyses by user
    List<CodeAnalysis> findByUserId(Integer userId);
    
    // Find all analyses by project
    List<CodeAnalysis> findByProjectId(Integer projectId);
    
    // Find analyses by user and project
    List<CodeAnalysis> findByUserIdAndProjectId(Integer userId, Integer projectId);
    
    // Find by filename
    List<CodeAnalysis> findByFileName(String fileName);
}

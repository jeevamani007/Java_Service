package com.example.demo.controller;

import com.example.demo.model.FileMetrics;
import com.example.demo.service.AnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for Thymeleaf-based file upload UI (Optional)
 * Main microservice API is in AnalyzerApiController
 */
@Controller
public class MainController {

    @Autowired
    private AnalyzerService analyzerService;

    // FRONT PAGE - Redirect to Python app for authentication
    @GetMapping("/")
    public String frontPage() {
        return "redirect:http://127.0.0.1:9000/login";
    }

    // FILE UPLOAD
    @PostMapping("/upload")
    public String uploadFiles(@RequestParam("files") MultipartFile[] files, Model model) throws IOException {

        if (files == null || files.length == 0) {
            model.addAttribute("message", "Please upload at least one file");
            return "upload";
        }

        List<FileMetrics> results = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String content = new String(file.getBytes());
                FileMetrics result = analyzerService.analyze(file.getOriginalFilename(), content);
                results.add(result);
            }
        }

        model.addAttribute("results", results);

        return "result";
    }

    @GetMapping("/upload")
public String showUploadPage() {
    return "upload"; // Thymeleaf template name: upload.html
}
}

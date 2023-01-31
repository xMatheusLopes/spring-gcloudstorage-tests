package com.matheus.gcloudupload.controllers;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.matheus.gcloudupload.interfaces.StorageServiceImpl;
import com.matheus.gcloudupload.services.GCloudStorageService;

@Controller
public class StorageController {

    private final StorageServiceImpl storageService;
    
    @Autowired
    public StorageController(GCloudStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<HashMap<String, String>> upload(@RequestParam("file") MultipartFile file) throws IOException {
        HashMap<String, String> response = new HashMap<>();
        response.put("url", storageService.store(file));
        
        return ResponseEntity.ok(response);
    }
}

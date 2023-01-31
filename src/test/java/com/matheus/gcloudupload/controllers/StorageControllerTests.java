package com.matheus.gcloudupload.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.matheus.gcloudupload.services.GCloudStorageService;

@SpringBootTest
public class StorageControllerTests {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private GCloudStorageService storageService;
    
    @Test
    @DisplayName("Test if storage post upload is working")
    void upload() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
            "file", 
            "hello.txt", 
            MediaType.TEXT_PLAIN_VALUE, 
            "Hello, World!".getBytes()
        );


        when(this.storageService.store(
            any(MultipartFile.class))
        ).thenReturn("http://success");

        MockMvc mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .build();

        mockMvc
            .perform(multipart("/upload")
            .file(file))
            .andExpect(status().isOk())
            .andExpect(content().string("{\"url\":\"http://success\"}"));
    }
}

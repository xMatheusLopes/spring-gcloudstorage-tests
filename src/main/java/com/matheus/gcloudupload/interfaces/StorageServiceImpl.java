package com.matheus.gcloudupload.interfaces;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface StorageServiceImpl {
    String store(MultipartFile file) throws IOException;
}

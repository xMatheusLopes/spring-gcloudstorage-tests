package com.matheus.gcloudupload.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.matheus.gcloudupload.interfaces.StorageServiceImpl;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Storage.BlobTargetOption;
import com.google.cloud.storage.Storage.PredefinedAcl;
import com.matheus.gcloudupload.services.GCloudStorageService;

@Service
public class GCloudStorageService implements StorageServiceImpl {

    private static final String BUCKET_NAME = "java-storage";

    private Storage storage = StorageOptions.getDefaultInstance().getService(); 

    @Override
    public String store(MultipartFile file) throws IOException {
        // Credits to https://gist.github.com/nogsantos/b3f4c0f7cd779b345a12d586ef552bdf
        try {			
			BlobInfo blobInfo = storage.create(
				BlobInfo.newBuilder(BUCKET_NAME, file.getOriginalFilename()).build(),
				file.getBytes(),
				BlobTargetOption.predefinedAcl(PredefinedAcl.PUBLIC_READ) 
			);
			return blobInfo.getMediaLink();
		} catch (IllegalStateException e){
			throw new RuntimeException(e);
		}
	}
}

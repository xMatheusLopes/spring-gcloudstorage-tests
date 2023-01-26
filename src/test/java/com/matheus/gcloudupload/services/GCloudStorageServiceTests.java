package com.matheus.gcloudupload.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HexFormat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobTargetOption;

@SpringBootTest
public class GCloudStorageServiceTests {

    @Autowired
    GCloudStorageService gCloudStorageService;

    Storage mockedStorage = mock(Storage.class);

    @Test
    void storeSuccess() throws IOException {
        Blob mockedBlob = mock(Blob.class);
        MultipartFile mockedMultipartFile = mock(MultipartFile.class);

        ReflectionTestUtils.setField(gCloudStorageService, "storage", this.mockedStorage);

        when(mockedMultipartFile.getOriginalFilename()).thenReturn("FileName");
        when(mockedMultipartFile.getBytes()).thenReturn(HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d"));

        when(mockedBlob.getMediaLink()).thenReturn("http://successfull-test/");

        when(this.mockedStorage.create(
            any(BlobInfo.class),
            any(byte[].class),
            any(BlobTargetOption.class))
        ).thenReturn(mockedBlob);

        Assertions.assertEquals("http://successfull-test/", gCloudStorageService.store(mockedMultipartFile));
    }

    @Test
    void storeFail() throws IOException {
        Blob mockedBlob = mock(Blob.class);
        MultipartFile mockedMultipartFile = mock(MultipartFile.class);

        ReflectionTestUtils.setField(gCloudStorageService, "storage", this.mockedStorage);

        when(mockedMultipartFile.getOriginalFilename()).thenReturn("FileName");
        when(mockedMultipartFile.getBytes()).thenReturn(HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d"));

        when(mockedBlob.getMediaLink()).thenReturn("http://successfull-test/");

        when(this.mockedStorage.create(
            any(BlobInfo.class),
            any(byte[].class),
            any(BlobTargetOption.class))
        ).thenThrow(IllegalStateException.class);

        Assertions.assertThrows(RuntimeException.class, () -> gCloudStorageService.store(mockedMultipartFile));
    }
}

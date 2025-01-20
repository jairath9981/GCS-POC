package com.jairath.gcs.poc.impl.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Blob;
import com.jairath.gcs.poc.api.contract.GCSCRUD;
import com.jairath.gcs.poc.api.exception.types.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;


@Service
@Slf4j
public class GCSCRUDServiceImpl implements GCSCRUD {

    /*

     */
    @Autowired
    private Storage storage;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public void uploadJson(String bucketName, String destinationPath) throws Exception {

        // Step 1: Create Dynamic JSON Data
        Map<String, Object> dynamicData = new HashMap<>();
        dynamicData.put("id", UUID.randomUUID().toString());
        dynamicData.put("timestamp", System.currentTimeMillis());
        dynamicData.put("message", "This is a dynamically created JSON file_1 uploaded directly to GCS.");

        // Step 2: Convert JSON Data to String
        String jsonContent = objectMapper.writeValueAsString(dynamicData);


        // Step 3: Upload JSON String to GCS
        BlobId blobId = BlobId.of(bucketName, destinationPath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("application/json").build();
        storage.create(blobInfo, jsonContent.getBytes(StandardCharsets.UTF_8));

        /* Working code to store
        BlobId blobId = BlobId.of(bucketName, "dummyFile.txt");
        ClassLoader classLoader = GCSCRUDServiceImpl.class.getClassLoader();

        // Get the file as a File object
        File file = new File(classLoader.getResource("dummyFile.txt").getFile());
        // Convert the file to a byte array
        Path filePath = file.toPath();
        byte[] fileByteaArr = Files.readAllBytes(filePath);
        storage.create(blobInfo, fileByteaArr);
        */


        log.info("Dynamic JSON uploaded directly to: " + bucketName + "/" + destinationPath);
    }



    @Override
    public String fetchFile(String bucketName, String fileName) {
        Blob blob = storage.get(bucketName, fileName);
        if (blob == null) {
            throw new CustomException("File not found: " + fileName, HttpStatus.NOT_FOUND);
        }
        return new String(blob.getContent(), StandardCharsets.UTF_8);
    }
}

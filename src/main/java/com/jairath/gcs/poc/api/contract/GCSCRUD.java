package com.jairath.gcs.poc.api.contract;


public interface GCSCRUD {

    void uploadJson(String bucketName, String destinationPath) throws Exception;

    String fetchFile(String bucketName, String fileName);
}

package com.jairath.gcs.poc.controller;


import com.jairath.gcs.poc.api.contract.GCSCRUD;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.io.File;

import static com.jairath.gcs.poc.common.util.CommonHelper.getCurrentDateInGivenPattern;
import static com.jairath.gcs.poc.common.util.Constants.DATE_TIME_PATTERN_dd_MMM_yyyy_HH_mm_ss;


@RestController
@Scope("prototype")
@Slf4j
@Validated
@CrossOrigin
@RequestMapping(value = "/gcs/v1")
@Api
public class GCSCRUDController {

    @Autowired
    GCSCRUD gcscrud;


    @GetMapping("/uploadFile")
    public String uploadDirectJson() {
        try {
            String bucketName = "jairath_gcs_poc";

            String currentDateAndTime = getCurrentDateInGivenPattern(DATE_TIME_PATTERN_dd_MMM_yyyy_HH_mm_ss);
            String destinationPath = "request"+ File.separator + currentDateAndTime + ".json";

            // Call the service to upload JSON directly
            gcscrud.uploadJson(bucketName, destinationPath);

            return "Dynamic JSON uploaded directly to GCS successfully! dummy";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error uploading JSON to GCS: " + e.getMessage();
        }
    }


    /**
     *  dummyFile.txt
     *
     *  request/21_Jan_2025_01_10_13.json
     */
    @GetMapping("/downloadFile")
    public String downloadDirectJson(@RequestParam String fileName) {
        try {
            String bucketName = "jairath_gcs_poc";
            // Call the service to upload JSON directly
            return gcscrud.fetchFile(bucketName, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error uploading JSON to GCS: " + e.getMessage();
        }
    }
}

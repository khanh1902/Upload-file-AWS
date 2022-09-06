package com.laptrinhjava.UpLoadFileWithAWS.controller;

import com.laptrinhjava.UpLoadFileWithAWS.entity.ResponseObject;
import com.laptrinhjava.UpLoadFileWithAWS.entity.Storage;
import com.laptrinhjava.UpLoadFileWithAWS.service.AmazonClient;
import com.laptrinhjava.UpLoadFileWithAWS.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

@RestController
@RequestMapping(path = "api/aws")
public class BucketController {
    @Autowired
    private AmazonClient amazonClient;

    @Autowired
    private StorageService storageService;

    @PostMapping("/upload")
    public String uploadFileV1(@RequestParam("file") MultipartFile file) {
        return amazonClient.uploadFile(file);
    }

    @PostMapping("/upload/db")
    public ResponseEntity<ResponseObject> uploadFileV2(@RequestParam("file") MultipartFile file) {
        String fileName = amazonClient.uploadFile(file);
        Storage storage = new Storage(fileName);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Save successfully!", storageService.save(storage))
        );
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<ResponseObject> getUrl(@PathVariable String fileName) {
        URL s3Url = amazonClient.getUrl(fileName);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Get successfully", s3Url)
        );
    }

    @DeleteMapping("/deletebyfilename/{fileName:.+}")
    public ResponseEntity<ResponseObject> deleteFile(@PathVariable String fileName) {
        amazonClient.deleteFile(fileName);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Delete successfully!", "")
        );
    }

    // delete by id
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteFileById(@PathVariable Long id) {
        Storage storage = storageService.findById(id);
        if(storage != null){
            amazonClient.deleteFile(storage.getFileName());
            storageService.delete(storage.getFileName());
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Delete successfully!", "")
            );
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("FAILED", "Delete failed!", "")
            );

    }

    // find by id
    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Storage findById = storageService.findById(id);
        if (findById != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Delete successfully!", findById)
            );
        } else
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("Failed", "Not found!", "")
            );
    }
}


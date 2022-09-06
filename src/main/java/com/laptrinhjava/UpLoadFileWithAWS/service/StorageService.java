package com.laptrinhjava.UpLoadFileWithAWS.service;

import com.laptrinhjava.UpLoadFileWithAWS.entity.Storage;

import java.util.Optional;

public interface StorageService {
    Storage save(Storage storage);
    Storage findById(Long id);
    Storage findByFileName(String fileName);
    void delete(String fileName);
}

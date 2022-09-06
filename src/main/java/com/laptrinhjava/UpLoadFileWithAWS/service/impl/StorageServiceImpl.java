package com.laptrinhjava.UpLoadFileWithAWS.service.impl;

import com.laptrinhjava.UpLoadFileWithAWS.entity.Storage;
import com.laptrinhjava.UpLoadFileWithAWS.repository.StorageRepository;
import com.laptrinhjava.UpLoadFileWithAWS.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    private StorageRepository storageRepository;

    @Override
    public Storage save(Storage storage) {
        return storageRepository.save(storage);
    }

    @Override
    public Storage findById(Long id) {
        return storageRepository.findStorageById(id);
    }

    @Override
    public Storage findByFileName(String fileName) {
        return storageRepository.findStorageByFileName(fileName);
    }

    @Override
    public void delete(String fileName) {
        Storage find = storageRepository.findStorageByFileName(fileName);
        if(find != null){
            storageRepository.deleteById(find.getId());
        }
    }
}

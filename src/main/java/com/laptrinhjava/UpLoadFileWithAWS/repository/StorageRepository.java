package com.laptrinhjava.UpLoadFileWithAWS.repository;

import com.laptrinhjava.UpLoadFileWithAWS.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {
    Storage findStorageById(Long id);
    Storage findStorageByFileName(String fileName);
}

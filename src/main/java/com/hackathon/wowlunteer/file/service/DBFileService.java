package com.hackathon.wowlunteer.file.service;

import com.hackathon.wowlunteer.file.persistence.repository.DBFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBFileService {

    private DBFileRepository dbFileRepository;

    @Autowired
    public DBFileService(DBFileRepository dbFileRepository) {
        this.dbFileRepository = dbFileRepository;
    }

}

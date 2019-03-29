package com.hackathon.wowlunteer.file.service;

import com.hackathon.wowlunteer.file.exception.FileNotFoundException;
import com.hackathon.wowlunteer.file.exception.FileStorageException;
import com.hackathon.wowlunteer.file.exception.WrongExtensionException;
import com.hackathon.wowlunteer.file.persitence.model.DBFile;
import com.hackathon.wowlunteer.file.persitence.repository.DBFileRepository;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DBFileStorageService {

    private DBFileRepository dbFileRepository;

    @Autowired
    public DBFileStorageService(DBFileRepository dbFileRepository) {
        this.dbFileRepository = dbFileRepository;
    }

    public DBFile storeFile(MultipartFile file) throws FileStorageException, WrongExtensionException, IOException, FileUploadBase.FileSizeLimitExceededException {

        if(file.getSize() > 1000000) {
            throw new FileUploadBase.FileSizeLimitExceededException("File is too large", file.getSize(), 1000000L);
        }
        String fileExtentions = ".jpg,.pdf";
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        int lastIndex = fileName.lastIndexOf('.');
        String substring = fileName.substring(lastIndex);

        if(fileExtentions.contains(substring)) {
            try {
                if (fileName.contains("..")) {
                    throw new FileStorageException("Filename contains invalid path sequence " + fileName);
                }

                DBFile DBFile = new DBFile(fileName, file.getContentType(), file.getBytes());

                return dbFileRepository.save(DBFile);

            } catch (Exception ex) {

                throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
            }

        } else {
            throw new WrongExtensionException("File is not supported for upload!");
        }
    }

    public DBFile getFile(String fileId) throws FileNotFoundException {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
    }
}

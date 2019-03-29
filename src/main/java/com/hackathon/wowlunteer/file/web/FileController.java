package com.hackathon.wowlunteer.file.web;

import com.hackathon.wowlunteer.file.exception.FileStorageException;
import com.hackathon.wowlunteer.file.exception.WrongExtensionException;
import com.hackathon.wowlunteer.file.persitence.model.DBFile;
import com.hackathon.wowlunteer.file.service.DBFileStorageService;
import com.hackathon.wowlunteer.file.utility.ErrorResponse;
import com.hackathon.wowlunteer.file.utility.UploadFileResponse;
import com.hackathon.wowlunteer.user.persistence.model.ApplicationUser;
import com.hackathon.wowlunteer.user.service.ApplicationUserService;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private DBFileStorageService dbFileStorageService;
    private ApplicationUserService applicationUserService;

    @Autowired
    public FileController(DBFileStorageService dbFileStorageService, ApplicationUserService applicationUserService) {
        this.dbFileStorageService = dbFileStorageService;
        this.applicationUserService = applicationUserService;
    }

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, Principal principal)
            throws FileStorageException, WrongExtensionException, IOException,
            FileUploadBase.FileSizeLimitExceededException {
        ApplicationUser applicationUser = applicationUserService.findByPrincipal(principal);

        DBFile dbFile = dbFileStorageService.storeFile(file, applicationUser);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri, file.getContentType(), file.getSize());
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        DBFile dbFile = dbFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

    @ResponseBody
    @ExceptionHandler(FileStorageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse fileStorageHadler(FileStorageException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(WrongExtensionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse wrongExtensionHandler(WrongExtensionException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(FileUploadBase.FileSizeLimitExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse fileSizeTooLargeHandler(FileUploadBase.FileSizeLimitExceededException ex) {
        return new ErrorResponse(ex.getMessage());
    }
}


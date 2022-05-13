package com.ihvn.validator.service;

import com.ihvn.validator.entities.FileUpload;
import com.ihvn.validator.repositories.FileUploadRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FileUploadService {

    private final FileUploadRepository fileUploadRepository;
    private final JdbcTemplate jdbcTemplate;

    public FileUploadService(FileUploadRepository fileUploadRepository, JdbcTemplate jdbcTemplate) {
        this.fileUploadRepository = fileUploadRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    // This method uses the file name to update the file status to validated only if the
    // file status is not already set to consumed and then also set the validation time
    // in the file update table
    public void changeFileStatusAndUpdateValidationDate(String fileName, String message){

        String updateStatus = "update file_upload set status = ? where " +
                "file_name = ? and status != 'CONSUMED'";
        String updateValidatedDate = "update file_upload set validator_date = ?, " +
                "data_validation_report = ? where file_name = ?";

        jdbcTemplate.update(updateStatus, "Validated", fileName);
        jdbcTemplate.update(updateValidatedDate, LocalDateTime.now(), message, fileName);

    }
}

package com.ihvn.validator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihvn.validator.entities.ErrorReport;
import com.ihvn.validator.models.ValidationSummary;
import com.ihvn.validator.repositories.ErrorReportRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class ErrorReportService {

    private final ErrorReportRepository errorReportRepository;
    private final FileUploadService fileUploadService;

    public ErrorReportService(ErrorReportRepository errorReportRepository, FileUploadService fileUploadService) {
        this.errorReportRepository = errorReportRepository;
        this.fileUploadService = fileUploadService;
    }

    // This method is taking the validation summary and save it
    // to the error report table
    @SneakyThrows
    public void saveErrorReport(
            ValidationSummary validationSummary, String fileName){
        ErrorReport errorReport = new ErrorReport();
        errorReport.setPatientId(validationSummary.getPatientUuid());

        if(!validationSummary.getErrors().isEmpty()){
            ObjectMapper objectMapper = new ObjectMapper();
            errorReport.setErrorMessage(objectMapper.writeValueAsString(validationSummary.getErrors()));
            // Saving the errors
            errorReportRepository.save(errorReport);
            fileUploadService
                    .changeFileStatusAndUpdateValidationDate(fileName, "VALIDATION_PASSED");
        }else {
            // Setting file upload status and validation date
            fileUploadService
                    .changeFileStatusAndUpdateValidationDate(fileName, "VALIDATION_FAILED");
        }

    }
}

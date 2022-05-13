package com.ihvn.validator.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ihvn.validator.entities.ErrorReport;
import com.ihvn.validator.models.ValidationSummary;
import com.ihvn.validator.repositories.ErrorReportRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ErrorReportService {

    private final ErrorReportRepository errorReportRepository;


    public ErrorReportService(ErrorReportRepository errorReportRepository) {
        this.errorReportRepository = errorReportRepository;
    }

    // This method is taking the validation summary and save it
    // to the error report table
    public void saveErrorReport(ValidationSummary validationSummary){
        ErrorReport errorReport = new ErrorReport();
        errorReport.setPatientId(validationSummary.getPatientUuid());
        errorReport.setErrorMessage(validationSummary.getErrors());

        // Saving the errors
        errorReportRepository.save(errorReport);
    }
}

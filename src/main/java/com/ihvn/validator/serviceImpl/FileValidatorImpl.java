/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihvn.validator.serviceImpl;

import com.ihvn.validator.models.*;
import com.ihvn.validator.service.*;
import com.ihvn.validator.utils.ConstantsUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

/**
 *
 * @author MORRISON.I
 */
@Service
public class FileValidatorImpl implements FileValidator{


    private final PharmacyValidator pharmacyValidator;
    private final LaboratoryValidator laboratoryValidator;
    private final ARTCommencementValidator artCommencementValidator;
    private final ErrorReportService errorReportService;

    public FileValidatorImpl(PharmacyValidator pharmacyValidator,
                             LaboratoryValidator laboratoryValidator,
                             ARTCommencementValidator artCommencementValidator,
                             ErrorReportService errorReportService) {
        this.pharmacyValidator = pharmacyValidator;
        this.laboratoryValidator = laboratoryValidator;
        this.artCommencementValidator = artCommencementValidator;
        this.errorReportService = errorReportService;
    }

    @SneakyThrows
    @Override
    public void validationFile(Container container) {
        
        List<EncounterType> allEncounters = container.getMessageData().getEncounters();
        DemographicsType demographicsType = container.getMessageData().getDemographics();

        List<EncounterType> pharmEncounters = new ArrayList<>();
        List<EncounterType> careEncounters = new ArrayList<>();
        List<EncounterType> labEncounters = new ArrayList<>();
        List<EncounterType> artCommenceEncounter = new ArrayList<>();
        
        allEncounters.forEach(encounter -> {
            switch (encounter.getEncounterTypeId()) {
                case ConstantsUtils.PharmacyEncounterType:
                    pharmEncounters.add(encounter);
                    break;
                case ConstantsUtils.CareCardEncounterType:
                    careEncounters.add(encounter);
                    break;
                case ConstantsUtils.LabEncounterType:
                    labEncounters.add(encounter);
                    break;
                case ConstantsUtils.ART_COMMENCEMENT_TYPE:
                    artCommenceEncounter.add(encounter);
                default:
                    break;
            }
        });

        ValidationSummary validationSummary = new ValidationSummary();
        List<EncounterErrors> errors = new ArrayList<EncounterErrors>();
        validationSummary.setPatientUuid(demographicsType.getPatientUuid());

        List<EncounterErrors> pharmacyEncounterErrors = pharmacyValidator.validate(pharmEncounters,
                container.getMessageData().getObs().stream()
              .filter(a -> a.getEncounterType()==ConstantsUtils.PharmacyEncounterType)
              .collect(Collectors.toList()), demographicsType);

        // Adding pharmacy encounter errors to validation summary if not null
        if (!pharmacyEncounterErrors.isEmpty())
            errors.addAll(pharmacyEncounterErrors);

        List<EncounterErrors> laboratoryEncounterErrors = laboratoryValidator.validate(labEncounters,
                container.getMessageData().getObs().stream()
                .filter(a -> a.getEncounterType() == ConstantsUtils.PharmacyEncounterType)
                .collect(Collectors.toList()), demographicsType);

        // Adding laboratory encounter errors to validation summary if not null
        if (!laboratoryEncounterErrors.isEmpty())
            errors.addAll(laboratoryEncounterErrors);

        List<EncounterErrors> artCommenceEncounterErrors = artCommencementValidator.validate(artCommenceEncounter,
                container.getMessageData().getObs().stream()
                .filter(encounter -> encounter.getEncounterType() == ConstantsUtils.ART_COMMENCEMENT_TYPE)
                .collect(Collectors.toList()), demographicsType
        );

        // Adding ART commencement encounter errors to validation summary if not null
        if (!artCommenceEncounterErrors.isEmpty()) errors.addAll(artCommenceEncounterErrors);

        validationSummary.setErrors(errors);

        // Saving all the errors
        errorReportService.saveErrorReport(validationSummary, container.getMessageHeader().getFileName());

    }
    
    
    
    
    
    
//    	private static void validate(String input) {
//		JSONObject jsonSchema = new JSONObject(new JSONTokener(
//		        FileValidatorImpl.class.getResourceAsStream("/sample-schema-modified.json")));
//		JSONObject jsonSubject = new JSONObject(input);
//		
//		Schema schema = SchemaLoader.load(jsonSchema);
//		schema.validate(jsonSubject);
//		
//	}
    
}

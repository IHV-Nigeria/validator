/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihvn.validator.serviceImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihvn.validator.models.*;
import com.ihvn.validator.service.*;
import com.ihvn.validator.utils.ConstantsUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MORRISON.I
 */
@Service
public class FileValidatorImpl implements FileValidator{

    @Autowired
    PharmacyValidator pharmacyValidator;
    
    @Autowired
    LaboratoryValidator laboratoryValidator;

    @Autowired
    ARTCommencementValidator artCommencementValidator;

    private final ErrorReportService errorReportService;
    private final FileUploadService fileUploadService;

    public FileValidatorImpl(ErrorReportService errorReportService,
                             FileUploadService fileUploadService) {
        this.errorReportService = errorReportService;
        this.fileUploadService = fileUploadService;
    }

    @Override
    public void validationFile(Container container) {
        
        List<EncounterType> allEncounters = container.getMessageData().getEncounters();
        DemographicsType demographicsType = container.getMessageData().getDemographics();

        List<EncounterType> pharmEncounters = new ArrayList<>();
        List<EncounterType> careEncounters = new ArrayList<>();
        List<EncounterType> labEncounters = new ArrayList<>();
        List<EncounterType> artCommenceEncounter = new ArrayList<>() ;
        
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

        Map<String, Object> objectMap = new HashMap<>();

        List<EncounterErrors> pharmacyEncounterErrors = pharmacyValidator.validate(pharmEncounters, container.getMessageData().getObs().stream()
              .filter(a -> a.getEncounterType()==ConstantsUtils.PharmacyEncounterType)
              .collect(Collectors.toList()), demographicsType);

        // Adding pharmacy encounter errors to validation summary if not null
        if (pharmacyEncounterErrors != null)
            errors.addAll(pharmacyEncounterErrors);
            /*objectMap.put(FormErrorsUtils.PHARMACY_ENCOUNTER_ERRORS,
                    pharmacyEncounterErrors);*/

        List<EncounterErrors> laboratoryEncounterErrors = laboratoryValidator.validate(labEncounters, container.getMessageData().getObs().stream()
                .filter(a -> a.getEncounterType() == ConstantsUtils.PharmacyEncounterType)
                .collect(Collectors.toList()), demographicsType);

        // Adding laboratory encounter errors to validation summary if not null
        if (laboratoryEncounterErrors != null)
            errors.addAll(laboratoryEncounterErrors);
            /*objectMap.put(FormErrorsUtils.LABORATORY_ENCOUNTER_ERRORS,
                    laboratoryEncounterErrors);*/

        List<EncounterErrors> artCommenceEncounterErrors = artCommencementValidator.validate(artCommenceEncounter, container.getMessageData().getObs().stream()
                .filter(encounter -> encounter.getEncounterType() == ConstantsUtils.ART_COMMENCEMENT_TYPE)
                .collect(Collectors.toList()), demographicsType
        );

        // Adding ART commencement encounter errors to validation summary if not null
        if (artCommenceEncounterErrors != null)
            errors.addAll(artCommenceEncounterErrors);
            /* objectMap.put(FormErrorsUtils.ART_COMMENCEMENT_ENCOUNTER_ERRORS,
                    artCommenceEncounterErrors);*/

        /* validationSummary.setErrors(errors);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNodeMap = mapper.convertValue(objectMap, JsonNode.class);*/

        // Saving all the errors
        errorReportService.saveErrorReport(validationSummary);

        // Setting file upload status and validation date
        fileUploadService.changeFileStatusAndUpdateValidationDate(
                container.getMessageHeader().getFileName());


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

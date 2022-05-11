/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihvn.validator.serviceImpl;

import com.ihvn.validator.models.Container;
import com.ihvn.validator.models.EncounterType;
import com.ihvn.validator.service.FileValidator;
import com.ihvn.validator.service.LaboratoryValidator;
import com.ihvn.validator.service.PharmacyValidator;
import com.ihvn.validator.utils.ConstantsUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author MORRISON.I
 */
public class FileValidatorImpl implements FileValidator{
    
    
    @Autowired
    PharmacyValidator pharmacyValidator;
    
    @Autowired
    LaboratoryValidator laboratoryValidator;

    @Override
    public void validationFile(Container container) {
        
        List<EncounterType> allEncounters = container.getMessageData().getEncounters();
        List<EncounterType> pharmEncounters = new ArrayList<>();
         List<EncounterType> careEncounters = new ArrayList<>();
           List<EncounterType> labEncounters = new ArrayList<>();
        
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
                default:
                    break;
            }
        });
        
        
      pharmacyValidator.validate(pharmEncounters, container.getMessageData().getObs().stream()
              .filter(a -> a.getEncounterType()==ConstantsUtils.PharmacyEncounterType)
              .collect(Collectors.toList()));
      
      laboratoryValidator.validate(labEncounters, container.getMessageData().getObs().stream()
              .filter(a -> a.getEncounterType()==ConstantsUtils.PharmacyEncounterType)
              .collect(Collectors.toList()));
      
      
       
        
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

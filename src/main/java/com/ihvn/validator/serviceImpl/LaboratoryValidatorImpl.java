/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihvn.validator.serviceImpl;

import com.ihvn.validator.models.EncounterErrors;
import com.ihvn.validator.models.EncounterType;
import com.ihvn.validator.models.ObsError;
import com.ihvn.validator.models.ObsType;
import com.ihvn.validator.models.ValidationSummary;
import com.ihvn.validator.service.LaboratoryValidator;
import com.ihvn.validator.utils.LabConceptsUtils;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 *
 * @author MORRISON.I
 */
public class LaboratoryValidatorImpl implements LaboratoryValidator {

    @Override
    public List<EncounterErrors> validate(List<EncounterType> encounters, List<ObsType> labObs) {
        
        List<EncounterErrors> allEncounterErrors = new ArrayList<>();
        
        encounters.stream().forEach(a -> {
            List<ObsError> errors = validateLabForm(a, labObs.stream().filter(b -> b.getEncounterId()==a.getEncounterId())
                    .collect(Collectors.toList()));
            EncounterErrors ee = new EncounterErrors();
            ee.setEncounterId(a.getEncounterId());
            ee.setFormId(a.getFormId());
            ee.setErrors(errors);
            
            allEncounterErrors.add(ee);
            
        });
        
        
        return allEncounterErrors;

    }

    private  List<ObsError> validateLabForm(EncounterType e, List<ObsType> obsList) {

        List<ObsError> allObsErrors = new ArrayList<>();
        StringJoiner sb = new StringJoiner(",");
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate stDate = LocalDate.of(1999, Month.JANUARY, 01);
        Date date_1999 = Date.from(stDate.atStartOfDay(defaultZoneId).toInstant());
        
        obsList.stream()
                .forEach(b -> {
                    ObsError eachError = new ObsError();
            eachError.setObsId(b.getObsId());
            
            switch (b.getConceptId()) {
                case LabConceptsUtils.InitialCD4Count:
                    if (b.getValueNumeric() == null) {
                        sb.add("cd4count is null");
                    }
                    break;
                case LabConceptsUtils.CurrentViralLoad:
                    if (b.getValueNumeric() == null) {
                        sb.add("current viralload is null");
                    }
                    break;
                case LabConceptsUtils.ViralLoadSampleCollectionDate:
                    if (b.getValueDatetime() == null) {
                        sb.add("viralload sample collection date is null");
                    } else if (DateUtils.truncate(b.getValueDatetime(), Calendar.DATE).after(new Date())) {
                        sb.add("viralload sample collection date is in the future");
                    }
                    break;
                case LabConceptsUtils.ViralLoadReportedDate:
                    if (b.getValueDatetime() == null) {
                        sb.add("viralload reported date is null");
                    } else if (DateUtils.truncate(b.getValueDatetime(), Calendar.DATE).after(new Date())) {
                        sb.add("viralload reported date is in the future");
                    } else if(DateUtils.truncate(b.getValueDatetime(), Calendar.DATE).before(date_1999)){
                        sb.add("viralload reported date is before 1999-01-01");
                    }
                    break;
                case LabConceptsUtils.ResultDate:
                    if (b.getValueDatetime() != null && DateUtils.truncate(b.getValueDatetime(), Calendar.DATE).after(new Date())) {
                        sb.add("result date is in the future");
                    } else if(b.getValueDatetime() != null && DateUtils.truncate(b.getValueDatetime(), Calendar.DATE).before(date_1999)){
                        sb.add("result date is before 1999-01-01");
                    }
                    break;
                case LabConceptsUtils.AssayDate:
                    if (b.getValueDatetime() != null && DateUtils.truncate(b.getValueDatetime(), Calendar.DATE).after(new Date())) {
                        sb.add("assay date is in the future");
                    } else if(b.getValueDatetime() != null && DateUtils.truncate(b.getValueDatetime(), Calendar.DATE).before(date_1999)){
                        sb.add("assay date is before 1999-01-01");
                    }
                    break;    
                case LabConceptsUtils.ApprovalDate:
                    if (b.getValueDatetime() != null && DateUtils.truncate(b.getValueDatetime(), Calendar.DATE).after(new Date())) {
                        sb.add("approval date is in the future");
                    } else if(b.getValueDatetime() != null && DateUtils.truncate(b.getValueDatetime(), Calendar.DATE).before(date_1999)){
                        sb.add("approval date is before 1999-01-01");
                    }
                    break;
                 case LabConceptsUtils.ViralLoadIndication:
                    if (b.getValueCoded() == 0.0 || StringUtils.isBlank(b.getVariableValue())) {
                        sb.add("viralload indication is missing");
                    } 
                    break;
                default:
                    break;
            }
            eachError.setError(sb.toString());

            
            allObsErrors.add(eachError);
            
                });
        
        
        return allObsErrors;

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihvn.validator.serviceImpl;

import com.ihvn.validator.models.EncounterType;
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
import java.util.stream.Collectors;
import org.apache.commons.lang3.time.DateUtils;

/**
 *
 * @author MORRISON.I
 */
public class LaboratoryValidatorImpl implements LaboratoryValidator {

    @Override
    public void validate(List<EncounterType> encounters, List<ObsType> labObs) {
        encounters.stream().forEach(a -> {

        });

    }

    private void validatePharmacyForm(EncounterType e, List<ObsType> obsList) {

        List<ValidationSummary> vals = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate stDate = LocalDate.of(1999, Month.JANUARY, 01);
        Date date_1999 = Date.from(stDate.atStartOfDay(defaultZoneId).toInstant());
        
        obsList.stream()
                .forEach(b -> {
                    ValidationSummary val = new ValidationSummary();
            //cd4count
            switch (b.getConceptId()) {
                case LabConceptsUtils.InitialCD4Count:
                    if (b.getValueNumeric() == null) {
                        sb.append("cd4count is null");
                    }
                    break;
                case LabConceptsUtils.CurrentViralLoad:
                    if (b.getValueNumeric() == null) {
                        sb.append("current viralload is null");
                    }
                    break;
                case LabConceptsUtils.ViralLoadSampleCollectionDate:
                    if (b.getValueDatetime() == null) {
                        sb.append("viralload sample collection date is null");
                    } else if (DateUtils.truncate(b.getValueDatetime(), Calendar.DATE).after(new Date())) {
                        sb.append("viralload sample collection date is in the future");
                    }
                    break;
                case LabConceptsUtils.ViralLoadReportedDate:
                     if (b.getValueDatetime() == null) {
                        sb.append("viralload reported date is null");
                    } else if (DateUtils.truncate(b.getValueDatetime(), Calendar.DATE).after(new Date())) {
                        sb.append("viralload reported date is in the future");
                    } else if(DateUtils.truncate(b.getValueDatetime(), Calendar.DATE).before(date_1999)){
                        sb.append("viralload reported date is before 1999-01-01");
                    }
                    break;
                case LabConceptsUtils.ResultDate:
                      if (b.getValueDatetime() == null) {
                        sb.append("result date is null");
                    } else if (DateUtils.truncate(b.getValueDatetime(), Calendar.DATE).after(new Date())) {
                        sb.append("result date is in the future");
                    } else if(DateUtils.truncate(b.getValueDatetime(), Calendar.DATE).before(date_1999)){
                        sb.append("result date is before 1999-01-01");
                    }
                    break;
                default:
                    break;
            }

                });

    }

}

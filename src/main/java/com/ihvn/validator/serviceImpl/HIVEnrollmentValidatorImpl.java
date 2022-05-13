/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihvn.validator.serviceImpl;

import com.ihvn.validator.models.*;

import java.util.List;
import com.ihvn.validator.service.HIVEnrollmentValidator;
import com.ihvn.validator.utils.ConstantsUtils;
import com.ihvn.validator.utils.HivEnrollmentConceptsUtils;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import com.ihvn.validator.utils.ValidatorDateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

/**
 *
 * @author MORRISON.I
 */
@Service
public class HIVEnrollmentValidatorImpl implements HIVEnrollmentValidator{

    @Override
    public List<EncounterErrors> validate(List<EncounterType> encounters,
                                          List<ObsType> obsList, DemographicsType demographicsType) {
        List<EncounterErrors> allEncounterErrors = new ArrayList<>();
        
        encounters.stream().forEach(a -> {
            List<ObsError> errors = validateHIVEnrollmentForm(a, obsList.stream().filter(b -> b.getEncounterId()==a.getEncounterId())
                    .collect(Collectors.toList()), demographicsType);
            EncounterErrors ee = new EncounterErrors();
            ee.setEncounterId(a.getEncounterId());
            ee.setFormId(a.getFormId());
            ee.setErrors(errors);

            if (!ee.getErrors().isEmpty()) allEncounterErrors.add(ee);
            
        });
        
        
        return allEncounterErrors;
    }
    
    private  List<ObsError> validateHIVEnrollmentForm(EncounterType e, List<ObsType> obsList,
                                                      DemographicsType demographicsType) {

        List<ObsError> allObsErrors = new ArrayList<>();
        StringJoiner sb = new StringJoiner(",");
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate stDate = LocalDate.of(1999, Month.JANUARY, 01);
        Date date_1999 = Date.from(stDate.atStartOfDay(defaultZoneId).toInstant());

        List<DataError> errors = new ArrayList<>();

        ValidatorDateUtils validatorDateUtils = new ValidatorDateUtils();
        LocalDate localDate_1999 = LocalDate.of(1999, Month.JANUARY, 01);
        
        obsList.stream()
                // .filter(voidedObs -> voidedObs.getVoided() == 0)
                .forEach(b -> {
                    ObsError eachError = new ObsError();
            eachError.setObsId(b.getObsId());
            
            switch (b.getConceptId()) {
                case HivEnrollmentConceptsUtils.CareEntryPoint:
                    if (StringUtils.isNotBlank(b.getVariableValue())) {
                        // sb.add("care entry point is blank");
                        errors.add(new DataError("care entry point is blank", ErrorLevel.CRITICAL));
                    }
                    break;
                case HivEnrollmentConceptsUtils.KPType:
                    if (ConstantsUtils.getObsbyConceptID(166284, obsList).isPresent() && StringUtils.isBlank(b.getVariableValue())) {
                       // sb.add("kptype is blank");
                         errors.add(new DataError("kptype is blank", ErrorLevel.NON_CRITICAL));
                    }
                    break;
                case HivEnrollmentConceptsUtils.DateTransferredIn:
                    Optional<ObsType> tempObs = ConstantsUtils.getObsbyConceptID(160540, obsList);
                    if (tempObs.isPresent() && tempObs.get().getValueCoded()==160593 && b.getValueDatetime()==null) {
                    //   sb.add("DateTransferredIn is blank");
                          errors.add(new DataError("DateTransferredIn is blank", ErrorLevel.NON_CRITICAL));
                    } else if (b.getValueDatetime() != null && validatorDateUtils.convertToLocalDateTime(b.getValueDatetime()).isAfter(LocalDate.now())) {
                      //  sb.add("DateTransferredIn is in the future");
                         errors.add(new DataError("DateTransferredIn is in the future", ErrorLevel.NON_CRITICAL));
                    }
                    break;
                case HivEnrollmentConceptsUtils.DateConfirmedHIVPos:
                    if (b.getValueDatetime() == null) {
                       sb.add("DateConfirmedHIVPos is null");
                       errors.add(new DataError("DateConfirmedHIVPos is null", ErrorLevel.CRITICAL));
                    } else if (b.getValueDatetime() != null &&  validatorDateUtils.convertToLocalDateTime(b.getValueDatetime()).isAfter(LocalDate.now())) {
                        //  sb.add("DateTransferredIn is in the future");
                        errors.add(new DataError("DateConfirmedHIVPos is in the future", ErrorLevel.CRITICAL));
                    }
                    break;
                default:
                    break;
            }
            eachError.setError(errors);

            if (!eachError.getError().isEmpty()) allObsErrors.add(eachError);
        });

        return allObsErrors;

    }

}

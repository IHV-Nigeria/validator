/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihvn.validator.serviceImpl;

import com.ihvn.validator.models.*;
import com.ihvn.validator.service.PharmacyValidator;
import com.ihvn.validator.utils.GenderUtils;
import com.ihvn.validator.utils.ValidatorDateUtils;
import com.ihvn.validator.utils.concepts.PharmacyConceptsUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author MORRISON.I
 */
@Service
public class PharmacyValidatorImpl implements PharmacyValidator{

    @Override
    public List<EncounterErrors> validate(List<EncounterType> encounters, List<ObsType> pharmObs,
                         DemographicsType demographicsType) {

       List<EncounterErrors> allEncounterErrors = new ArrayList<>();
       encounters.stream().forEach(a -> {
           List<ObsError> errors = validatePharmacyForm(a, pharmObs.stream().filter(b -> b.getEncounterId()==a.getEncounterId())
                   .collect(Collectors.toList()), demographicsType);
           
           //validate all obs for the Encounter
           EncounterErrors ee = new EncounterErrors();
           ee.setEncounterId(a.getEncounterId());
           ee.setFormId(a.getFormId());
           ee.setErrors(errors);

           if (!ee.getErrors().isEmpty()) allEncounterErrors.add(ee);
       });

       return allEncounterErrors;
    }

    private  List<ObsError> validatePharmacyForm(EncounterType e, List<ObsType> obsList,
                                                 DemographicsType demographicsType) {

        List<ObsError> allObsErrors = new ArrayList<>();

        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate stDate = LocalDate.of(1999, Month.JANUARY, 01);
        Date date_1999 = Date.from(stDate.atStartOfDay(defaultZoneId).toInstant());
        
         List<DataError> errors = new ArrayList<>();

        ValidatorDateUtils validatorDateUtils = new ValidatorDateUtils();
        LocalDate localDate_1999 = LocalDate.of(1999, Month.JANUARY, 01);

        obsList.stream()
                // .filter(voidedObs -> voidedObs.getVoided() == 0)
                .forEach( currentObs -> {
                    ObsError eachError = new ObsError();
                    eachError.setObsId(currentObs.getObsId());

                    switch (currentObs.getConceptId()){
                        case PharmacyConceptsUtils.TREATMENT_TYPE:
                            if (currentObs.getValueCoded() == 0.0 ||
                                    StringUtils.isBlank(currentObs.getVariableValue())) {
                              //  sb.add("Treatment type is null");
                                errors.add(new DataError("Treatment type is null", ErrorLevel.CRITICAL));
                            }
                            break;
                        case PharmacyConceptsUtils.VISIT_TYPE:
                            if (currentObs.getValueCoded() == 0.0 ||
                                    StringUtils.isBlank(currentObs.getVariableValue())
                            ){
                               // sb.add("Visit type is null");
                                 errors.add(new DataError("Visit type is null", ErrorLevel.NON_CRITICAL));
                            }
                            break;
                        case PharmacyConceptsUtils.PREGNANCY_STATUS:
                            if(Objects.equals(GenderUtils.FEMALE.getType(), demographicsType.getGender()) &&
                                    (StringUtils.isBlank(currentObs.getVariableValue()))) {
                               // sb.add("Pregnancy Status is null");
                                 errors.add(new DataError("Pregnancy Status is null", ErrorLevel.NON_CRITICAL));
                            }
                            break;
                        case PharmacyConceptsUtils.PICKUP_REASON:
                            if (StringUtils.isBlank(currentObs.getVariableValue())){
                               // sb.add("Pickup reason is null");
                                 errors.add(new DataError("Pickup reason is null", ErrorLevel.NON_CRITICAL));
                            }
                            break;
                        case PharmacyConceptsUtils.DISPENSING_MODALITY:
                            if (StringUtils.isBlank(currentObs.getVariableValue())){
                               // sb.add("Dispensing modality is null");
                                  errors.add(new DataError("Dispensing modality is null", ErrorLevel.NON_CRITICAL));
                            }
                            break;
                        case PharmacyConceptsUtils.FACILITY_DISPENSING_MODALITY:
                            // TODO
                            break;
                        case PharmacyConceptsUtils.DDD_DISPENSING_MODALITY:
                            // TODO
                            break;
                        case PharmacyConceptsUtils.MMD_TYPE:
                            if (StringUtils.isBlank(currentObs.getVariableValue())){
                               // sb.add("MMD type is null");
                                 errors.add(new DataError("MMD type is null", ErrorLevel.NON_CRITICAL));
                            }
                            break;
                        case PharmacyConceptsUtils.TREATMENT_AGE_CATEGORY:
                            if (StringUtils.isBlank(currentObs.getVariableValue())
                            ){
                              //  sb.add("Treatment age category is null");
                                 errors.add(new DataError("Treatment age category is null", ErrorLevel.NON_CRITICAL));
                            }
                            break;
                        case PharmacyConceptsUtils.CURRENT_REGIMEN_LINE:
                            if (StringUtils.isBlank(currentObs.getVariableValue())
                            ){
                              //  sb.add("Current regimen is null");
                                 errors.add(new DataError("Current regimen is null", ErrorLevel.NON_CRITICAL));
                            }
                            break;
                        case PharmacyConceptsUtils.DAYS_OF_ARV_REFILL:
                            try {
                                int intValue = Integer.parseInt(String.valueOf(currentObs.getValueNumeric()));
                                if (intValue < 30 && intValue > 180){
                                  //  sb.add("Days of ARV refill must be between 30 - 180");
                                    errors.add(new DataError("Days of ARV refill must be between 30 - 180", ErrorLevel.NON_CRITICAL));
                                }
                            } catch (NumberFormatException nfe) {
                                //sb.add("Days of ARV refill must be an integer");
                                 errors.add(new DataError("Days of ARV refill must be an integer", ErrorLevel.NON_CRITICAL));
                            }
                            break;
                        case PharmacyConceptsUtils.PILL_BALANCE:
                            // TODO
                            break;
                        case PharmacyConceptsUtils.CALCULATED_NEXT_APPOINTMENT:
                            if(currentObs.getValueDatetime() == null){
                            //    sb.add("Calculated next appointment date is null");
                                 errors.add(new DataError("Calculated next appointment date is null", ErrorLevel.NON_CRITICAL));
                            }
                            break;
                        case PharmacyConceptsUtils.LAST_INH_DISPENSED_DATE:
                            if (currentObs.getValueDatetime() == null) {
                            //    sb.add("Last INH dispensed date is null");
                                 errors.add(new DataError("Last INH dispensed date is null", ErrorLevel.NON_CRITICAL));
                            } else if (currentObs.getValueDatetime() != null && validatorDateUtils.convertToLocalDateTime(currentObs.getValueDatetime()).isAfter(LocalDate.now())) {;
                               // sb.add("Last INH dispensed date is in the future");
                                 errors.add(new DataError("Last INH dispensed date is in the future", ErrorLevel.NON_CRITICAL));
                            } else if(currentObs.getValueDatetime() != null && validatorDateUtils.convertToLocalDateTime(currentObs.getValueDatetime()).isBefore(localDate_1999)){
                               // sb.add("Last INH dispensed date is before 1999-01-01");
                                 errors.add(new DataError("Last INH dispensed date is before 1999-01-01", ErrorLevel.NON_CRITICAL));
                            }
                            break;
                        case PharmacyConceptsUtils.DATE_ORDERED:
                            if (currentObs.getValueDatetime() == null) {
                               // sb.add("Date Ordered is null");
                                errors.add(new DataError("Date Ordered is null", ErrorLevel.NON_CRITICAL));
                            } else if (currentObs.getValueDatetime() != null && validatorDateUtils.convertToLocalDateTime(currentObs.getValueDatetime()).isAfter(LocalDate.now())) {
                               // sb.add("Date Ordered is in the future");
                                   errors.add(new DataError("Date Ordered is in the future", ErrorLevel.NON_CRITICAL));
                            } else if(currentObs.getValueDatetime() != null && validatorDateUtils.convertToLocalDateTime(currentObs.getValueDatetime()).isBefore(localDate_1999)){
                               // sb.add("Date Ordered is before 1999-01-01");
                                errors.add(new DataError("Date Ordered is before 1999-01-01", ErrorLevel.NON_CRITICAL));
                            }
                            break;
                        default:
                            break;
                    }

                    eachError.setError(errors);
                    if (!eachError.getError().isEmpty()) allObsErrors.add(eachError);

                }
        );

        return allObsErrors;
    }
    
}



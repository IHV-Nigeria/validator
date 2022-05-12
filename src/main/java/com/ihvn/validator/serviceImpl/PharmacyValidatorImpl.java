/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihvn.validator.serviceImpl;

import com.ihvn.validator.models.*;
import com.ihvn.validator.service.PharmacyValidator;
import com.ihvn.validator.utils.GenderUtils;
import com.ihvn.validator.utils.concepts.PharmacyConceptsUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author MORRISON.I
 */
public class PharmacyValidatorImpl implements PharmacyValidator{

    @Override
    public void validate(List<EncounterType> encounters, List<ObsType> pharmObs,
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

           allEncounterErrors.add(ee);
       });
    }

    private  List<ObsError> validatePharmacyForm(EncounterType e, List<ObsType> obsList,
                                                 DemographicsType demographicsType) {

        List<ObsError> allObsErrors = new ArrayList<>();

        StringJoiner sb = new StringJoiner(",");
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate stDate = LocalDate.of(1999, Month.JANUARY, 01);
        Date date_1999 = Date.from(stDate.atStartOfDay(defaultZoneId).toInstant());

        obsList.stream()
                .forEach( currentObs -> {
                    ObsError eachError = new ObsError();
                    eachError.setObsId(currentObs.getObsId());

                    switch (currentObs.getConceptId()){
                        case PharmacyConceptsUtils.TREATMENT_TYPE:
                            if (currentObs.getValueCoded() == 0.0 ||
                                    StringUtils.isBlank(currentObs.getVariableValue())) {
                                sb.add("Treatment type is null");
                            }
                            break;
                        case PharmacyConceptsUtils.VISIT_TYPE:
                            if (currentObs.getValueCoded() == 0.0 ||
                                    StringUtils.isBlank(currentObs.getVariableValue())
                            ){
                                sb.add("Visit type is null");
                            }
                            break;
                        case PharmacyConceptsUtils.PREGNANCY_STATUS:
                            if(Objects.equals(GenderUtils.FEMALE.getType(), demographicsType.getGender()) &&
                                    (currentObs.getValueCoded() == 0.0 ||
                                            StringUtils.isBlank(currentObs.getVariableValue()))
                            ) {
                                sb.add("Pregnancy Status is null");
                            }
                            break;
                        case PharmacyConceptsUtils.PICKUP_REASON:
                            if (currentObs.getValueCoded() == 0.0 ||
                                    StringUtils.isBlank(currentObs.getVariableValue())
                            ){
                                sb.add("Pickup reason is null");
                            }
                            break;
                        case PharmacyConceptsUtils.DISPENSING_MODALITY:
                            if (currentObs.getValueCoded() == 0.0 ||
                                    StringUtils.isBlank(currentObs.getVariableValue())
                            ){
                                sb.add("Dispensing modality is null");
                            }
                            break;
                        case PharmacyConceptsUtils.FACILITY_DISPENSING_MODALITY:
                            // TODO
                            break;
                        case PharmacyConceptsUtils.DDD_DISPENSING_MODALITY:
                            // TODO
                            break;
                        case PharmacyConceptsUtils.MMD_TYPE:
                            if (currentObs.getValueCoded() == 0.0 ||
                                    StringUtils.isBlank(currentObs.getVariableValue())
                            ){
                                sb.add("MMD type is null");
                            }
                            break;
                        case PharmacyConceptsUtils.TREATMENT_AGE_CATEGORY:
                            if (currentObs.getValueCoded() == 0.0 ||
                                    StringUtils.isBlank(currentObs.getVariableValue())
                            ){
                                sb.add("Treatment age category is null");
                            }
                            break;
                        case PharmacyConceptsUtils.CURRENT_REGIMEN_LINE:
                            if (currentObs.getValueCoded() == 0.0 ||
                                    StringUtils.isBlank(currentObs.getVariableValue())
                            ){
                                sb.add("Current regimen is null");
                            }
                            break;
                        case PharmacyConceptsUtils.DAYS_OF_ARV_REFILL:
                            try {
                                int intValue = Integer.parseInt(String.valueOf(currentObs.getValueNumeric()));
                                if (intValue < 30 && intValue > 180){
                                    sb.add("Days of ARV refill must be between 30 - 180");
                                }
                            } catch (NumberFormatException nfe) {
                                sb.add("Days of ARV refill must be an integer");
                            }
                            break;
                        case PharmacyConceptsUtils.PILL_BALANCE:
                            // TODO
                            break;
                        case PharmacyConceptsUtils.CALCULATED_NEXT_APPOINTMENT:
                            if(currentObs.getValueDatetime() == null){
                                sb.add("Calculated next appointment date is null");
                            }
                            break;
                        case PharmacyConceptsUtils.LAST_INH_DISPENSED_DATE:
                            if (currentObs.getValueDatetime() == null) {
                                sb.add("Last INH dispensed date is null");
                            } else if (DateUtils.truncate(currentObs.getValueDatetime(), Calendar.DATE).after(new Date())) {
                                sb.add("Last INH dispensed date is in the future");
                            } else if(DateUtils.truncate(currentObs.getValueDatetime(), Calendar.DATE).before(date_1999)){
                                sb.add("Last INH dispensed date is before 1999-01-01");
                            }
                            break;
                        case PharmacyConceptsUtils.DATE_ORDERED:
                            if (currentObs.getValueDatetime() == null) {
                                sb.add("Date Ordered is null");
                            } else if (DateUtils.truncate(currentObs.getValueDatetime(), Calendar.DATE).after(new Date())) {
                                sb.add("Date Ordered is in the future");
                            } else if(DateUtils.truncate(currentObs.getValueDatetime(), Calendar.DATE).before(date_1999)){
                                sb.add("Date Ordered is before 1999-01-01");
                            }
                            break;
                        default:
                            break;
                    }

                    eachError.setError(sb.toString());
                    allObsErrors.add(eachError);

                }
        );

        return allObsErrors;
    }
    
}



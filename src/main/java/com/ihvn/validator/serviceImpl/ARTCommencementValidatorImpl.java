package com.ihvn.validator.serviceImpl;

import com.ihvn.validator.models.*;
import com.ihvn.validator.service.ARTCommencementValidator;
import com.ihvn.validator.utils.GenderUtils;
import com.ihvn.validator.utils.ValidatorDateUtils;
import com.ihvn.validator.utils.concepts.ARTCommencementConceptsUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ARTCommencementValidatorImpl implements ARTCommencementValidator {

    @Override
    public List<EncounterErrors> validate(List<EncounterType> encounters, List<ObsType> artCommenceObs, DemographicsType demographicsType) {
        List<EncounterErrors> allEncounterErrors = new ArrayList<>();
        encounters.stream().forEach(a -> {
            List<ObsError> errors = validateARTCommencementForm(a, artCommenceObs.stream()
                    .filter(b -> b.getEncounterId()==a.getEncounterId())
                    .collect(Collectors.toList()), demographicsType);

            //validate all obs for the Encounter
            EncounterErrors ee = new EncounterErrors();
            ee.setEncounterId(a.getEncounterId());
            ee.setFormId(a.getFormId());
            ee.setErrors(errors);

            if (!ee.getErrors().isEmpty()) allEncounterErrors.add(ee);
        });

        return  allEncounterErrors;
    }

    private  List<ObsError> validateARTCommencementForm(EncounterType e, List<ObsType> obsList,
                                                 DemographicsType demographicsType) {

        List<ObsError> allObsErrors = new ArrayList<ObsError>();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate stDate = LocalDate.of(1999, Month.JANUARY, 01);
        Date date_1999 = Date.from(stDate.atStartOfDay(defaultZoneId).toInstant());
        
        List<DataError> errors = new ArrayList<>();

        ValidatorDateUtils validatorDateUtils = new ValidatorDateUtils();
        LocalDate localDate_1999 = LocalDate.of(1999, Month.JANUARY, 01);

        obsList.stream()
                // .filter(voidedObs -> voidedObs.getVoided() == 0)
                .forEach(currentObs -> {

                    ObsError eachError = new ObsError();
                    eachError.setObsId(currentObs.getObsId());

                    switch (currentObs.getConceptId()) {
                        case ARTCommencementConceptsUtils.ART_START_DATE:
                            if (currentObs.getValueDatetime() == null) {
                             //   sb.add("ART start date is null");
                                errors.add(new DataError("ART start date is null", ErrorLevel.CRITICAL));
                                
                            } else if (currentObs.getValueDatetime() != null && validatorDateUtils.convertToLocalDateTime(currentObs.getValueDatetime()).isAfter(LocalDate.now())) {
                               // sb.add("ART start date is in the future");
                              errors.add(new DataError("ART start date is in the future", ErrorLevel.CRITICAL));
                                
                            } else if(currentObs.getValueDatetime() != null && validatorDateUtils.convertToLocalDateTime(currentObs.getValueDatetime()).isBefore(localDate_1999)){
                             //   sb.add("ART start date is before 1999-01-01");
                                errors.add(new DataError("ART start date is before 1999-01-01", ErrorLevel.NON_CRITICAL));
                            }
                            break;
                        case ARTCommencementConceptsUtils.CLINICAL_STAGE:
                            if (StringUtils.isBlank(currentObs.getVariableValue())
                            ){
                              //  sb.add("Clinical stage is null");
                                 errors.add(new DataError("Clinical stage is null", ErrorLevel.NON_CRITICAL));
                            }
                            break;
                        case ARTCommencementConceptsUtils.FUNCTIONAL_STATUS:
                            if (StringUtils.isBlank(currentObs.getVariableValue())
                            ){
                              //  sb.add("Functional status is null");
                                errors.add(new DataError("Functional status is null", ErrorLevel.NON_CRITICAL));
                                
                            }
                            break;
                        case ARTCommencementConceptsUtils.PREGNANCY_STATUS:
                            if(Objects.equals(GenderUtils.FEMALE.getType(), demographicsType.getGender()) &&
                                    (StringUtils.isBlank(currentObs.getVariableValue()))
                            ) {
                               // sb.add("Pregnancy Status is null");
                                 errors.add(new DataError("Pregnancy Status is null", ErrorLevel.NON_CRITICAL));
                            }
                            break;
                        case ARTCommencementConceptsUtils.INITIAL_REGIMEN_LINE:
                            if (StringUtils.isBlank(currentObs.getVariableValue())
                            ){
                              //  sb.add("Initial regimen line is null, must select one of adult or paediatric");
                                  errors.add(new DataError("Initial regimen line is null, must select one of adult or paediatric", ErrorLevel.CRITICAL));
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

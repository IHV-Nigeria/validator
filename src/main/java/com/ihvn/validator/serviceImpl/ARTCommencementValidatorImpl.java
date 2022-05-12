package com.ihvn.validator.serviceImpl;

import com.ihvn.validator.models.*;
import com.ihvn.validator.service.ARTCommencementValidator;
import com.ihvn.validator.utils.GenderUtils;
import com.ihvn.validator.utils.concepts.ARTCommencementConceptsUtils;
import com.ihvn.validator.utils.concepts.PharmacyConceptsUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class ARTCommencementValidatorImpl implements ARTCommencementValidator {

    @Override
    public void validate(List<EncounterType> encounters, List<ObsType> artCommenceObs, DemographicsType demographicsType) {
        List<EncounterErrors> allEncounterErrors = new ArrayList<>();
        encounters.stream().forEach(a -> {
            List<ObsError> errors = validateARTCommencementForm(a, artCommenceObs.stream().filter(b -> b.getEncounterId()==a.getEncounterId())
                    .collect(Collectors.toList()), demographicsType);

            //validate all obs for the Encounter
            EncounterErrors ee = new EncounterErrors();
            ee.setEncounterId(a.getEncounterId());
            ee.setFormId(a.getFormId());
            ee.setErrors(errors);

            allEncounterErrors.add(ee);
        });
    }

    private  List<ObsError> validateARTCommencementForm(EncounterType e, List<ObsType> obsList,
                                                 DemographicsType demographicsType) {

        List<ObsError> allObsErrors = new ArrayList<ObsError>();

        StringJoiner sb = new StringJoiner(",");
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate stDate = LocalDate.of(1999, Month.JANUARY, 01);
        Date date_1999 = Date.from(stDate.atStartOfDay(defaultZoneId).toInstant());

        obsList.stream()
                .forEach(currentObs -> {
                    ObsError eachError = new ObsError();
                    eachError.setObsId(currentObs.getObsId());

                    switch (currentObs.getConceptId()) {
                        case ARTCommencementConceptsUtils.ART_START_DATE:
                            if (currentObs.getValueDatetime() == null) {
                                sb.add("ART start date is null");
                            } else if (DateUtils.truncate(currentObs.getValueDatetime(), Calendar.DATE).after(new Date())) {
                                sb.add("ART start date is in the future");
                            } else if(DateUtils.truncate(currentObs.getValueDatetime(), Calendar.DATE).before(date_1999)){
                                sb.add("ART start date is before 1999-01-01");
                            }
                            break;
                        case ARTCommencementConceptsUtils.CLINICAL_STAGE:
                            if (currentObs.getValueCoded() == 0.0 ||
                                    StringUtils.isBlank(currentObs.getVariableValue())
                            ){
                                sb.add("Clinical stage is null");
                            }
                            break;
                        case ARTCommencementConceptsUtils.FUNCTIONAL_STATUS:
                            if (currentObs.getValueCoded() == 0.0 ||
                                    StringUtils.isBlank(currentObs.getVariableValue())
                            ){
                                sb.add("Functional status is null");
                            }
                            break;
                        case ARTCommencementConceptsUtils.PREGNANCY_STATUS:
                            if(Objects.equals(GenderUtils.FEMALE.getType(), demographicsType.getGender()) &&
                                    (currentObs.getValueCoded() == 0.0 ||
                                            StringUtils.isBlank(currentObs.getVariableValue()))
                            ) {
                                sb.add("Pregnancy Status is null");
                            }
                            break;
                        case ARTCommencementConceptsUtils.INITIAL_REGIMEN_LINE:
                            if (currentObs.getValueCoded() == 0.0 ||
                                    StringUtils.isBlank(currentObs.getVariableValue())
                            ){
                                sb.add("Initial regimen line is null, must select one of adult or paediatric");
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

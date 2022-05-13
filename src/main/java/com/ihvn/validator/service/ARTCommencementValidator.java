package com.ihvn.validator.service;

import com.ihvn.validator.models.DemographicsType;
import com.ihvn.validator.models.EncounterErrors;
import com.ihvn.validator.models.EncounterType;
import com.ihvn.validator.models.ObsType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ARTCommencementValidator {

    List<EncounterErrors> validate(List<EncounterType> encounters, List<ObsType> artCommenceObs, DemographicsType type);

}

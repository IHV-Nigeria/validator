package com.ihvn.validator.service;

import com.ihvn.validator.models.DemographicsType;
import com.ihvn.validator.models.EncounterType;
import com.ihvn.validator.models.ObsType;

import java.util.List;

public interface ARTCommencementValidator {

    void validate(List<EncounterType> encounters, List<ObsType> artCommenceObs, DemographicsType type);

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihvn.validator.service;

import com.ihvn.validator.models.DemographicsType;
import com.ihvn.validator.models.EncounterErrors;
import com.ihvn.validator.models.EncounterType;
import com.ihvn.validator.models.ObsType;
import java.util.List;

/**
 *
 * @author MORRISON.I
 */
public interface LaboratoryValidator {
    List<EncounterErrors> validate(List<EncounterType> encounters, List<ObsType> labObs, DemographicsType demographicsType);
}

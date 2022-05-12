/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihvn.validator.utils;

import com.ihvn.validator.models.ObsType;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author MORRISON.I
 */
public class ConstantsUtils {

    public static final int PharmacyEncounterType = 13;
    public static final int CareCardEncounterType = 12;
    public static final int LabEncounterType = 11;

    public static final  int ART_COMMENCEMENT_TYPE = 25;

    public static Optional<ObsType> getObsbyConceptID(int conceptId, List<ObsType> obsList) {

        return obsList.stream().filter(a -> a.getConceptId() == conceptId).findAny();

    }

}

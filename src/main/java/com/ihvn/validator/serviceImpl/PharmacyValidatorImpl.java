/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihvn.validator.serviceImpl;

import com.ihvn.validator.models.EncounterType;
import com.ihvn.validator.models.ObsType;
import com.ihvn.validator.service.PharmacyValidator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author MORRISON.I
 */
public class PharmacyValidatorImpl implements PharmacyValidator{

    @Override
    public void validate(List<EncounterType> encounters, List<ObsType> pharmObs) {
       encounters.stream().forEach(a -> {
           List<ObsType> encObs = pharmObs.stream()
                   .filter(b -> b.getEncounterId()==a.getEncounterId())
                   .collect(Collectors.toList());
           
           //validate all obs for the Encounter
           
       });
    }
    
}

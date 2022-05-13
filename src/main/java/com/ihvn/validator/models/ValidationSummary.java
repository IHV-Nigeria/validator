/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihvn.validator.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author MORRISON.I
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationSummary {

    private UUID patientUuid;
    private List<EncounterErrors> errors;

}

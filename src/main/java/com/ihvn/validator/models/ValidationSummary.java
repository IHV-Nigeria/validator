/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihvn.validator.models;

import java.util.List;

/**
 *
 * @author MORRISON.I
 */
public class ValidationSummary {

    private String patientuuid;
    private List<EncounterErrors> errors;

    public String getPatientuuid() {
        return patientuuid;
    }

    public void setPatientuuid(String patientuuid) {
        this.patientuuid = patientuuid;
    }

    public List<EncounterErrors> getErrors() {
        return errors;
    }

    public void setErrors(List<EncounterErrors> errors) {
        this.errors = errors;
    }

}

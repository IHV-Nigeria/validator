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
public class EncounterErrors {
    
    private int encounterId;
    private int formId;
    private List<ObsError> errors;

    public int getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(int encounterId) {
        this.encounterId = encounterId;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public List<ObsError> getErrors() {
        return errors;
    }

    public void setErrors(List<ObsError> errors) {
        this.errors = errors;
    }
    
    
    
    
}

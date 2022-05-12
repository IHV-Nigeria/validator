/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihvn.validator.models;

/**
 *
 * @author MORRISON.I
 */
public class DataError {

    public String error;
    public ErrorLevel errorLevel;

    public DataError(String error, ErrorLevel errorLevel) {
        this.error = error;
        this.errorLevel = errorLevel;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ErrorLevel getErrorLevel() {
        return errorLevel;
    }

    public void setErrorLevel(ErrorLevel errorLevel) {
        this.errorLevel = errorLevel;
    }
}

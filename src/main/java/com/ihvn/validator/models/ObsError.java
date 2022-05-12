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
public class ObsError {

    private List<DataError> error;
    private int obsId;

    public List<DataError> getError() {
        return error;
    }

    public void setError(List<DataError> error) {
        this.error = error;
    }

    public int getObsId() {
        return obsId;
    }

    public void setObsId(int obsId) {
        this.obsId = obsId;
    }


}

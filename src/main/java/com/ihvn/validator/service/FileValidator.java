/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihvn.validator.service;

import com.ihvn.validator.models.Container;
import org.springframework.stereotype.Service;

/**
 *
 * @author MORRISON.I
 */

@Service
public interface FileValidator {
    void validationFile(Container container);
}

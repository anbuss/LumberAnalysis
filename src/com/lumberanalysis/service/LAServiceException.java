/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.service;

/**
 *
 * @author samidoss
 */
public class LAServiceException extends Exception {
    private static final long serialVersionUID = 1L;

    public LAServiceException(Throwable e) {
        super(e);
    }

    public LAServiceException(String message, Throwable e) {
         super(message, e);
    }
    
}

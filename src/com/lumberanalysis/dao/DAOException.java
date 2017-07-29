/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.dao;

/**
 *
 * @author samidoss
 */

public class DAOException extends Exception {
    private static final long serialVersionUID = 1L;

    public DAOException(Throwable e) {
        super(e.getMessage(),e);
    }
    public DAOException(String message, Throwable e) {
        super(message+"\nCause Message: "+e.getMessage(),e);
    }
}

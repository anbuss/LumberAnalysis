/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.webapp.util;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 *
 * @author samidoss
 */
public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory{
    private ExceptionHandlerFactory parent;
 
  public CustomExceptionHandlerFactory(ExceptionHandlerFactory parent) {
    this.parent = parent;
  }
 
  @Override
  public ExceptionHandler getExceptionHandler() {
    ExceptionHandler result = (ExceptionHandler)new CustomExceptionHandler(parent.getExceptionHandler());
    return result;
  } 
}

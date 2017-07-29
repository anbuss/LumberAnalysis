/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.dao;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 *
 * @author samidoss
 */
public class FilterCriteria extends LinkedHashMap<String, Object> implements Serializable{
    private static final long serialVersionUID = 1L;
    
    public FilterCriteria(){
        super();
    }
    @Override
    public Object put(String k, Object v){
        
        if (v == null) return v;
        
        if (v instanceof String){
           return ("".equals(v))?"":super.put(k, v);
        }
        return super.put(k, v);
    }
    
}

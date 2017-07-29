/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.service;

import com.lumberanalysis.model.PSObject;
import java.util.List;

/**
 *
 * @author samidoss
 */

public interface PSDataManager extends GenericManager<PSObject, Long>{
    public List<PSObject> getPSDatas();
    
    public List<PSObject> getPSDatas(String appName, String errorCode, String tag_Message);
    
    public List<String> getDistinctAppNames();
    
}

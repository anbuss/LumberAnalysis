/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.service;

import com.lumberanalysis.model.LastBackground;
import com.lumberanalysis.model.LastBackground.Status;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author samidoss
 */

public interface BackgroundJobsManager extends GenericManager<LastBackground, Long>{
    
    public LastBackground getLastActivity(String appName, Status status);

    public int batchInsert(List<Map> result, String tableName, String appName);

	public Collection<? extends Map> getResultList(String string, Object object);
    
}

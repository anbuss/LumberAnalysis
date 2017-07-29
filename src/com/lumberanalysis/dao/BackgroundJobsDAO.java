/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.dao;

import com.lumberanalysis.model.LastBackground;
import com.lumberanalysis.model.LastBackground.Status;
import java.util.List;
import java.util.Map;

/**
 *
 * @author samidoss
 */
public interface BackgroundJobsDAO extends GenericDao<LastBackground, Long> {
    
   public LastBackground getLastActivity(String appName, Status status);

    public int batchInsert(List<Map> result, String query, String appName);
   
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.service.impl;

import com.lumberanalysis.dao.BackgroundJobsDAO;
import com.lumberanalysis.model.LastBackground;
import com.lumberanalysis.model.LastBackground.Status;
import com.lumberanalysis.service.BackgroundJobsManager;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author samidoss
 */
@Service("BackgroundJobsManager")
public class BackgroundJobsManagerImpl extends GenericManagerImpl<LastBackground, Long> implements BackgroundJobsManager {
    private static final long serialVersionUID = 1L;

    private BackgroundJobsDAO backgroundJobsDao;

    @Autowired
    public BackgroundJobsManagerImpl(BackgroundJobsDAO psDataDaoToset) {
        super(psDataDaoToset);
        this.backgroundJobsDao = psDataDaoToset;
    }

    /**
     * @return the backgroundJobsDao
     */
    public BackgroundJobsDAO getBackgroundJobsDao() {
        return backgroundJobsDao;
    }

    @Override
    public LastBackground getLastActivity(String appName, Status status) {
        return getBackgroundJobsDao().getLastActivity(appName,status);
    }

    @Override
    public int batchInsert(List<Map> result, String tableName,String appName) {
        if (result != null && result.size() > 1){
            Map columunList = result.get(0);
            
            String query = "insert into "+ tableName +" "+(columunList.keySet().toString().replace('[', '(')).replace(']', ')')+ " VALUES (";
            for(int i=1;i<=columunList.size();i++){
                query += "?";
                 if (i+1 <= columunList.size())   
                     query += ", ";
            }
            query += ") ";
            return getBackgroundJobsDao().batchInsert(result, query,appName);
        }
        return -1;
    }

	@Override
	public Collection<? extends Map> getResultList(String string, Object object) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}

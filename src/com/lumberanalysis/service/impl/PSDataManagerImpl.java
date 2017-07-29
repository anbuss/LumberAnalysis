/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.service.impl;

import com.lumberanalysis.dao.FilterCriteria;
import com.lumberanalysis.dao.PSDataDAO;
import com.lumberanalysis.model.PSObject;
import com.lumberanalysis.service.PSDataManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author samidoss
 */
@Service("psDataManager")
public class PSDataManagerImpl extends GenericManagerImpl<PSObject, Long> implements PSDataManager {
    private static final long serialVersionUID = 1L;

    private PSDataDAO psDataDao;

    @Autowired
    public PSDataManagerImpl(PSDataDAO psDataDaoToset) {
        super(psDataDaoToset);
        this.psDataDao = psDataDaoToset;
    }

    @Override
    public List<PSObject> getPSDatas() {
        return getPsDataDao().getAll();
    }

    @Override
    public List<PSObject> getPSDatas(String appName, String errorCode, String tag_Message) {
        FilterCriteria fc = new FilterCriteria();
        fc.put("providerName", appName);
        fc.put("providerIssueId", errorCode);
        fc.put("tag_message", tag_Message);
        
        return getPsDataDao().getPSDataByCriteria(fc);
    }

    @Override
    public List<String> getDistinctAppNames() {
        return getPsDataDao().getDistinctAppNames();
    }

    /**
     * @return the psDataDao
     */
    public PSDataDAO getPsDataDao() {
        return psDataDao;
    }
}

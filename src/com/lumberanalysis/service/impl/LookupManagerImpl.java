package com.lumberanalysis.service.impl;

import com.lumberanalysis.dao.LookupDao;
import com.lumberanalysis.model.LabelValue;
import com.lumberanalysis.model.Role;
import com.lumberanalysis.service.LookupManager;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.stereotype.Service;



@Service("lookupManager")
public class LookupManagerImpl implements LookupManager {
    @Inject
    @Named("lookupDao")
    private LookupDao lookupDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LabelValue> getAllRoles() {
        List<Role> roles = lookupDao.getRoles();
        List<LabelValue> list = new ArrayList<LabelValue>();

        for (Role role1 : roles) {
            list.add(new LabelValue(role1.getName(), role1.getName()));
        }

        return list;
    }

    public LookupDao getLookupDao() {
        return lookupDao;
    }

    public void setLookupDao(LookupDao lookupDao) {
        this.lookupDao = lookupDao;
    }
}

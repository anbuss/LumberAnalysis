package com.lumberanalysis.service.impl;

import com.lumberanalysis.dao.DAOException;
import com.lumberanalysis.dao.RoleDao;
import com.lumberanalysis.model.Role;
import com.lumberanalysis.service.LAServiceException;
import com.lumberanalysis.service.RoleManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("roleManager")
public class RoleManagerImpl extends GenericManagerImpl<Role, Long> implements RoleManager {
    private static final long serialVersionUID = 1L;
    private RoleDao roleDao;

    @Autowired
    public RoleManagerImpl(RoleDao roleDao) {
        super(roleDao);
        this.roleDao = roleDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Role> getRoles(Role role) {
        return dao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role getRole(String rolename) {
        return getRoleDao().getRoleByName(rolename);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role saveRole(Role role)throws LAServiceException{
        try {
            return dao.save(role);
        } catch (DAOException ex) {
            log.error("Error while Savinge Role's", ex);
            throw new LAServiceException(ex);
        }       
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeRole(String rolename) {
        getRoleDao().removeRole(rolename);
    }
    
    public List<Role> getRolesByListIds(Long[] ids){
        List<Long> rol0 = new ArrayList<Long>(Arrays.asList(ids));
        return getRoleDao().getRolesByListIds(rol0);
    }
    @Override
    public List<Role> getRolesByIdList(Long[] id){
        List<Role> l = new ArrayList<Role>(id.length);
        for(Long ll:id){    
            l.add(getRoleDao().get(ll));
        }
        return l;
    }

    /**
     * @return the roleDao
     */
    public RoleDao getRoleDao() {
        return roleDao;
    }
}
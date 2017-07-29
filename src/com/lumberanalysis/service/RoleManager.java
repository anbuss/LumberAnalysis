package com.lumberanalysis.service;

import com.lumberanalysis.model.Role;
import java.util.List;

/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 */
public interface RoleManager extends GenericManager<Role, Long> {
    /**
     * {@inheritDoc}
     */
    List<Role> getRoles(Role role);

    /**
     * {@inheritDoc}
     */
    Role getRole(String rolename);

    /**
     * {@inheritDoc}
     */
    Role saveRole(Role role) throws LAServiceException;
    
    public List<Role> getRolesByIdList(Long[] id);

    /**
     * {@inheritDoc}
     */
    void removeRole(String rolename);
}

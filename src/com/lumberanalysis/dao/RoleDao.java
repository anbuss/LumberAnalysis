package com.lumberanalysis.dao;

import com.lumberanalysis.model.Role;
import java.util.List;

/**
 * Role Data Access Object (DAO) interface.
 *
 * @author Samidoss
 */
public interface RoleDao extends GenericDao<Role, Long> {
    /**
     * Gets role information based on rolename
     * @param rolename the rolename
     * @return populated role object
     */
    Role getRoleByName(String rolename);
    
    public List<Role> getRolesByListIds(List<Long> ids);

    /**
     * Removes a role from the database by name
     * @param rolename the role's rolename
     */
    void removeRole(String rolename);
}

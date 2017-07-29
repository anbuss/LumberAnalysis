package com.lumberanalysis.dao.hibernate;

import com.lumberanalysis.dao.RoleDao;
import com.lumberanalysis.model.Role;
import java.util.List;
import org.springframework.stereotype.Repository;


@Repository
public class RoleDaoHibernate extends GenericDaoHibernate<Role, Long> implements RoleDao {

    /**
     * Constructor to create a Generics-based version using Role as the entity
     */
    public RoleDaoHibernate() {
        super(Role.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role getRoleByName(String rolename) {
        @SuppressWarnings("unchecked")
        List<Role> roles = getHibernateTemplate().find("from Role where name=?", rolename);
        if (roles.isEmpty()) {
            return null;
        } else {
            return roles.get(0);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Role> getRolesByListIds(List<Long> ids) {

        List<Role> roles = getHibernateTemplate().find("from Role where id in (?)", ids);
        return roles;
    }

    /**
     * {@inheritDoc}
     */
    public void removeRole(String rolename) {
        Object role = getRoleByName(rolename);
        getHibernateTemplate().delete(role);
    }
}

package com.lumberanalysis.dao.hibernate;

import java.util.List;

import com.lumberanalysis.dao.LookupDao;
import com.lumberanalysis.model.Role;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;


@Repository("lookupDao")
public class LookupDaoHibernate implements LookupDao {
    private Logger log = Logger.getLogger(LookupDaoHibernate.class);
    private HibernateTemplate hibernateTemplate;

    @Autowired
    public LookupDaoHibernate(SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Role> getRoles() {
        log.debug("Retrieving all role names...");

        return hibernateTemplate.find("from Role order by name");
    }
}

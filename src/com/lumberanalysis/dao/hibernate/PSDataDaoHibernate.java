/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.dao.hibernate;

import com.lumberanalysis.dao.FilterCriteria;
import com.lumberanalysis.dao.PSDataDAO;
import com.lumberanalysis.model.PSObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author samidoss
 */
@Repository
public class PSDataDaoHibernate extends GenericDaoHibernate<PSObject, Long> implements PSDataDAO{

     public PSDataDaoHibernate() {
        super(PSObject.class);
    }
    
    @Override
    public List<PSObject> getPSDatas() {
        return getHibernateTemplate().find("from PSObject u order by upper(u.providerName), u.providerVersion, u.providerIssueId");
    }

    @Override
    public PSObject savePSData(PSObject psData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<PSObject> getPSDataByCriteria(FilterCriteria criteria) {
        Criteria cri = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(PSObject.class);
        for(Entry<String,Object> e:criteria.entrySet()){
            String key = e.getKey();
            if (key.equals("providerIssueId")){
                cri.add( Restrictions.like("providerIssueId", e.getValue()) );
            }else{
            if (key.equals("tag_message")) {
                //cri.add( Restrictions.like("name", "Fritz%") )
                cri.add(Restrictions.or(
                            Restrictions.or(
                                Restrictions.like("shortDescription", "%" + e.getValue() + "%"),
                                Restrictions.like("cause", "%" + e.getValue() + "%")),
                            Restrictions.or(
                                Restrictions.like("fix", "%" + e.getValue() + "%"),
                                Restrictions.like("tags", "%" + e.getValue() + "%"))
                            )
                        );
            }
            }
            
        }
        return cri.list();
    }

    @Override
    /**
     * Load only the distinct application name only, DON'T load the full object.
     */
    //TODO : DON"T load full object.
    public List<String> getDistinctAppNames() {
       // getHibernateTemplate().find("from PSObject u order by upper(u.providerName), u.providerVersion, u.providerIssueId");
        ArrayList<String> l = new ArrayList<String>();
        return l;
    }
    
}

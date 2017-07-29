/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.dao.hibernate;

import com.logviewer.log.AppInfo;
import com.logviewer.log.exception.LogViewerConfigException;
import com.logviewer.log.util.LogHelper;
import com.logviewer.log.util.LogUtil;
import com.lumberanalysis.dao.BackgroundJobsDAO;
import com.lumberanalysis.model.LastBackground;
import com.lumberanalysis.model.LastBackground.Status;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author samidoss
 */
@Repository
public class BackgroundJobsDaoHibernate extends GenericDaoHibernate<LastBackground, Long> implements BackgroundJobsDAO{
    private int BATCH_SIZE = 32;

     public BackgroundJobsDaoHibernate() {
        super(LastBackground.class);
    }
    

    @Override
    public LastBackground getLastActivity(String appName, Status status) {
        Criteria cri = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(LastBackground.class);
        
                cri.add( Restrictions.eq("appName", appName));
                cri.add( Restrictions.eq("status", status));
            
        return (LastBackground) cri.uniqueResult();
    }
    public int batchInsert(List<Map> list, String query, String appName) {
        try {
            SQLQuery qu = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(query);
            AppInfo appInfo = null;
            try {
                appInfo = LogHelper.getAppInfoByAppName(appName);
            } catch (LogViewerConfigException ex) {
                Logger.getLogger(BackgroundJobsDaoHibernate.class.getName()).log(Level.SEVERE, null, ex);
            }
            Date d;
            for (int i = 0; i < list.size(); i++) {
                try {
                    Map row = list.get(i);
                    int j = 0;
                    for (Iterator<Entry<String, String>> it = row.entrySet().iterator(); it.hasNext();) {
                        Entry<String, String> e = it.next();
                        if (e.getKey().equals("TIMESTAMP")) {
                            d = LogUtil.getDatePattern(appInfo.getPatterns(0).getDatePattern(), e.getValue(), (appInfo.getPatterns(0).getDatePattern().contains("z")) ? appInfo.getTimeZone() : null);
                            qu.setParameter(j++, d);
                        } else {
                            qu.setParameter(j++, e.getValue());
                        }
                    }
                    qu.executeUpdate();
                } catch (ParseException ex) {
                    Logger.getLogger(BackgroundJobsDaoHibernate.class.getName()).log(Level.SEVERE, null, ex);
                    //Skip this record and move it to next one.
                }
                
                if (i % BATCH_SIZE == 0) { 
                    //flush a batch of inserts and release memory:
                    getHibernateTemplate().flush();
                    getHibernateTemplate().clear();
                }

            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        //   tx.commit();
        return 0;
    }
   
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.webapp.action;

import com.logviewer.log.AppInfo;
import com.logviewer.log.Constant;
import com.logviewer.log.LogPattern;
import com.logviewer.log.logs.AbstractLogs;
import com.logviewer.log.logs.Logs;
import com.lumberanalysis.service.BackgroundJobsManager;
import com.lumberanalysis.service.LAServiceException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author samidoss
 */
public final class TwoDBCLog extends AbstractLogs implements Logs {
     private List<String> pattern = new ArrayList<String>();
     
    public TwoDBCLog() {
        super();
    }
    
    public TwoDBCLog(AppInfo logAppinfo) {
        super(logAppinfo);
        getLogFormatFromLayout(appInfo.getPatterns(0).getLogPattern());
    }
    
   
    @Override
    public void getLogFormatFromLayout(String layout) {         
             String[] res1 = layout.split(appInfo.getPatterns(0).getLogSplitChar());
             loadFormatFromConverters(Arrays.asList(res1));
    }
    
   
    public void loadFormatFromConverters(List<String> converters) {
        int i = 0;
        for (Iterator iter = converters.iterator(); iter.hasNext();) {
            String converter = (String) iter.next();
            if (converter != null) {
                converter = converter.replace('\\', ' ').trim();                
                if (converter.equals("")) {
                    continue;
                }
                if (converter.toUpperCase().equals(Constant.MESSAGE)) {
                    messageIndex = i;
                }else if (converter.toUpperCase().equals(Constant.TIMESTAMP)) {
                     dateTimeIndex = i;
                }
                pattern.add(converter);
            }
            i++;
        }
    }
    public void toLogs() {
        try {
            ApplicationContext springContext =
                    WebApplicationContextUtils.getWebApplicationContext(ContextLoaderListener.getCurrentWebApplicationContext().getServletContext());

            BackgroundJobsManager ser =  (BackgroundJobsManager) springContext.getBean("BackgroundJobsManager");
            
            StringBuilder query = new StringBuilder();
            query.append("SELECT ");
            for(String selectClause : getPattern() ){
                query.append(selectClause).append(",");
            }
            query.deleteCharAt(query.length()-1)
                 .append(" From ").append(appInfo.getTableName());
             String stringToAdd = " WHERE ";
                boolean isNeedtoAdd = true;
                if (getSearchLog().getType() != null && !"".equals(getSearchLog().getType().trim())) {
                    MessageFormat mf = new MessageFormat(appInfo.getTypeColumn());
                    String[] arrval = {getSearchLog().getType()};
                    query.append((isNeedtoAdd) ? stringToAdd : " ");
                    query.append(mf.format(arrval));
                    stringToAdd = " AND ";
                }
                if (getSearchLog().getClassName() != null && !getSearchLog().getClassName().trim().equals("")) {
                    MessageFormat mf = new MessageFormat(appInfo.getClassColumn());
                    String[] arrval = {getSearchLog().getClassName()};
                    query.append((isNeedtoAdd) ? stringToAdd : " ");
                    query.append(mf.format(arrval));
                    stringToAdd = " AND ";
                }

                //Thred info
                if (getSearchLog().getThreadName() != null && !getSearchLog().getThreadName().trim().equals("")) {
                    MessageFormat mf = new MessageFormat(appInfo.getThreadColumn());
                    String[] arrval = {getSearchLog().getThreadName()};
                    query.append((isNeedtoAdd) ? stringToAdd : " ");
                    query.append(mf.format(arrval));
                    stringToAdd = " AND ";
                }
                
                if (getSearchLog().getNdc() != null && !getSearchLog().getNdc().trim().equals("")) {
                    MessageFormat mf = new MessageFormat(appInfo.getNdcColumn());
                    String[] arrval = {getSearchLog().getNdc()};
                    query.append((isNeedtoAdd) ? stringToAdd : " ");
                    query.append(mf.format(arrval));
                    stringToAdd = " AND ";
                }
                
                if (getSearchLog().getLog() != null && !getSearchLog().getLog().trim().equals("")) {
                    MessageFormat mf = new MessageFormat(appInfo.getMessageColumn());
                    String[] arrval = {getSearchLog().getLog()};
                    query.append((isNeedtoAdd) ? stringToAdd : " ");
                    query.append(mf.format(arrval));
                    //stringToAdd = " AND ";
                }
           // query.append(SET MAX ROW)
            getValues().addAll(ser.getResultList(query.toString(), null));
        } catch (Exception ex) {
            Logger.getLogger(TwoDBCLog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Deprecated
    private void toLogs1() {
        Statement sqlSta = null;
        Connection con = null;
        try {
            StringBuilder buffer = new StringBuilder();
            String stringToAdd = " WHERE ";
            boolean isNeedtoAdd = true;
            if (getSearchLog().getType() != null && !"".equals(getSearchLog().getType().trim())) {
                MessageFormat mf = new MessageFormat(appInfo.getTypeColumn());
                String[] arrval = {getSearchLog().getType()};
                buffer.append((isNeedtoAdd) ? stringToAdd : " ");
                buffer.append(mf.format(arrval));
                stringToAdd = " AND ";
            }
            if (getSearchLog().getClassName() != null && !getSearchLog().getClassName().trim().equals("")) {
                MessageFormat mf = new MessageFormat(appInfo.getClassColumn());
                String[] arrval = {getSearchLog().getClassName()};
                buffer.append((isNeedtoAdd) ? stringToAdd : " ");
                buffer.append(mf.format(arrval));
                stringToAdd = " AND ";
            }

            //Thred info
            if (getSearchLog().getThreadName() != null && !getSearchLog().getThreadName().trim().equals("")) {
                MessageFormat mf = new MessageFormat(appInfo.getThreadColumn());
                String[] arrval = {getSearchLog().getThreadName()};
                buffer.append((isNeedtoAdd) ? stringToAdd : " ");
                buffer.append(mf.format(arrval));
                stringToAdd = " AND ";
            }
            
            if (getSearchLog().getNdc() != null && !getSearchLog().getNdc().trim().equals("")) {
                MessageFormat mf = new MessageFormat(appInfo.getNdcColumn());
                String[] arrval = {getSearchLog().getNdc()};
                buffer.append((isNeedtoAdd) ? stringToAdd : " ");
                buffer.append(mf.format(arrval));
                stringToAdd = " AND ";
            }
            
            if (getSearchLog().getLog() != null && !getSearchLog().getLog().trim().equals("")) {
                MessageFormat mf = new MessageFormat(appInfo.getMessageColumn());
                String[] arrval = {getSearchLog().getLog()};
                buffer.append((isNeedtoAdd) ? stringToAdd : " ");
                buffer.append(mf.format(arrval));
                //stringToAdd = " AND ";
            }
            getLog().info("QUERY : "+appInfo.getQuery() + " " + buffer.toString());
      //      JDBCHelper helper = new JDBCHelper(appInfo.getURL(), appInfo.getDriver(), appInfo.getUserName(), appInfo.getPassword());
           // ResultSet rs = helper.execute(appInfo.getQuery() + " " + buffer.toString(), null, appInfo.getCutoffLimt());
          //  sqlSta =  helper.getStatement();
       //     sqlSta.setFetchSize(appInfo.getCutoffLimt());
            ResultSet rs = sqlSta.executeQuery(appInfo.getQuery() + " " + buffer.toString());
            if (rs != null) {
                          
                    while (rs.next()) {
                        Map<String, Object> lRecs = new LinkedHashMap<String, Object>();
                        for (int i = 0; i < pattern.size(); i++) {                        
                            lRecs.put(pattern.get(i), rs.getObject(i+1).toString());
                        }
                        getValues().add(lRecs);
                    }                
               
            }
        } catch (SQLException ex) {
            try {
                Logger.getLogger(TwoDBCLog.class.getName()).log(Level.SEVERE, null, ex);
                if (sqlSta != null )
                    con = sqlSta.getConnection();
                sqlSta.close();
                con.close();
            } catch (SQLException ex1) {
                Logger.getLogger(TwoDBCLog.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
               if (sqlSta != null )
                    con = sqlSta.getConnection();
                sqlSta.close();
                con.close();
            } catch (SQLException ex1) {
                Logger.getLogger(TwoDBCLog.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    @Override
    public String getType() {
        return "TwoDBCLog";
    }
    
    @Override
    public void process(List<String> l, LogPattern lp) {
        setSearchLog(lp);
        toLogs();
    }
    @Override
    public void processReport(List<String> l, LogPattern lp) {
        setSearchLog(lp);
        toLogs();
    }

    @Override
    public List<String> getPattern() {
       return pattern;
    }
    
   /* @Override
    protected List<String> getPattern(String currentLine) {
        return getPattern();
    }*/

    @Override
    public Map<String, String> getPatternData(String currentLine) {
        return  constructDataMap(currentLine);
    }
}

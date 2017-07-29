/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.webapp.action.reports;

import com.logviewer.log.Constant;
import com.logviewer.log.LogPattern;
import com.logviewer.log.engine.LogReport;
import com.logviewer.log.report.ReportData;
import java.util.Calendar;
import java.util.Map;

/**
 *
 * @author samidoss
 */
public class FirewallReportData extends ReportData{

    public FirewallReportData() {
        super();
    }
    
    public FirewallReportData(LogPattern ser) {
        super(ser);
    }
    @Override
    public void init() {
        Calendar curTime = Calendar.getInstance();
       
        getSearchCriteria().setEndDateTime(curTime.getTime());
        Calendar startTime = (Calendar) curTime.clone();
        startTime.set(Calendar.HOUR, -24);
        getSearchCriteria().setStartDateTime(startTime.getTime());
        
        
    }
    @SuppressWarnings("unchecked")
    @Override
    public void manuplateData(){ 
        //addX(getSearchCriteria().getAppName());
        String key = Constant.LEVEL;
        for(Map.Entry<String,Map<String,Map<String,LogReport.GroupByCount>>> en: getResult().entrySet()){ 
            addX(en.getKey());
            Map<String,Map<String,LogReport.GroupByCount>> forApp = en.getValue();
            Map<String,LogReport.GroupByCount> data = (Map) forApp.get(key);
            for(Map.Entry<String,LogReport.GroupByCount> eachApp: data.entrySet()){
                addY((String)eachApp.getValue().getKey(), (int)eachApp.getValue().getCount());                
            }
            resetCurrentSeriesLength();
        }        
    }

    @Override
    public String getReportName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}

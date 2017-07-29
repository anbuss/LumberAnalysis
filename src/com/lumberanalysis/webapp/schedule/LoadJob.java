/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.webapp.schedule;

import com.logviewer.log.AppInfo;
import com.logviewer.log.ConstructPOJO;
import com.logviewer.log.LogPattern;
import com.logviewer.log.exception.LogViewerConfigException;
import com.logviewer.log.util.LogHelper;
import com.lumberanalysis.model.LastBackground;
import com.lumberanalysis.service.BackgroundJobsManager;
import com.lumberanalysis.service.LAServiceException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author samidoss
 */
@Component(value = "LoadJob")
public class LoadJob extends LAJobs {

    @Inject
    @Named("BackgroundJobsManager")
    private BackgroundJobsManager bgjManager;
   
    @Override
    public void process(JobExecutionContext jec){

        String appName = "sa.Prod.M1";//getNextJobToRun();        
        
        try {
            AppInfo appInfo = LogHelper.getAppInfoByAppName(appName);
            LastBackground lb = new LastBackground();
            try {
                lb.setAppName(appInfo.getAppName());

                lb.setStatusInternal('I');

                lb = bgjManager.save(lb);
            } catch (Exception e) {
                e.printStackTrace();
            }
            LastBackground lastLB = null;
            try {
                lastLB = bgjManager.getLastActivity(appInfo.getAppName(), LastBackground.Status.Compleated);
            } catch (Exception e) {
                e.printStackTrace();
            }
            lb.setLastExecutedStartTime(new java.sql.Date(System.currentTimeMillis()));
            lb.setStatusInternal('S');
            try {
                lb = bgjManager.save(lb);
            } catch (LAServiceException ex) {
                Logger.getLogger(LoadJob.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                Date startTime = (lastLB != null) ? ((lastLB.getLastExecutedCompleatedTime() != null) ? lastLB.getLastExecutedCompleatedTime() : null) : null;
                LogHelper lh = new LogHelper();
                //  List<String> fileList = lh.getListofFiles(appInfo.getAppName(), startTime , lb.getLastExecutedStartTime());
                ConstructPOJO pojo = new ConstructPOJO();


                LogPattern lp = new LogPattern();
                lp.setDatePattern(appInfo.getPatterns(0).getDatePattern());
                SimpleDateFormat s = new SimpleDateFormat(appInfo.getPatterns(0).getDatePattern());

                lp.setCriteria((startTime != null) ? s.format(startTime) : null, s.format(lb.getLastExecutedStartTime()), "", "", "", "", "", "");
                List<Map> result = pojo.loadLogs(appInfo.getAppName(), lp);
                bgjManager.batchInsert(result, appInfo.getTableName(), appName);

                lb.setStatusInternal('C');
                lb = bgjManager.save(lb);

            } catch (Exception e) {
                ;
            }
        } catch (LogViewerConfigException ex) {
            Logger.getLogger(LoadJob.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void start(JobExecutionContext jec) {
        bgjManager = (BackgroundJobsManager) getBean("BackgroundJobsManager");
    }

    @Override
    public void end(JobExecutionContext jec) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
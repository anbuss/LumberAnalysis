/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.webapp.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author samidoss
 */
abstract class LAJobs extends QuartzJobBean{

    @Override
    protected void executeInternal(JobExecutionContext jec) throws JobExecutionException {
        start(jec);
        process(jec);
        end(jec);
    }
    public abstract void start(JobExecutionContext jec);
    public abstract void process(JobExecutionContext jec);
    public abstract void end(JobExecutionContext jec);
    
     public Object getBean(String beanName){
        ApplicationContext springContext =
                WebApplicationContextUtils.getWebApplicationContext(ContextLoaderListener.getCurrentWebApplicationContext().getServletContext());

        return springContext.getBean(beanName); 
    }
   
}

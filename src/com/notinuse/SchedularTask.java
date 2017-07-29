/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.notinuse/*lumberanalysis.webapp.action*/;

import com.lumberanalysis.webapp.action.usermanagement.LoginCheck;
import com.lumberanalysis.service.UserManager;
import javax.mail.MessagingException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 *
 * @author samidoss
 */
public class SchedularTask extends QuartzJobBean {

    private UserManager userManager;
    private LoginCheck loginCheck;

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public void setLoginCheck(LoginCheck loginCheck) {
        this.loginCheck = loginCheck;
    }

    @Override
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {
        try {
            loginCheck.sendMaessage();
        } catch (MessagingException exception) {
            System.out.print(exception.getMessage());

        }
    }
}

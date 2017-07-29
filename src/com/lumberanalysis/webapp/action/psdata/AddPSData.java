/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.webapp.action.psdata;

import com.lumberanalysis.model.PSObject;
import com.lumberanalysis.service.LAServiceException;
import com.lumberanalysis.service.PSDataManager;
import com.lumberanalysis.webapp.action.BasePage;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author samidoss
 */
@Named("psBeanAdd")
@Scope("view")
public class AddPSData extends BasePage implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private PSObject psData;
    
    @Inject
    @Named("psDataManager")
    private PSDataManager psDataManager;

    @PostConstruct
    private void init() {
        String psid = getParameter("psid");
        if (psid != null)
            psData = getPsDataManager().get(Long.valueOf(psid));
        else{
            if (psData == null)
                psData = new PSObject();
        }
    }

    public PSObject getPsData() {
        return psData;
    }

    public void setPsData(PSObject psData) {
        this.psData = psData;
    }

    public String save() throws LAServiceException{
      try{
       PSObject save = psDataManager.save(psData);
       addMessage("Saved.");
       return "";
      }catch(LAServiceException e){
        getLog().error(e.getMessage());
        throw e;
        //return "error";
      }
     // return "";
    }

    public PSDataManager getPsDataManager() {
        return psDataManager;
    }

    public void setPsDataManager(PSDataManager psDataManager) {
        this.psDataManager = psDataManager;
    }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.webapp.action.psdata;

import com.logviewer.log.ConstructPOJO;
import com.logviewer.log.view.ApplicationNodeForView;
import com.lumberanalysis.model.PSObject;
import com.lumberanalysis.service.PSDataManager;
import com.lumberanalysis.webapp.action.BasePage;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author samidoss
 */
@Named("psBean")
//@Scope("view")
@Scope("request")
public class PSData extends BasePage implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject
    @Named("psDataManager")
    private PSDataManager psDataManager;
    
     private static Map<String, Object> infoType;
   

    static {
        infoType = new LinkedHashMap<String, Object>();
        ConstructPOJO pojom = new ConstructPOJO();
        for(ApplicationNodeForView node : pojom.getAppLeafListByRole(null)){
          infoType.put(node.getText(),node.getId());  //label, value
        }
        
    }

    public Map<String, Object> getAppNames() {
        return infoType;
    }
   
    private String[] categorytype;
    
    private String appName = null;
    private String errorCode;
    private String tag_Message;
    
    private DataModel<PSObject> result = null;
    
    @PostConstruct
    private void init(){
       /* PSObject p = new PSObject();
        
        p.setProviderName("Weblogic app server");
        p.setProviderVersion("10.3");
        p.setProviderIssueId("BEA-00001");
        p.setShortDescription("Pleser populate the table;");
        result.add(p); */
       // result = new ListDataModel<PSObject>(getPsDataManager().getPSDatas());
    }


     public void go(ActionEvent event) {
        result = new ListDataModel<PSObject>(getPsDataManager().getPSDatas(appName,errorCode,tag_Message));
    }
    
    public DataModel<PSObject> getResult() {
        return result;
    }

    public void setResult(List<PSObject> result) {
       // this.result = result;
    }

    public PSDataManager getPsDataManager() {
        return psDataManager;
    }

    public void setPsDataManager(PSDataManager psDataManager) {
        this.psDataManager = psDataManager;
    }
   // private String rowStart = "</TD><TR ><TD colspan=\"4\">";
    private String rowEnd = "</TD></TR><TR style=\"display: none;\"><TD>";

    public String getRowEnd() {
        return rowEnd;
    }

    public void setRowEnd(String rowEnd) {
       // this.rowEnd = rowEnd;
    }

    public String getRowStart() {
        return "</TD><TR id=\"des"+result.getRowIndex() + 1+"\" style=\"text-align:left;background:#FFFFFF;font-size:12px;border:1px solid yellow;display: none;\"><TD colspan=\"4\">";
    }

    public void setRowStart(String rowStart) {
     //   this.rowStart = rowStart;
    }

   
    public String getRowCauseStart() {
        return "</TD></TR><TR id=\"cause"+result.getRowIndex() + 1+"\" style=\"text-align:left;background:#FFFFFF;font-size:12px;border:1px solid yellow;display: none;\"><TD colspan=\"4\">";
    }

    public void setRowCauseStart(String rowStart) {
     //   this.rowStart = rowStart;
    }
    
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getTag_Message() {
        return tag_Message;
    }

    public void setTag_Message(String tag_Message) {
        this.tag_Message = tag_Message;
    }

    public String[] getCategorytype() {
        return categorytype;
    }

    public void setCategorytype(String[] categorytype) {
        this.categorytype = categorytype;
    }
    
}
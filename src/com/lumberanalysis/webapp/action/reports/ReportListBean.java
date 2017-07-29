/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.webapp.action.reports;

import com.logviewer.log.ConstructPOJO;
import com.logviewer.log.report.Last24HoursReportByType;
//import com.logviewer.log.logs.report.ReportData;
import com.logviewer.log.report.ReportData;
import com.logviewer.log.view.ApplicationNodeForView;
import com.logviewer.log.view.LogRowForView;
import com.lumberanalysis.service.UserSecurityAdvice;
import com.lumberanalysis.webapp.action.logviewer.TreeBackingBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.DataModel;
import javax.inject.Named;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.ListUtils;
import org.jcp.xml.dsig.internal.dom.ApacheCanonicalizer;
import org.springframework.context.annotation.Scope;

@Named("reportListBean")
@Scope("view")
public class ReportListBean extends TreeBackingBean implements Serializable {

    // String displaySelectedMultiple;
    private static Map<String, Object> infoType;
    private ConstructPOJO pojo = new ConstructPOJO();

    static {
        infoType = new LinkedHashMap<String, Object>();
        infoType.put("DEBUG", "DEBUG"); //label, value
        infoType.put("ERROR", "ERROR");
        infoType.put("FATAL", "FATAL");
        infoType.put("INFO", "INFO");
        infoType.put("OFF", "OFF");
        infoType.put("TRACE", "TRACE");
        infoType.put("WARN", "WARN");
    }

    public Map<String, Object> getFavFood2Value() {
        return infoType;
    }
    public Map<String, Object> getLevelOneValue() {
        Map<String, Object> levelOne = new LinkedHashMap<String, Object>();
        List<ApplicationNodeForView>  levelFirstAppList = new ArrayList<>()/*getLevelOne()*/;
        for(ApplicationNodeForView appL :levelFirstAppList){
            levelOne.put(appL.getText(), appL.getId());
        }
        return levelOne;
    }
    public Map<String, Object> getLevelTwoValue() {
        Map<String, Object> levelTwo = new LinkedHashMap<String, Object>();
        if ( selectedLevelOne != null && selectedLevelOne.length > 0 ){
            //selectedLevelTwo = removeDeslectedParentOne(selectedLevelTwo,selectedLevelOne);
            List<ApplicationNodeForView>  levelFirstAppList = new ArrayList<>()/*getLevelTwoChild(Arrays.asList(selectedLevelOne))*/;
            for(ApplicationNodeForView appL :levelFirstAppList){
                levelTwo.put(appL.getText(), appL.getId());
            }
        }
        return levelTwo;
    }
    public Map<String, Object> getLevelThreeValue() {
        Map<String, Object> levelThree = new LinkedHashMap<String, Object>();
        List<String> levelTwo = new ArrayList<String>();
        //Check curen seleted Level 2 is avalable still from it's parent.
        if ( selectedLevelTwo != null && selectedLevelTwo.length > 0 ){
            List<ApplicationNodeForView>  levelFirstAppList = new ArrayList<>()/*getLevelTwo(Arrays.asList(selectedLevelTwo))*/;
            for(ApplicationNodeForView appL :levelFirstAppList){
                levelTwo.add(appL.getId());
            }
        }
        if ( selectedLevelTwo != null && selectedLevelTwo.length > 0 ){
            List<String> selectedLevelTwoList = null;
            if ((levelTwo != null && !levelTwo.isEmpty()) && (selectedLevelThree != null && selectedLevelThree.length > 0)){
                selectedLevelTwoList = ListUtils.intersection(levelTwo,Arrays.asList(selectedLevelThree));
            }else{
                selectedLevelTwoList = Arrays.asList(selectedLevelTwo);
            }
            selectedLevelThree = null;/*removeDeslectedParentOne(selectedLevelThree,selectedLevelTwo)*/;
                List<ApplicationNodeForView>  levelFirstAppList = new ArrayList<>()/*getLevelThree(selectedLevelTwoList)*/;
            for(ApplicationNodeForView appL :levelFirstAppList){
                levelThree.put(appL.getText(), appL.getId());
            }
        }
        return levelThree;
    }
    public boolean isStillSelected(String level2, String Level1){
        return false;
    }
    public Map<String, Object> getLevelThreeValues() {
        Map<String, Object> levelThree = new LinkedHashMap<String, Object>();
        List<String> levelTwo = new ArrayList<String>();
        //Check current seleted Level 2 is avalable still from it's parent.
        if ( selectedLevelTwo != null && selectedLevelTwo.length > 0 ){
            List<ApplicationNodeForView>  levelFirstAppList = new ArrayList<>()/*getLevelTwo(Arrays.asList(selectedLevelTwo))*/;
            for(ApplicationNodeForView appL :levelFirstAppList){
                levelTwo.add(appL.getId());
            }
        }
        if ( selectedLevelTwo != null && selectedLevelTwo.length > 0 ){
            List<String> selectedLevelTwoList = null;
            if ((levelTwo != null && !levelTwo.isEmpty()) && (selectedLevelThree != null && selectedLevelThree.length > 0)){
                selectedLevelTwoList = ListUtils.intersection(levelTwo,Arrays.asList(selectedLevelThree));
            }else{
                selectedLevelTwoList = Arrays.asList(selectedLevelTwo);
            }
            selectedLevelThree = null/*removeDeslectedParentOne(selectedLevelThree,selectedLevelTwo)*/;
                List<ApplicationNodeForView>  levelFirstAppList = new ArrayList<>()/*getLevelThree(selectedLevelTwoList)*/;
            for(ApplicationNodeForView appL :levelFirstAppList){
                levelThree.put(appL.getText(), appL.getId());
            }
        }
        return levelThree;
    }
    private static final long serialVersionUID = 3524937486662786265L;
    private String[] selectedLevelOne;
    private String[] selectedLevelTwo;
    private String[] selectedLevelThree;

    public String[] getSelectedLevelOne() {
        return selectedLevelOne;
    }

    public void setSelectedLevelOne(String[] selectedLevelOne) {
        this.selectedLevelOne = selectedLevelOne;
    }

    public String[] getSelectedLevelThree() {
        return selectedLevelThree;
    }

    public void setSelectedLevelThree(String[] selectedLevelThree) {
        this.selectedLevelThree = selectedLevelThree;
    }

    public String[] getSelectedLevelTwo() {
        return selectedLevelTwo;
    }

    public void setSelectedLevelTwo(String[] selectedLevelTwo) {
        this.selectedLevelTwo = selectedLevelTwo;
    }
    
    public void result(AjaxBehaviorEvent event) {
        //message = "Hello World";
    }
    

   

   
    //------------------------------------------------------------------

    public ReportListBean() {
        if (getRoot() == null) {
            appList();
        }
      
    }

    //@PostConstruct
    void initialiseSession() {
        FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    /*
     * For Result of this page.
     */
    private DataModel<LogRowForView> dataModel;

    public DataModel<LogRowForView> getResultList() {
        if (dataModel == null) {
           
        }
        return dataModel;
    }

    

   
    /**
     * Below method are used related to chart related methods.
     */
    private ReportData rp = new Last24HoursReportByType();

    public ReportData getRp() {
        return rp;
    }

    public void setRp(ReportData rp) {
        this.rp = rp;
    }

    public void displaySelectedChart(ActionEvent event) {
        List<String> nodes = getSelectedAppLeafList();
        //rp = new Last24HoursReportByType();
        if (nodes != null && nodes.size() > 0) {
                
                pojo.loadReportData(nodes, rp);          
        }        
    }
    public String getChartCategory() {
        if (rp != null && rp.getX() != null) {
            String s = rp.getX().toString().replace("[", "");
            return s.replace("]", "");
        }
        return "";
    }

    public void setChartCategory(String s) {
    }

    public void setChartData(String s) {
    }

    public String getChartData() {
        if (rp != null && rp.getY() != null) {
            JSONArray ser = new JSONArray();
            for (Map.Entry<String, List<Long>> e : rp.getY().entrySet()) {
                JSONObject j = new JSONObject();
                j.accumulate("name", e.getKey());
                j.accumulate("data", e.getValue());
                ser.add(j);
                
            }
            return ser.toString();
        }
        return "";
    }

    @Override
    public void getSearchCriteria() {
        
    }
}


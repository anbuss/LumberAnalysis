package com.lumberanalysis.webapp.action.logviewer;


import com.logviewer.log.ConstructPOJO;
import com.logviewer.log.LogPattern;
import com.logviewer.log.report.Last24HoursReportByType;
import com.logviewer.log.report.ReportData;
import com.logviewer.log.view.LogRowForView;
import com.lumberanalysis.model.User;
import com.lumberanalysis.service.UserSecurityAdvice;
import com.lumberanalysis.webapp.action.DownloadBean;
import com.lumberanalysis.webapp.action.TwoDBCLog;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Scope;

@Named("logViewerBean")
@Scope("view")
public class LogViewer extends TreeBackingBean implements Serializable {

    // String displaySelectedMultiple;
    private static Map<String, Object> infoType;
    private ConstructPOJO pojo = new ConstructPOJO();
    private List<String> uniqueResultList;

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
    private static final long serialVersionUID = 3524937486662786265L;
    private String[] categorytype;
    private String className;
    private String threadName;
    private String nestedDiagnosticContext;
    private String freeText;
    private String logstartdate;
    private String logtilldate;
    private String logstarttime;
    private String logtilltime;
    //private String treeData;
    
    private Boolean liveCheck;
    // Render type.
    boolean type = true;
    
    private UserSecurityAdvice securityAdvice = new UserSecurityAdvice();

    /**
     * @return the categorytype
     */
    public String[] getCategorytype() {
        return categorytype;
    }

    /**
     * @param categorytype the categorytype to set
     */
    public void setCategorytype(String[] categorytype) {
        this.categorytype = categorytype;
    }

    /**
     * @return the className
     */
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setFreeText(String freeText) {
        this.freeText = freeText;
    }

    public void setLogstartdate(String logstartdate) {
        this.logstartdate = logstartdate;
    }

    public void setLogstarttime(String logstarttime) {
        this.logstarttime = logstarttime;
    }

    public void setLogtilldate(String logtilldate) {
        this.logtilldate = logtilldate;
    }

    public void setLogtilltime(String logtilltime) {
        this.logtilltime = logtilltime;
    }

    public void setNestedDiagnosticContext(String nestedDiagnosticContext) {
        this.nestedDiagnosticContext = nestedDiagnosticContext;
    }

    public void setSecurityAdvice(UserSecurityAdvice securityAdvice) {
        this.securityAdvice = securityAdvice;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the threadName
     */
    public String getThreadName() {
        return threadName;
    }

    /**
     * @return the nestedDiagnosticContext
     */
    public String getNestedDiagnosticContext() {
        return nestedDiagnosticContext;
    }

    /**
     * @return the freeText
     */
    public String getFreeText() {
        return freeText;
    }

    /**
     * @return the logstartdate
     */
    public String getLogstartdate() {
        return logstartdate;
    }

    /**
     * @return the logtilldate
     */
    public String getLogtilldate() {
        return logtilldate;
    }

    /**
     * @return the logstarttime
     */
    public String getLogstarttime() {
        return logstarttime;
    }

    /**
     * @return the logtilltime
     */
    public String getLogtilltime() {
        return logtilltime;
    }
    private User user;

    public String search() {
        user = securityAdvice.getCurrentUser();
        return "success";
    }

 /*   public String getTreeData() {

        return "{title: \"item1 with key and tooltip\", tooltip: \"Look, a tool tip!\" }";

    }

    public void setTreeData(String treeData) {
        this.treeData = treeData;
    } */
    //------------------------------------------------------------------

    public LogViewer() {
        if (getRoot() == null) {
            appList();
        }
       this.liveCheck = true;
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
            //ConstructPOJO pojo = new ConstructPOJO();

//            List<String> nodes = getSelectedAppLeafList();
//            if (nodes != null && nodes.size() > 0) {
//                LogPattern lp = new LogPattern();
//                lp.setDatePattern("MM/dd/yyyy HH:mm");
//                lp.setCriteria(logstartdate, logtilldate, "", freeText, "", className, threadName, nestedDiagnosticContext);
//                //pojo.loadReportData(nodes, lp);
//                dataModel = new ListDataModel<LogRowForView>(pojo.loadLogsAsObjectForUnique(nodes, lp)); //getPaginator().createPageDataModel();
//            }
        }
        return dataModel;
    }

    

    public void displaySelectedMultiple(ActionEvent event) {
        dataModel = null;
        List<String> nodes = getSelectedAppLeafList();
        if (nodes != null && nodes.size() > 0) {
            if (this.liveCheck){
                LogPattern lp = new LogPattern();
                lp.setDatePattern("MM/dd/yyyy HH:mm");
                lp.setCriteria(logstartdate, logtilldate, "", freeText, "", className, threadName, nestedDiagnosticContext);
                //pojo.loadReportData(nodes, lp);
                dataModel = new ListDataModel<LogRowForView>(pojo.loadLogsAsObjectForUnique(nodes, lp)); //getPaginator().createPageDataModel();
                uniqueResultList = pojo.constructResultListColumns(nodes);
            }else{
                ;//Do Database operation.
            }
            }
    }
    public boolean anyResultAvalable(){
        return !getResultList().isRowAvailable();
    }
    private boolean anyResultAvalableForTable;
    public void setAnyResultAvalableForTable(boolean s){
        ;
    }
    public boolean getAnyResultAvalableForTable(){
        if (getResultList() != null)
            return getResultList().isRowAvailable();
        return false;
    }
    private String resultCaption1;
    public void setResultCaption1(String resultCaption1) {        
    }
    
    public String getResultCaption1(){
        if (uniqueResultList != null && uniqueResultList.size() >=1)
            return getBundle().getString(uniqueResultList.get(0));
        return "";
    }
private String resultCaption2;
private String resultCaption3;
private String resultCaption4;

    public String getResultCaption2() {
        if (uniqueResultList != null && uniqueResultList.size() >=2)
            return getBundle().getString(uniqueResultList.get(1));
        return "";
    }

    public void setResultCaption2(String resultCaption2) {
        
    }

    public String getResultCaption3() {
       if (uniqueResultList != null && uniqueResultList.size() >=3)
            return getBundle().getString(uniqueResultList.get(2));
        return "";
    }

    public void setResultCaption3(String resultCaption3) {
        
    }

    public String getResultCaption4() {
        if (uniqueResultList != null && uniqueResultList.size() >=4)
            return getBundle().getString(uniqueResultList.get(3));
        return "";
    }

    public void setResultCaption4(String resultCaption4) {
        
    }

    public void htmlDownload(ActionEvent event) throws IOException {
        dataModel = null;

        if (getSelectedNodes() != null && getSelectedNodes().length > 0) {

//            ConstructPOJO pojo = new ConstructPOJO();

            List<String> nodes = getSelectedAppLeafList();
            String report = null;
            if (nodes != null && nodes.size() > 0) {

                LogPattern lp = new LogPattern();
                lp.setDatePattern("MM/dd/yyyy HH:mm");
                lp.setCriteria(logstartdate, logtilldate, "", freeText, "", className, threadName, nestedDiagnosticContext);
                String fileName = nodes.toString().replace('[', ' ');
                fileName = fileName.replace(']', ' ');
                fileName = fileName.replace(',', '_').trim();
                fileName += "-"+getCurrentUser().getUsername();
                report = pojo.loadLogsToHTML(nodes, lp, fileName + ".html");
            }

            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
            
            response.setContentType("application/force-download");          
            // externalContext.setResponseHeader("Content-Length", garbage.getContent().length);
           // response.addHeader("Content-Disposition", "attachment; filename=\"" + downloadFile + "\"");
            response.addHeader("Content-Disposition", "attachment;filename=\"" + report + "\"");
            FileInputStream in = null;
            OutputStream output = response.getOutputStream();
            try {
                //output = externalContext.getResponseOutputStream();
                File file = new File(report);
                long length = file.length();
                in = new FileInputStream(file);
                byte[] buf = new byte[1024];
                
//                int c;
//                while ((c = in.read()) != -1) {
//                    output.write(c);
//                }
                while ((in != null) && ((length = in.read(buf)) != -1)) {
                    output.write(buf, 0, (int) length);
                }
            } finally {
                if (in != null) {
                    in.close();
                    output.flush();
                   output.close();
                }
                facesContext.responseComplete();
            }
            
        }
    }

    public void startDate(ValueChangeEvent event) throws AbortProcessingException {
        logstartdate = event.getNewValue().toString();
    }

    public boolean getType() {
        return type;
    }

    public void setType(boolean ty) {
        type = ty;
    }

    @Override
    public void getSearchCriteria() {
        //setType(false);
    }
    /**
     * Below method are used related to chart related methods.
     */
    private ReportData rp = null;

    public ReportData getRp() {
        return rp;
    }

    public void setRp(ReportData rp) {
        this.rp = rp;
    }

    public void displaySelectedChart(ActionEvent event) {
        List<String> nodes = getSelectedAppLeafList();

        if (nodes != null && nodes.size() > 0) {
                rp = new Last24HoursReportByType();
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
            for (Entry<String, List<Long>> e : rp.getY().entrySet()) {
                JSONObject j = new JSONObject();
                j.accumulate("name", e.getKey());
                j.accumulate("data", e.getValue());
                ser.add(j);
                
            }
            return ser.toString();
        }
        return "";
    }

    public Boolean getLiveCheck() {
        return liveCheck;
    }

    public void setLiveCheck(Boolean liveCheck) {
        this.liveCheck = liveCheck;
    }
    private String actionColStart = "</td> "+
               " <td>" ;
                 
     private String actionColEnd = "</td>";

    public String getActionColStart() {
        return actionColStart;
    }

    public void setActionColStart(String actionCol) {
        //this.actionCol = actionCol;
    }

    public String getActionColEnd() {
        return actionColEnd;
    }

    public void setActionColEnd(String actionColEnd) {
        //this.actionColEnd = actionColEnd;
    }
    
}
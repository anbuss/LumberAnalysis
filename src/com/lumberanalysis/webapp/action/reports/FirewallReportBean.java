package com.lumberanalysis.webapp.action.reports;

import com.logviewer.log.ConstructPOJO;
import com.logviewer.log.report.Last24HoursReportByType;
import com.logviewer.log.report.ReportData;
import com.lumberanalysis.model.User;
import com.lumberanalysis.service.UserSecurityAdvice;
import com.lumberanalysis.webapp.action.logviewer.TreeBackingBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Scope;



@Named("firewallReportBean")
@Scope("view")
public class FirewallReportBean extends TreeBackingBean implements Serializable {

   
    private static final long serialVersionUID = 3524937486662786265L;
    
    
    // Render type.
    private boolean type = true;
    private UserSecurityAdvice securityAdvice = new UserSecurityAdvice();

    

    public void setSecurityAdvice(UserSecurityAdvice securityAdvice) {
        this.securityAdvice = securityAdvice;
    }

   
    public void setUser(User user) {
        this.user = user;
    }

    
    private User user;

    public String search() {
        user = securityAdvice.getCurrentUser();
        return "success";
    }

    public String getTreeData() {

        return "{title: \"item1 with key and tooltip\", tooltip: \"Look, a tool tip!\" }";

    }

   
    //------------------------------------------------------------------

    public FirewallReportBean() {
        if (getRoot() == null) {
            appList();
        }
     
    }

    //@PostConstruct
    void initialiseSession() {
        FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

   

    public boolean getType() {
        return isType();
    }

    public void setType(boolean ty) {
        type = ty;
    }

    @Override
    public void getSearchCriteria() {
        setType(false);
    }
    private ReportData rp = null;

    public String getChartCategory() {
        ConstructPOJO pojo = new ConstructPOJO();

        List<String> nodes = getSelectedAppLeafList();

        if (nodes != null && nodes.size() > 0) {
            if (rp == null) {
                rp = new Last24HoursReportByType();
                pojo.loadReportData(nodes, rp);
            }
        }
        if (rp != null && rp.getX() != null) {
            return rp.getX().toString();
        }
        return "";
    }

    public void setChartCategory(String s) {
    }

    public void setChartData(String s) {
    }

    public String getChartData() {
        ConstructPOJO pojo = new ConstructPOJO();

        List<String> nodes = new ArrayList<String>();
        nodes.add("FireWall");//getSelectedAppLeafList();

        if (nodes != null && nodes.size() > 0) {
            if (rp == null) {
                rp = new Last24HoursReportByType();
                pojo.loadReportData(nodes, rp);
            }
        }
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

    /**
     * @return the type
     */
    public boolean isType() {
        return type;
    }
}
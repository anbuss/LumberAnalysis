/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.webapp.action.usermanagement;

//import com.lumberanalysis.model.PSObject;
import com.lumberanalysis.model.User;
import com.lumberanalysis.webapp.action.BasePage;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author samidoss
 */
@Named("searchUserBean")
@Scope("request")
public class SearchUser extends BasePage implements Serializable{
    private static final long serialVersionUID = 1L;
    
      
    private String appName = null;
    private String userNameToSearch=null;
    
    private DataModel<User> result = null;
    
    @PostConstruct
    private void init(){
       // result = new ListDataModel<User>(getPsDataManager().getPSDatas());
    }

    
    public void go(ActionEvent event) {
        List<User> a = getUserManager().getUsersListByName(userNameToSearch);
        if (a !=null && a.size() > 0)
            result = new ListDataModel<User>(a);
    }
    
    public boolean getEmptyYa() {
        return (result!=null && result.getRowCount() > 0)?true:false;
    }
    public void setEmptyYa(boolean a) {
        
    }
    public DataModel<User> getResult() {
        return result;
    }

    public void setResult(List<User> result) {
       // this.result = result;
    }

    
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getUserNameToSearch() {
        return userNameToSearch;
    }

    public void setUserNameToSearch(String userNameToSearch) {
        this.userNameToSearch = userNameToSearch;
    }
    
}
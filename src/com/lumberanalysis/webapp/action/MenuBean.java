/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.webapp.action;

import com.lumberanalysis.model.Role;
import com.lumberanalysis.model.User;
import com.lumberanalysis.webapp.action.menu.ProcessMenu;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.NavigationHandler;
import javax.inject.Named;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author samidoss
 */
@Named("menuBean")
@Scope("view")
@SuppressWarnings("unchecked")
public class MenuBean extends BasePage implements Serializable{
    private static final long serialVersionUID = 1L;
   // MenuLink ml = new MenuLink();
    
    public List<MenuLink> firstLineMenu;
    public List<MenuLink> secondLevelMenu;
    
    @PostConstruct
    
    public void init(){
       ProcessMenu.init(); //TODO: Room to improve performance.
       firstLineMenu =  filter(ProcessMenu.getMenulist());
    }

    public List<MenuLink> getFirstLineMenu() {
        // TODO : Move this code to navagatior/lisiner.
        User u = getCurrentUser();
        if (u != null && u.getUsername().equalsIgnoreCase("anonymousUser")){
            NavigationHandler myNav = getFacesContext().getApplication().getNavigationHandler();
            String redirect = "/Login.xhtml";
            myNav.handleNavigation(getFacesContext(), null, redirect);

        }
        return Collections.unmodifiableList(firstLineMenu);
    }

    @Deprecated
    public void setFirstLineMenu(List<MenuLink> firstLineMenu) {
        this.firstLineMenu = firstLineMenu;
    }
    
    public List<MenuLink> getSecondLevelMenu() {
        return Collections.unmodifiableList(firstLineMenu);
    }

    private List<MenuLink> filter(List<MenuLink> menulist) {
        List<MenuLink> fil=new ArrayList<MenuLink>();
        if (getCurrentUser() == null) return fil;
        Set<Role> l = getCurrentUser().getRoles();
        for(MenuLink me:menulist){
            for(Role ro:l){
                if(me.getRoleList().contains(ro.getName())){
                    fil.add(me);
                    break;
                }
            }
        }
        
        return fil;
    }

    public String getLLLink(){
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            if ( userName != null &&
                (!"".equals(userName.trim()) && !userName.equalsIgnoreCase("anonymousUser")))
            return "Logout.jsp";
        }
        return "Login.xhtml";
    }
    public String getLLLinkDisplay(){
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            if ( userName != null &&
                (!"".equals(userName.trim()) && !userName.equalsIgnoreCase("anonymousUser")))
            return "Logout";
        }
        return "Login";
    }

}

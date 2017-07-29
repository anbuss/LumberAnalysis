/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.webapp.action.menu;


import java.io.Serializable;
import java.util.List;


/**
 *
 * @author samidoss
 */
@Deprecated
public class MenuModel implements Serializable {

    private Integer id;
    private String label;
    private String link;
    private List<MenuModel> childMenus;
//    private Set<String> role;

    public MenuModel() {
    }

    public List<MenuModel> getChildMenus() {
        return childMenus;
    }

    public String getLabel() {
        return label;
    }

    public String getLink() {
        return link;
    }

    public Integer getId() {
        return id;
    }

//    public Set<String> getRole() {
//        return role;
//    }
    public void setChildMenus(List<MenuModel> childMenus) {
        this.childMenus = childMenus;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setId(Integer id) {
        this.id = id;
    }
//    public void setRole(Set<String> role) {
//        this.role = role;
//    }
}

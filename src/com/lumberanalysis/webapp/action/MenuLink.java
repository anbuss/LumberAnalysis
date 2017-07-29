/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.webapp.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author samidoss
 */
public class MenuLink implements Serializable{
    public String name;
    public String url;
    public String caption;
    public String parent;
    private Integer seq;
    private List roleList;

    public List getRoleList() {
        return roleList;
    }

    public void setRoleList(List roleList) {
        this.roleList = roleList;
    }
    public List<MenuLink> child;
    
    public boolean havingChild=false;

    public boolean isHavingChild() {
        return havingChild;
    }

    public boolean getHavingChild() {
        return this.havingChild;
    }
    public void setHavingChild(boolean havingChild) {
        this.havingChild = havingChild;
    }

    MenuLink(String url, String caption) {
        this.url = url;
        this.caption = caption;
    }

    @SuppressWarnings("unchecked")
    public MenuLink(String name, String url, String caption, String parent, String[] roles) {
        this.name = name;
        this.url = url;
        this.caption = caption;
        this.parent = parent;
        this.roleList = new ArrayList(roles.length);
        Collections.addAll(this.roleList, roles);
    }
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<MenuLink> getChild() {
        return child;
    }

    public void setChild(List<MenuLink> child) {
        this.child = child;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
    
    public boolean addChild(MenuLink childObject){
        if (childObject != null){
            if (this.name.equals(childObject.getParent())){
                if (this.child == null){
                    this.child = new ArrayList();
                    havingChild=true;
                    child.add(childObject);
                }else{
                    child.add(childObject);
                }
            }
        }
        return false;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
    
}

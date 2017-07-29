/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.webapp.action.menu;

import com.lumberanalysis.model.Role;
import com.lumberanalysis.model.User;
import com.lumberanalysis.service.UserSecurityAdvice;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author samidoss
 */

public class MenuCreateService {

    private User user;
    private Map<String, List<MenuModel>> mapMenuModel;
    private UserSecurityAdvice securityAdvice = new UserSecurityAdvice();
    private String menuHtml = "";
    private List<MenuModel> menus;

    public UserSecurityAdvice getSecurityAdvice() {
        return securityAdvice;
    }

    public MenuCreateService() {
        createMenu();
    }

    public User getUser() {
        return user;
    }

    public Map<String, List<MenuModel>> getMapMenuModel() {
        return mapMenuModel;
    }

    public void setMapMenuModel(Map<String, List<MenuModel>> mapMenuModel) {
        this.mapMenuModel = mapMenuModel;
    }

    public String getMenuHtml() {
        return menuHtml;
    }

    public void setMenuHtml(String menuHtml) {
        this.menuHtml = menuHtml;
    }

    public List<MenuModel> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuModel> menus) {
        this.menus = menus;
    }


    public void createMenu() {
        user = securityAdvice.getCurrentUser();
//        StringBuilder sb = new StringBuilder();
        List<StringBuilder> subMenus = new ArrayList<StringBuilder>();
//        sb.append("<ul id=\"ddtopmenubar\" class=\"mattblackmenu\">");
        if (user != null) {
            for (Role role : user.getRoles()) {
               menus = mapMenuModel.get(role.getName());
                for (MenuModel menu : menus) {
                    if (menu.getChildMenus() == null && menu.getChildMenus().size() == 0) {
//                        sb.append("<li><a href=\"" + menu.getLink() + "\">" + menu.getLabel() + "</a></li>");
                    } else {
//                        sb.append("<li><a href=\"" + menu.getLink() + "\" rel=\"subMenu_" + menu.getId() + "\">" + menu.getLabel() + "</a></li>");
                        StringBuilder subMenu = new StringBuilder();
//                        subMenu.append("<ul id=\"subMenu_" + menu.getId() + "\" class=\"ddsubmenustyle\">");
                        for (MenuModel sub : menu.getChildMenus()) {
//                            subMenu.append("<li><a href=\"" + sub.getLink() + "\">" + sub.getLabel() + "</a></li>");
                        }
//                        subMenu.append("</ul>");
                        subMenus.add(subMenu);
                    }
                }

            }
        }
//        sb.append("</ul>");
//        if (subMenus.size() > 0) {
//            for (StringBuilder build : subMenus) {
//                sb.append(build);
//            }
//        }
//        menuHtml = sb.toString();
//        return;
    }
}

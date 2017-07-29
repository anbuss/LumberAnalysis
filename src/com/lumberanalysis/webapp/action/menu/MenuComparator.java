/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.webapp.action.menu;

import com.lumberanalysis.webapp.action.MenuLink;
import java.util.Comparator;

/**
 *
 * @author samidoss
 */
public class MenuComparator implements Comparator<MenuLink> {
    public int compare(MenuLink o1, MenuLink o2) {
        return o1.getSeq() - o2.getSeq();
    }

}

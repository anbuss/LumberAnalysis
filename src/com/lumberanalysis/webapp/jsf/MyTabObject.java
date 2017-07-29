/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.webapp.jsf;

/**
 *
 * @author samidoss
 */
public class MyTabObject {
    String tabfilename;
String tabid;
public String getTabfilename() {
    return tabfilename;
}
public void setTabfilename(String tabfilename) {
    this.tabfilename = tabfilename;
}
public String getTabid() {
    return tabid;
}
public void setTabid(String tabid) {
    this.tabid = tabid;
}
public MyTabObject(String tabfilename, String tabid) {
    super();
    this.tabfilename = tabfilename;
    this.tabid = tabid;
}

}

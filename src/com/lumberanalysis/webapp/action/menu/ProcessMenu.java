/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.webapp.action.menu;

import com.lumberanalysis.webapp.action.MenuLink;
import com.lumberanalysis.webapp.util.LumberAnalysisProperty;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author samidoss
 */
//TODO: it should me singleton class
//TODO:Stop parsing the XML file whenever page is geting loaded/refresh.

public class ProcessMenu {

    private static ProcessMenu PROCESS_MENU = null;
    private static List<MenuLink> menulist;

    public static List<MenuLink> getMenulist() {
        return menulist;
    }


    private ProcessMenu() {
        process();
    }

    private void process() {

        try {

            //File fXmlFile = new File("D:\\Online\\u2z.services\\Dropbox\\Log Project\\tomcat\\LumberAnalysis\\src\\java\\com\\lumberanalysis\\webapp\\action\\menu\\LAMenu.xml");
        	URL foourl = LumberAnalysisProperty.class.getClassLoader().getResource("LAMenu.xml");        	
        	File fXmlFile = new File(foourl.getFile());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            parseDocument(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseDocument(Document dom) {
        //get the root element
        Hashtable<String, MenuLink> menuGroup = new Hashtable<String, MenuLink>();
        Element docEle = dom.getDocumentElement();

        //get a nodelist of elements
        NodeList nl = docEle.getElementsByTagName("menu");
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {

                //get the employee element
                Element el = (Element) nl.item(i);

                //get the Employee object
                MenuLink e = getMenuLink(el);
                
                //add it to list
                if (menuGroup.containsKey(e.parent)) {
                    menuGroup.get(e.parent).addChild(e);
                } else {
                    menuGroup.put(e.name, e);
                }
            }
        }
        menulist = new ArrayList<MenuLink>(menuGroup.values());
        Collections.sort(menulist, new MenuComparator());
    }

    private MenuLink getMenuLink(Element empEl) {
MenuLink e = null;
        try{
        String uurl = LumberAnalysisProperty.getInstance().getAppURL()+getTextValue(empEl, "url");
        String captin = getTextValue(empEl, "captin");

        String name = empEl.getAttribute("name");
        String parent = empEl.getAttribute("parent");
        String order = empEl.getAttribute("order");
        
        String role = getTextValue(empEl, "role");
        if (role == null)
            role = "";
        //Create a new Employee with the value read from the xml nodes
        e = new MenuLink(name, uurl, captin, parent,role.split(":"));
        e.setSeq(Integer.parseInt(order));
        }catch(Exception e1){
            getLog().error("Error while processing Menu : "+ e1.getMessage());
        }
      return e;
    }

   
    private String getTextValue(Element ele, String tagName) {
        String textVal = null;
        try {


            NodeList nl = ele.getElementsByTagName(tagName);
            if (nl != null && nl.getLength() > 0) {
                Element el = (Element) nl.item(0);
                textVal = el.getFirstChild().getNodeValue();
            }
        } catch (Exception e) {
            textVal = e.getMessage();
        }
        return textVal;
    }

    /**
     * Calls getTextValue and returns a int value
     */
    /*private int getIntValue(Element ele, String tagName) {
        //in production application you would catch the exception
        return Integer.parseInt(getTextValue(ele, tagName));
    }*/

    public static void init() {
       if (PROCESS_MENU == null) {
            PROCESS_MENU = new ProcessMenu();
        }
    }
    
    private Logger LOG = Logger.getLogger(this.getClass());

    public Logger getLog() {
        return LOG;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.webapp.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.logviewer.log.util.AppProperty;

/**
 *
 * @author samidoss
 */
public class LumberAnalysisProperty {
     private static Properties p = new Properties();

     
     private LumberAnalysisProperty(){
       // p.put("URL","http://localhost:8080/LumberAnalysis/");
    	 try {
				loadAppProperty();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     }
     private static LumberAnalysisProperty INSTANCE=null;
     
     public static LumberAnalysisProperty getInstance(){
         
         if (INSTANCE == null){
             //synchronized(INSTANCE){
             INSTANCE = new LumberAnalysisProperty();             
         //}
         }
         return INSTANCE;
     }
     private static void loadAppProperty() throws IOException {
         InputStream foo = null;
         try {
             //String pa = LogHelper.getConfigInfo();// + PROPERTIES_FILE;
            // foo = LumberAnalysisProperty.class.getResourceAsStream("lumberanalysis.properties");
             URL foourl = LumberAnalysisProperty.class.getClassLoader().getResource("lumberanalysis.properties");
           //  if (foo == null) {
                 foo = new FileInputStream(foourl.getFile());
            // }
             p.load(foo);
             foo.close();
         } catch (IOException e) {
             Logger.getLogger(AppProperty.class.getName()).error(e.getMessage(), e);
         }finally{
             if (foo !=null){
                 foo.close();
             }
         }
     } 
    public String getAppURL() {
        return p.getProperty("URL");
    }
    public String getMenu() {
        return p.getProperty("URL");
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis;

import java.util.Properties;

/**
 *
 * @author samidoss
 */
public class LumberAnalysisProperties {
    private static Properties appProperties= null;
    
    public LumberAnalysisProperties(Properties prop){
        LumberAnalysisProperties.appProperties = prop;
    }
    
    public static String getApplicationPropertie(String key, String value){
        return LumberAnalysisProperties.appProperties.getProperty(key, value);
    }
    public static String getApplicationPropertie(String key){
        return LumberAnalysisProperties.appProperties.getProperty(key);
    }
    public static Boolean getBooleanValue(String key, String value){
        String s = getApplicationPropertie(key, value);
        if ((s == null) || ("".equals(s.trim())))
            s = value;
        return Boolean.getBoolean(s.toLowerCase());
    }
    public static Boolean getSchudelerRun(){
        return getBooleanValue("schuduler.start", "false");
    }
}

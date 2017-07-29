/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.webapp.action;

import java.io.*;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author samidoss
 */
@Named("downloadBean")
@Scope("view")
public class DownloadBean extends BasePage implements Serializable {

    private static final long serialVersionUID = 1L; 

    /**
     * Download file.
     */
    public void downloadFile(String fileName) throws IOException {
        File file = new File( fileName );
        InputStream fis = new FileInputStream(file);
        try {
 HttpServletResponse response = getResponse();

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename="+file.getName());
            byte[] buf = new byte[1024];
            
           // while ((offset < buf.length) && ((numRead = fis.read(buf, offset, buf.length - offset)) >= 0)) {
             while (fis.read(buf) >= 0) {
                response.getOutputStream().write(buf);
            }
            // fis.close();
           
            
            response.getOutputStream().flush();
            response.getOutputStream().close();
            getFacesContext().responseComplete();
        } finally {
            fis.close();
           // file.delete();
        }
    }
}

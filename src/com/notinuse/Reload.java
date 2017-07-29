package com.notinuse;

import java.io.IOException;
import java.io.Serializable;

import com.lumberanalysis.webapp.listener.StartupListener;

/**
 * JSF Page class to handle reloading options in servlet context.
 *
 * 
 */
@Deprecated
public class Reload extends BasePage implements Serializable {
    private static final long serialVersionUID = 2466200890766730676L;

  /*  public String execute() throws IOException {

        if (getLog().isDebugEnabled()) {
            getLog().debug("Entering 'execute' method");
        }

        StartupListener.setupContext(getServletContext());
        addMessage("reload.succeeded"); 

        return "mainMenu";
    }
*/
}

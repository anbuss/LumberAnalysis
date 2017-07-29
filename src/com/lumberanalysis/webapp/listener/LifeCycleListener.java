/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.webapp.listener;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import org.apache.log4j.Logger;

/**
 *
 * @author samidoss
 */

@SuppressWarnings("serial")
public class LifeCycleListener implements PhaseListener  {
    private static final Logger log = Logger.getLogger(StartupListener.class);

    @Override
    public void afterPhase(PhaseEvent pe) {
        log.info("START PHASE " + pe.getPhaseId());
    }

    @Override
    public void beforePhase(PhaseEvent pe) {
        log.info("END PHASE " + pe.getPhaseId());
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }
    
}

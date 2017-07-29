/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.dao;

import com.lumberanalysis.model.PSObject;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author samidoss
 */
public interface PSDataDAO extends GenericDao<PSObject, Long> {
    
   // @Transactional
   // UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * Gets a list of users ordered by the uppercase version of their username.
     *
     * @return List populated list of users
     */
    List<PSObject> getPSDatas();

    /**
     * Saves a user's information.
     * @param user the object to be saved
     * @return the persisted User object
     */
    PSObject savePSData(PSObject psData);

    /**
     * Retrieves the password in DB for a user
     * @param username the user's username
     * @return the password in DB, if the user is already persisted
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    List<PSObject> getPSDataByCriteria(FilterCriteria criteria);

    public List<String> getDistinctAppNames();
}

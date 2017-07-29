package com.lumberanalysis.service.impl;

import com.lumberanalysis.dao.DAOException;
import com.lumberanalysis.dao.GenericDao;
import com.lumberanalysis.service.GenericManager;
import com.lumberanalysis.service.LAServiceException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 * This class serves as the Base class for all other Managers - namely to hold
 * common CRUD methods that they might all use. You should only need to extend
 * this class when your require custom CRUD logic.
 *
 * <p>To register this class in your Spring context file, use the following XML.
 * <pre>
 *     &lt;bean id="userManager" class="com.lumberanalysis.service.impl.GenericManagerImpl"&gt;
 *         &lt;constructor-arg&gt;
 *             &lt;bean class="com.lumberanalysis.dao.hibernate.GenericDaoHibernate"&gt;
 *                 &lt;constructor-arg value="com.lumberanalysis.model.User"/&gt;
 *                 &lt;property name="sessionFactory" ref="sessionFactory"/&gt;
 *             &lt;/bean&gt;
 *         &lt;/constructor-arg&gt;
 *     &lt;/bean&gt;
 * </pre>
 *
 * @author Samidoss
 * @param <T> a type variable
 * @param <PK> the primary key for that type
 */
public class GenericManagerImpl<T, PK extends Serializable> implements GenericManager<T, PK>, Serializable {
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Logger log = Logger.getLogger(this.getClass());

    /**
     * GenericDao instance, set by constructor of child classes
     */
    protected GenericDao<T, PK> dao;

    public GenericManagerImpl() {}

    public GenericManagerImpl(GenericDao<T, PK> genericDao) {
        this.dao = genericDao;
    }

    /**
     * {@inheritDoc}
     */
    public List<T> getAll() {
        return dao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    public T get(PK id) {
        return dao.get(id);
    }

    /**
     * {@inheritDoc}
     */
    public boolean exists(PK id) {
        return dao.exists(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T save(T object) throws LAServiceException{
        try {
            return dao.save(object);
        } catch (DAOException ex) {
            log.error(ex.getMessage(), ex);
            throw new LAServiceException(ex.getMessage(), ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void remove(PK id) {
        dao.remove(id);
    }
}

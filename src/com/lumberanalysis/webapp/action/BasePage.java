package com.lumberanalysis.webapp.action;

import com.lumberanalysis.Constants;
import com.lumberanalysis.model.Role;
import com.lumberanalysis.model.User;
import com.lumberanalysis.service.MailEngine;
import com.lumberanalysis.service.UserManager;
import com.lumberanalysis.service.UserSecurityAdvice;
import com.lumberanalysis.webapp.util.RequestUtil;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.NullComparator;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.log4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;

public class BasePage {

    
    @Inject
    @Named("userManager")
    private UserManager userManager;
    @Inject
    @Named("mailEngine")
    private MailEngine mailEngine;
    
    private SimpleMailMessage message;
    
    private String templateName;
   // private FacesContext facesContext;
    private String sortColumn;
    private boolean ascending;
    private boolean nullsAreHigh;
    protected  boolean roleUser;
    protected  boolean roleAdmin;

    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
    
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    // Convenience methods ====================================================
    public String getParameter(String name) {
        return getRequest().getParameter(name);
    }

   /* public Map getCountries() {
        CountryModel model = new CountryModel();
        return model.getCountries(getRequest().getLocale());
    }*/

    public String getBundleName() {
        return getFacesContext().getApplication().getMessageBundle();
    }

    public ResourceBundle getBundle() {
        //ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        //return ResourceBundle.getBundle(getBundleName(), getRequest().getLocale(), classLoader);
       // return ResourceBundle.getBundle(getBundleName(), getFacesContext().getViewRoot().getLocale());
        return getFacesContext().getApplication().getResourceBundle(getFacesContext(), "msg");

    }

    public String getText(String key) {
        String localizedMessage;

        try {
            localizedMessage = getBundle().getString(key);
        } catch (java.util.MissingResourceException mre) {
            getLog().warn("Missing key for '" + key + "'");
            return "???" + key + "???";
        }catch(Exception e){
            return "???" + key +(e.getLocalizedMessage())+ "???";
        }

        return localizedMessage;
    }

    public String getText(String key, Object arg) {
        if (arg == null) {
            return getText(key);
        }

        MessageFormat form = new MessageFormat(getBundle().getString(key));

        if (arg instanceof String) {
            return form.format(new Object[]{arg});
        } else if (arg instanceof Object[]) {
            return form.format(arg);
        } else {
            getLog().error("arg '" + arg + "' not String or Object[]");

            return "";
        }
    }

    protected void addMessage(String key, Object arg) {
       
        getFacesContext().addMessage(null, new FacesMessage(getText(key, arg)));
       
    }

    protected void addMessage(String key) {
        addMessage(key, null);
    }

    @SuppressWarnings("unchecked")
    protected void addError(String key, Object arg) {
        // The "JSF Way" doesn't allow you to put HTML in your error messages, so I don't use it.
        // FacesUtils.addErrorMessage(formatMessage(key, arg));
        List<String> errors = (List) getSession().getAttribute("errors");

        if (errors == null) {
            errors = new ArrayList<String>();
        }
        if (key != null){
        // if key contains a space, don't look it up, it's likely a raw message
        if (key.contains(" ") && arg == null) {
            errors.add(key);
        } else {
            errors.add(getText(key, arg));
        }
        }else{
            errors.add("key to message itself is null");
        }
        getSession().setAttribute("errors", errors);
    }

    public boolean isRoleAdmin() {
        return roleAdmin;
    }

    public void setRoleAdmin(boolean roleAdmin) {
        this.roleAdmin = roleAdmin;
    }

    public boolean isRoleUser() {
        return roleUser;
    }

    public void setRoleUser(boolean roleUser) {
        this.roleUser = roleUser;
    }

  
    public boolean isAdmin(User user) {
        if(user !=null){
            for (Role role : user.getRoles()) {
                if (role.getName().equals("ROLE_ADMIN")) {
                    return true;
                }
            }
        }
        return false;
    }

  

    public boolean isUser(User user) {
        for (Role role : user.getRoles()) {
            if (role.getName().equals("ROLE_USER")) {
                return true;
            }
        }
        return false;
    }

    
    protected void addError(String key) {
        addError(key, null);
    }

    /**
     * Convenience method for unit tests.
     * @return boolean indicator of an "errors" attribute in the session
     */
    public boolean hasErrors() {
        return (getSession().getAttribute("errors") != null);
    }

    /**
     * Servlet API Convenience method
     * @return HttpServletRequest from the FacesContext
     */
    protected HttpServletRequest getRequest() {
        return (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
    }

    /**
     * Servlet API Convenience method
     * @return the current user's session
     */
    protected HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * Servlet API Convenience method
     * @return HttpServletResponse from the FacesContext
     */
    protected HttpServletResponse getResponse() {
        return (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
    }

    /**
     * Servlet API Convenience method
     * @return the ServletContext form the FacesContext
     */
    protected ServletContext getServletContext() {
        return (ServletContext) getFacesContext().getExternalContext().getContext();
    }

    /**
     * Convenience method to get the Configuration HashMap
     * from the servlet context.
     *
     * @return the user's populated form from the session
     */
    protected Map getConfiguration() {
        Map config = (HashMap) getServletContext().getAttribute(Constants.CONFIG);

        // so unit tests don't puke when nothing's been set
        if (config == null) {
            return new HashMap();
        }

        return config;
    }

    /**
     * Convenience message to send messages to users, includes app URL as footer.
     * @param user the user to send the message to
     * @param msg the message to send
     * @param url the application's URL
     */
    protected void sendUserMessage(User user, String msg, String url) {
        if (getLog().isDebugEnabled()) {
            getLog().debug("sending e-mail to user [" + user.getEmail() + "]...");
        }

        message.setTo(user.getFullName() + "<" + user.getEmail() + ">");

        Map<String, Serializable> model = new HashMap<String, Serializable>();
        model.put("user", user);

        // TODO: once you figure out how to get the global resource bundle in
        // WebWork, then figure it out here too.  In the meantime, the Username
        // and Password labels are hard-coded into the template.
        // model.put("bundle", getTexts());
        model.put("message", msg);
        model.put("applicationURL", url);
        getMailEngine().sendMessage(message, templateName, model);
    }

    public void setMailEngine(MailEngine mailEngine) {
        this.mailEngine = mailEngine;
    }

    public SimpleMailMessage getMessage() {
        if (message == null)
            message = new SimpleMailMessage();
        return message;
    }

    public void setMessage(SimpleMailMessage message) {
        this.message = message;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    // The following methods are used by t:dataTable for sorting.
    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    /**
     * Sort list according to which column has been clicked on.
     * @param list the java.util.List to sort
     * @return ordered list
     */
    @SuppressWarnings("unchecked")
    protected List<User> sort(List<User> list) {
        Comparator comparator = new BeanComparator(sortColumn, new NullComparator(nullsAreHigh));
        if (!ascending) {
            comparator = new ReverseComparator(comparator);
        }
        Collections.sort(list, comparator);
        return list;
    }

    /**
     * @return the userManager
     */
    public UserManager getUserManager() {
        return userManager;
    }

    /**
     * @return the mailEngine
     */
    public MailEngine getMailEngine() {
        return mailEngine;
    }
    private User user;
    public User getCurrentUser(){
        UserSecurityAdvice securityAdvice = new UserSecurityAdvice();
        user = securityAdvice.getCurrentUser();
        return user;
    }
    
    public String getAppURL(){
        return RequestUtil.getAppURL(getRequest());
    }
    
    private Logger LOG = Logger.getLogger(this.getClass());

    public Logger getLog() {
        return LOG;
    }
}

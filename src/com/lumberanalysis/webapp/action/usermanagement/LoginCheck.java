/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.webapp.action.usermanagement;

//import java.util.HashMap;
import com.lumberanalysis.model.User;
import com.lumberanalysis.service.UserManager;
import com.lumberanalysis.service.UserSecurityAdvice;
import com.lumberanalysis.webapp.action.BasePage;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
//import org.springframework.security.web.authentication.AbstractProcessingFilter;

//import javax.faces.bean.SessionScoped;
/**
 *
 * @author samidoss
 */
//@ManagedBean(name = "loginCheck")
//@SessionScoped
//@Named("loginCheck")
//@Scope("view")
@SuppressWarnings("serial")
public class LoginCheck extends BasePage implements Serializable {

    private Map<String, String> userMap;
    private String username = "";
    private String password = "";
    private boolean rememberMe = false;
    private boolean loggedIn = false;
    private UserManager userManager;
    private User user;
    private String error;
    private UserSecurityAdvice securityAdvice = new UserSecurityAdvice();
    @Resource(name = "authenticationManager")
    AuthenticationManager authenticationManager;

    
    //private String userFromContext;

    public String getUserFromContext(){
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return null;
    }
    
    
    public Map<String, String> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<String, String> userMap) {
        this.userMap = userMap;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public UserSecurityAdvice getSecurityAdvice() {
        return securityAdvice;
    }

    public void setSecurityAdvice(UserSecurityAdvice securityAdvice) {
        this.securityAdvice = securityAdvice;
    }

    /** Creates a new instance of LoginCheck */
//    public LoginCheck() {
//        userMap = new HashMap<String, String>();
//        userMap.put("Tulika", "Tulika");
//    }
    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String doLogin() throws IOException, ServletException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_security_check");

        dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());

        FacesContext.getCurrentInstance().responseComplete();
        
        /*
         * TODO : Remove the below step's
         */
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            if (authenticate.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authenticate);
               // return true;
            }

      //  HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
      //  HttpSession ses = req.getSession();
        user = securityAdvice.getCurrentUser();
        
        roleAdmin = isAdmin(user);
        roleUser = isUser(user);
        ExternalContext ec = getFacesContext().getExternalContext();
        if(roleAdmin){
            return "successAdmin";
        }else{
            ec.redirect("home/success.xhtml");
            return "";
        }
        // It's OK to return null here because Faces is just going to exit.
//        return null;

    }

    @PostConstruct
    private void handleErrorMessage() {
        Exception e = (Exception) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(
                AbstractAuthenticationProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY);

        if (e instanceof BadCredentialsException) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(
                    AbstractAuthenticationProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY, null);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username or password not valid.", null));
        }
    }

    public boolean isRememberMe() {
        return this.rememberMe;
    }

    public void setRememberMe(final boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public boolean isLoggedIn() {
        return this.loggedIn;
    }

    public void setLoggedIn(final boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void verify(AjaxBehaviorEvent event) {
        try {
            user = userManager.getUserByUsername(username);
            error = "";
        } catch (UsernameNotFoundException exception) {
            error = exception.getMessage();
        }

    }

    public String sendMaessage() throws MessagingException {
        String[] mailId = {"tulikasinghtushi@gmail.com"};
        try {
            getMailEngine().sendMessage(mailId, "tulika", "Hello", "Hi");
        } catch (MailException me) {
            addError(me.getMostSpecificCause().getMessage());
            return null;
        }
        return "success";
    }
}

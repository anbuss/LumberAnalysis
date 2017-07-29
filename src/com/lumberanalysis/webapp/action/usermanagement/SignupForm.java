package com.lumberanalysis.webapp.action.usermanagement;


import com.lumberanalysis.Constants;
import com.lumberanalysis.model.Role;
import com.lumberanalysis.model.User;
import com.lumberanalysis.service.RoleManager;
import com.lumberanalysis.service.UserExistsException;
import com.lumberanalysis.webapp.action.BasePage;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.MailException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * JSF Page class to handle signing up a new user.
 */

@Named("regBean")
//@Scope("view")
@Scope("view")

public class SignupForm extends BasePage implements Serializable {
    private static final long serialVersionUID = 3524937486662786265L;
    private User user = new User();
    @Inject
    @Named("roleManager")
    private RoleManager roleManager;
    private Role[] allRoles;
    private Long[] selectedRoles;
    
    //@Autowired
   // private UserManager userManager1;
    @PostConstruct
    private void init() {
        String psid = getParameter("psid");
        if (psid != null){
            user = getUserManager().getUserByUsername(psid);
            Long[] rol = new Long[user.getRoles().size()];
            int i=0;
            for(Role r:user.getRoles()){
                rol[i++] = r.getId();
                
            }
            if (rol.length > 0){
                setSelectedRoles(rol);
            }
        }
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    public String save() throws Exception {
        user.setEnabled(true);

        // Set the default user role on this new user
      //  user.addRole(roleManager.getRole(Constants.USER_ROLE));

        try {
            user.setRoles(new HashSet<Role>(getRoleManager().getRolesByIdList(selectedRoles)));
            user = getUserManager().saveUser(user);
        } catch (AccessDeniedException ade) {
            // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity 
            getLog().warn(ade.getMessage());
            getResponse().sendError(HttpServletResponse.SC_FORBIDDEN);
            return null; 
        } catch (UserExistsException e) {
            addMessage("errors.existing.user", new Object[]{user.getUsername(), user.getEmail()});

            // redisplay the unencrypted passwords
            user.setPassword(user.getConfirmPassword());
            return null;
        }

        addMessage("user.registered");
        getSession().setAttribute(Constants.REGISTERED, Boolean.TRUE);

        // log user in automatically
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getConfirmPassword(), user.getAuthorities());
        auth.setDetails(user);
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Send an account information e-mail
        getMessage().setSubject(getText("signup.email.subject"));

        try {
            sendUserMessage(user, getText("signup.email.message"),
                    getAppURL());
        } catch (MailException me) {
            addError(me.getMostSpecificCause().getMessage());
            return null;
        }

        return "mainMenu";
    }

    public String getCountry() {
        return getUser().getAddress().getCountry();
    }

    // for some reason, the country drop-down won't do 
    // getUser().getAddress().setCountry(value)
    public void setCountry(String country) {
        getUser().getAddress().setCountry(country);
    }

    public Role[] getAllRoles(){
        List<Role> r = roleManager.getAll();
        allRoles = r.toArray(new Role[r.size()]);
       return allRoles;
    }

    public void setAllRoles(Role[] allRoles) {
        this.allRoles = allRoles;
    }

    
    public RoleManager getRoleManager() {
        return roleManager;
    }

    public Long[] getSelectedRoles() {
        return selectedRoles;
    }

    public void setSelectedRoles(Long[] selectedRoles) {
        this.selectedRoles = selectedRoles;
    }
    
}

package com.lumberanalysis.webapp.action.usermanagement;


import com.lumberanalysis.model.User;
import com.lumberanalysis.webapp.action.BasePage;
import org.springframework.mail.MailException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Managed Bean to send password hints to registered users.
 *
 * <p>
 * <a href="PasswordHint.java.html"><i>View Source</i></a>
 * </p>
 *
 * 
 */
public class PasswordHint extends BasePage {
    private String username;
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public String execute() {
        
        // ensure that the username has been sent
        if (username == null || "".equals(username)) {
            getLog().warn("Username not specified, notifying user that it's a required field.");

            addError("errors.required", getText("user.username"));
            return null;
        }
        
        if (getLog().isDebugEnabled()) {
            getLog().debug("Processing Password Hint...");
        }
        
        // look up the user's information
        try {
            User user = getUserManager().getUserByUsername(username);

            StringBuffer msg = new StringBuffer();
            msg.append("Your password hint is: ").append(user.getPasswordHint());
            msg.append("\n\nLogin at: ").append(getAppURL());

            getMessage().setTo(user.getEmail());
            String subject = '[' + getText("webapp.name") + "] " + getText("user.passwordHint");
            getMessage().setSubject(subject);
            getMessage().setText(msg.toString());
//            mailEngine.send(message);
            
            addMessage("login.passwordHint.sent", 
                       new Object[] { username, user.getEmail() });
            
        } catch (UsernameNotFoundException e) {
            getLog().warn(e.getMessage());
            // If exception is expected do not rethrow
            addError("login.passwordHint.error", username);
        } catch (MailException me) {
            addError(me.getCause().getLocalizedMessage());
        }

        return "success";
    }
}

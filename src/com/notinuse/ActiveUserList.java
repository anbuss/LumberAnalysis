package com.notinuse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lumberanalysis.model.User;

public class ActiveUserList extends BasePage implements Serializable {
    private static final long serialVersionUID = -2725378005612769815L;

    public ActiveUserList() {
    //    setSortColumn("username");
    }
    
    public List<User> getUsers() {
    //    Set<User> users = (Set<User>) getServletContext().getAttribute(UserCounterListener.USERS_KEY);
      //  if (users != null) {
       //     return sort(new ArrayList<User>(users));
       // } else {
            return new ArrayList<User>();
        //}
    }
}

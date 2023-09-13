package org.launchcode.scorekeeperapp.models;

import javax.persistence.Entity;

@Entity
public class Host extends User{

    public Host(){
    }

    public Host (String username, String email, String password){
        super();
    }

}

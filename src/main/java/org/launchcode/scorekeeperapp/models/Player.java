package org.launchcode.scorekeeperapp.models;

import javax.persistence.Entity;

@Entity
public class Player extends User{

    public Player(){

    }

    public Player(String username, String email, String password){
        super();
    }
}


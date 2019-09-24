package com.irichment.identity.api.vo;

/**
 * @author canang technologies
 */
public class User extends Principal {

    private String realName;
    private String email;
    private String password;
    private Actor actor;

    public String getRealName() { return realName; }

    public void setRealName(String realName) { this.realName = realName; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    @Override
    public String getPassword() { return password; }

    @Override
    public void setPassword(String password) { this.password = password; }

    public Actor getActor() { return actor; }

    public void setActor(Actor actor) { this.actor = actor; }
}

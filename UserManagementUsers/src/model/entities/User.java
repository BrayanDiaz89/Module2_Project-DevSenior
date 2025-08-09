package model.entities;

import model.enums.UserRole;
import model.enums.UserState;

public class User {

    private String first_name;
    private String last_name;
    private Integer id = 0;
    private String username;
    private String password;
    private UserRole role;
    private UserActionSys[] actions = new UserActionSys[100];
    private UserState state;

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public UserActionSys[] getActions() {
        return actions;
    }

    public UserState getState() {
        return state;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setActions(UserActionSys[] actions) {
        this.actions = actions;
    }

    public User(String first_name, String last_name, String username, String password, UserRole role, UserActionSys[] actions) {
        this.first_name = first_name;
        this.last_name = last_name;
        id++;
        this.username = username;
        this.password = password;
        this.role = role;
        this.actions = actions;
    }

    public User(String first_name, String last_name, UserActionSys[] actions) {
        this.first_name = first_name;
        this.last_name = last_name;
        id++;
        this.actions = actions;
    }

    public void lockUser(){
        this.state = UserState.LOCKED;
    }
    public void maximumLockUser(){
        this.state = UserState.MAX_LOCK;
    }
}

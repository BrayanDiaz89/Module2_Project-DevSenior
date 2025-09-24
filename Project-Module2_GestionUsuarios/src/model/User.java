package model;

import model.enums.RoleUser;
import model.enums.StateUser;

import java.util.Arrays;

public class User {
    public static User[] users = new User[100];
    private int id = 0;
    private String first_name;
    private String last_name;
    private String userName;
    private String password;
    private RoleUser userRole;
    private ActionUser[] actions = new ActionUser[50];
    private StateUser state;


    public User(String first_name, String last_name, String userName, String password, RoleUser userRole) {
        id += 1;
        this.first_name = first_name;
        this.last_name = last_name;
        this.userName = userName;
        this.password = password;
        this.userRole = userRole;
        state = StateUser.ACTIVE;
        actions[0] = new ActionUser("Usuario creado");
    }

    public User(String first_name, String last_name) {
        id += 1;
        this.first_name = first_name;
        this.last_name = last_name;
        userRole = RoleUser.GUEST;
        state = StateUser.ACTIVE;
        actions[0] = new ActionUser("Usuario creado");
    }

    public StateUser getState() {
        return state;
    }

    public ActionUser[] getActions() {
        return actions;
    }

    public void insertAction(ActionUser action){
        for(int i = 0; i < actions.length; i++){
            if(actions[i] == null){
                actions[i] = action;
                break;
            }
        }
    }

    public int getId() {
        return id;
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

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public RoleUser getUserRole() {
        return userRole;
    }
    public void asignedToAdminRole(User user){
        userRole = RoleUser.ADMIN;
        user.insertAction(new ActionUser(String.format("Se asignó rol admin a %s.", user.getUserName())));
    }

    public void setState(StateUser state){
        this.state = state;
    }

    @Override
    public String toString() {
        return String.format("""
                Datos del usuario:
                Id: %d
                Fullname: %s %s
                Username: %s
                Password: %s
                Role: %s
                State User: %s
                Actions: %s
                """, id, first_name, last_name, userName, password, userRole, state, Arrays.toString(actions));
    }
}

package model.entities;

import model.enums.UserRole;
import model.enums.UserState;

import java.util.Arrays;

public class User {

    private String first_name;
    private String last_name;
    private Integer id = 0;
    private String username;
    private String password;
    private UserRole role;
    private static UserActionSys[] actions = new UserActionSys[100];
    private UserState state;
    public static User[] users = new User[50];

    public User(){}

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

    public User(String first_name, String last_name, String username, String password, UserRole role) {
        this.first_name = first_name;
        this.last_name = last_name;
        id++;
        this.username = username;
        this.password = password;
        this.role = role;
        actions[0] = new UserActionSys(String.format("Se ha creado el usuario %s", first_name));
    }

    public User(String first_name, String last_name) {
        this.first_name = first_name;
        this.last_name = last_name;
        id++;
        username = "guest"+id;
        actions[0] = new UserActionSys(String.format("Se ha creado el usuario invitado %s", first_name));
    }

    public void enterAnAction(UserActionSys action){
        var actionsUser = actions;
        for(int i= 0; i < actionsUser.length; i++){
            if(actionsUser[i] == null){
                actionsUser[i] = action;
            } else {
                System.out.println("Haz llenado tu límite de acciones a registrar, debes eliminar acciones para continuar.");
            }
        }
    }

    public boolean theCurrentUserIsAdmin(User user){
        return user.getRole().equals(UserRole.ADMIN);
    }

    public void updateActionsUser(User user, String messageAction){
        user.enterAnAction(new UserActionSys(messageAction));
    }

    public void lockUser(){
        this.state = UserState.LOCKED;
    }
    public void maximumLockUser(){
        this.state = UserState.MAX_LOCK;
    }

    public void unlockUser(){
        this.state = UserState.ACTIVE;
    }

    public void assignedToUserRoleAdmin(){
        this.role = UserRole.ADMIN;
    }

    @Override
    public String toString() {
        return String.format("""
                Datos de usuario:
                Id: %d
                Nombre completo: %s %s
                Username: %s
                Role: %s
                Actions: %s
                """, id, first_name, last_name, username, role.toString(), Arrays.toString(actions));
    }

    public String userGuest() {
        return String.format("""
                Datos de usuario:
                Nombre completo: %s %s
                Username: %s
                Role: %s
                Actions: %s
                """, first_name, last_name, username, role.toString(), Arrays.toString(actions));
    }
}

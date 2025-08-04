package service;

import java.util.Scanner;

import model.entitie.User;
import model.enums.UserRole;

/*      this.first_name = first_name;
        this.last_name = last_name;
        id++;
        this.username = username;
        this.password = password;
        this.role = role;
        actions = new UserAction[100];
        actions[0] = new UserAction(String.format("Se creó el usuario %s", username));
        state = UserState.ACTIVE;*/
public class UserManager {

    private User[] userList = new User[50];
    
    public UserManager(){}

    public User[] getUserList() {
        return userList;
    }

    public void setUserList(User[] userList) {
        this.userList = userList;
    } 

}

package service;

import model.entities.User;
import service.crud.CreateUser;
import service.crud.DeleteUser;
import service.crud.UpdateUser;
import service.login.AuthenticatorUser;

import java.util.Scanner;

public class UserService {

    public UserService() {}

    CreateUser createUser = new CreateUser();
    UpdateUser updateUser = new UpdateUser();
    DeleteUser deleteUser = new DeleteUser();
    AuthenticatorUser loginUser = new AuthenticatorUser();

    public void createUserService(Scanner keyboard){
        createUser.createUser(keyboard);
    }

    public void updateUserService(Scanner keyboard){
        updateUser.updateUser(keyboard);
    }

    public void deleteUser(User user, Scanner keyboard){
        deleteUser.findUserByDelete(user, keyboard);
    }

    public User authenticationUser(Scanner keyboard){
        return loginUser.findUser(keyboard);
    }
}

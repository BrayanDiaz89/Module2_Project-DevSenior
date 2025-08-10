package service;

import service.crud.CreateUser;
import service.crud.DeleteUser;
import service.crud.UpdateUser;

import java.util.Scanner;

public class UserService {

    public UserService() {}

    CreateUser createUser = new CreateUser();
    UpdateUser updateUser = new UpdateUser();
    DeleteUser deleteUser = new DeleteUser();

    public void createUserService(Scanner keyboard){
        createUser.createUser(keyboard);
    }

    public void updateUserService(Scanner keyboard){
        updateUser.updateUser(keyboard);
    }

    public void deleteUser(Scanner keyboard){
        deleteUser.findUser(keyboard);
    }
}

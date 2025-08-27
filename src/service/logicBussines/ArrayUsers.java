package service.logicBussines;

import model.entities.User;
import model.enums.UserRole;
import model.enums.UserState;

import java.util.Arrays;
import java.util.Scanner;

public class ArrayUsers {

    public static void getAllUsers(){
        System.out.println("La lista de todos los usuarios registrados es: ");
        System.out.println(Arrays.toString(User.users));
    }

    public User getUserByUsername(Scanner keyboard){
        System.out.println("Digite el username: ");
        var username = keyboard.nextLine();
        for(int i = 0; i < User.users.length; i++){
            if(User.users[i].getUsername().equalsIgnoreCase(username)){
                return User.users[i];
            }
        }
        return null;
    }

    public User getUserById(Scanner keyboard){
        System.out.println("Digite el id del usuario: ");
        var idUser = keyboard.nextInt();
        for(int i = 0; i < User.users.length; i++){
            if(User.users[i].getId() == idUser){
                return User.users[i];
            }
        }
        return null;
    }

    public User getUserByRole(Scanner keyboard){
        System.out.println("Digite el rol del usuario: ");
        var roleUser = keyboard.nextLine();
        for(int i = 0; i < User.users.length; i++){
            if(User.users[i].getRole().equals(UserRole.ADMIN)){
                return User.users[i];
            }
        }
        return null;
    }

    public void getInfoNameAndHistoryUser(User user){
        var nameUser = user.getUsername();
        var history = user.getActions();
        System.out.printf("El usuario %s, ha realizado las acciones: ", nameUser);
        System.out.println(Arrays.toString(history));
    }

    public void getAllHistoryByUsersArray(){
        for(int i = 0; i < User.users.length; i++){
            System.out.printf("Acciones del usuario %d", i+1);
            System.out.println(Arrays.toString(User.users[i].getActions()));
            System.out.printf("Finalizaron las acciones del usuario %d", i+1);
        }
    }

    public void getAllHistoryByGuestUsers(){
        for(int i = 0; i < User.users.length; i++){
            if(User.users[i].getRole() == UserRole.GUEST){
                System.out.printf("Acciones del usuario %d", i+1);
                System.out.println(Arrays.toString(User.users[i].getActions()));
                System.out.printf("Finalizaron las acciones del usuario %d", i+1);
            }
        }
    }

    public void getAllLockedUsers(){
        System.out.println("Lista de usuarios bloqueados: ");
        for(int i = 0; i < User.users.length; i++){
            if(User.users[i].getState() == UserState.LOCKED){
                var usernameLocked = User.users[i].getUsername();
                System.out.println(usernameLocked);
            }
        }
    }

}

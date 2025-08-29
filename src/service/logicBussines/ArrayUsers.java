package service.logicBussines;

import model.entities.User;
import model.enums.UserRole;
import model.enums.UserState;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class ArrayUsers {

    private static final User[] arrayUsers = User.users;

    public static void getAllUsers(){
        System.out.println("La lista de todos los usuarios registrados, con sus datos completos es: ");
        System.out.println(Arrays.toString(User.users));
    }

    public static void getListNamesOfAllUsers(){
        System.out.println("Lista de usuarios registrados: ");
        for(int i = 0; i < arrayUsers.length; i++){
            var fullName = arrayUsers[i].getFirst_name()+ " "+ arrayUsers[i].getLast_name();
            System.out.println(fullName);
        }
    }

    public static User getUserByUsername(String username){
        for(int i = 0; i < arrayUsers.length; i++){
            if(arrayUsers[i].getUsername().equalsIgnoreCase(username)){
                return arrayUsers[i];
            }
        }
        return null;
    }

    public static User getUserById(Integer idUser){
        for(int i = 0; i < arrayUsers.length; i++){
            if(Objects.equals(arrayUsers[i].getId(), idUser)){
                return arrayUsers[i];
            }
        }
        return null;
    }

    public static User getUserByRole(UserRole roleUser){
        for(int i = 0; i < arrayUsers.length; i++){
            var role = arrayUsers[i].getRole();
            if(role.equals(roleUser)){
                System.out.println("Usuario con el rol mencionado: ");
                return arrayUsers[i];
            }
        }
        return null;
    }

    public static void getInfoNameAndHistoryUser(User user){
        var nameUser = user.getUsername();
        var history = user.getActions();
        System.out.printf("El usuario %s, ha realizado las acciones: ", nameUser);
        System.out.println(Arrays.toString(history));
    }

    public static void getAllHistoryByUsersArray(){
        for(int i = 0; i < arrayUsers.length; i++){
            System.out.printf("Acciones del usuario %d", i+1);
            System.out.println(Arrays.toString(arrayUsers[i].getActions()));
            System.out.printf("Finalizaron las acciones del usuario %d", i+1);
        }
    }

    public static void getAllHistoryByGuestUsers(){
        for(int i = 0; i < arrayUsers.length; i++){
            if(arrayUsers[i].getRole() == UserRole.GUEST){
                System.out.printf("Acciones del usuario %d", i+1);
                System.out.println(Arrays.toString(arrayUsers[i].getActions()));
                System.out.printf("Finalizaron las acciones del usuario %d", i+1);
            }
        }
    }

    public static void getAllLockedUsers(){
        System.out.println("Lista de usuarios bloqueados: ");
        for(int i = 0; i < arrayUsers.length; i++){
            if(arrayUsers[i].getState() == UserState.LOCKED){
                var usernameLocked = arrayUsers[i].getUsername();
                System.out.println(usernameLocked);
            }
        }
    }
}

package service.login;

import model.entities.User;
import service.SelectUser;
import service.crud.CreateUser;
import service.crud.UpdateUser;

import java.util.Scanner;

public class AuthenticatorUser implements SelectUser {

    CreateUser getUsersArray = new CreateUser();
    UpdateUser getMethodPasswordIsCorrect = new UpdateUser();

    public User findUser(Scanner keyboard){
        System.out.println("Digite el nombre del usuario (Apodo): ");
        var username = keyboard.nextLine();
        var arrayUsers = getUsersArray.getUsers();

        for(int i = 0; i < arrayUsers.length; i++) {
            if (arrayUsers[i].getUsername().equalsIgnoreCase(username)) {
                var user = arrayUsers[i];
                System.out.printf("¡Hola %s!, seguiremos con tú contraseña", user.getUsername());
                var validateAuth = getMethodPasswordIsCorrect.thePasswordIsCorrect(3, user, keyboard);
                if (validateAuth) {
                    System.out.printf("Bienvenido %s, que gusto tenerte por aquí de nuevo!.", user.getUsername());
                    return user;
                } else {
                    System.out.println("No ha sido posible iniciar sesión! :(. Valida si tú usuario ha sido bloqueado.");
                    return null;
                }
            } else {
                System.out.println("El username especificado no existe en la lista de usuarios.");
            }
        }
        return null;
    }

}

package service.login;

import model.entities.User;
import service.SelectUser;
import service.crud.CreateUser;
import service.crud.UpdateUser;

import java.util.Scanner;

public class AuthenticatorUser implements SelectUser {

    UpdateUser getMethodPasswordIsCorrect = new UpdateUser();

    public User findUser(Scanner keyboard){
        System.out.println("Digite el nombre del usuario (Apodo): ");
        var username = keyboard.nextLine();

        for(int i = 0; i < User.users.length; i++) {
            if (User.users[i].getUsername().equalsIgnoreCase(username)) {
                var user = User.users[i];
                System.out.printf("¡Hola %s!, seguiremos con tú contraseña", user.getUsername());
                var validateAuth = getMethodPasswordIsCorrect.thePasswordIsCorrect(3, user, keyboard);
                if (validateAuth) {
                    System.out.printf("Bienvenido %s, que gusto tenerte por aquí de nuevo!.", user.getUsername());
                    user.updateActionsUser(user, String.format("%s ha iniciado sesión.", user.getUsername()));
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

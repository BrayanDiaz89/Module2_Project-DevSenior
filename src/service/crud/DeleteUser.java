package service.crud;

import model.entities.User;
import java.util.Scanner;

public class DeleteUser {

    public User findUserByDelete(User user, Scanner keyboard) {
        if(!user.theCurrentUserIsAdmin(user)){
            user.updateActionsUser(user, "Intentó eliminar usuarios. Acceso denegado.");
            System.out.println("Permiso denegado. No eres administrador.");
            return null;
        }
        System.out.println("Digite el nombre del usuario (apodo): ");
        var username = keyboard.nextLine();
        for (int i = 0; i < User.users.length; i++) {
            if (User.users != null && User.users[i].getUsername().equalsIgnoreCase(username)) {
                var userDelete = User.users[i];
                if (validationDeleteUser(userDelete, keyboard)) {
                    User.users[i] = null;
                    user.updateActionsUser(user, String.format("Se eliminó al usuario %s", userDelete.getUsername()));
                    System.out.println("Usuario eliminado con éxito.");
                    return User.users[i];
                } else {
                    user.updateActionsUser(user, String.format("Se canceló la operación para la eliminar al usuario: %s", userDelete.getUsername()));
                    System.out.println("Se ha cancelado la operación.");
                }
            } else {
                System.out.println("Username no encontrado en la lista de usuarios.");
            }
        }
        return null;
    }

    public boolean validationDeleteUser(User user, Scanner keyboard){
        while (true){
            System.out.printf("Está seguro que desea eliminar a el usuario %s. Presione Y=si o N=no", user.getUsername());
            String decisionUser = keyboard.nextLine();
            if(decisionUser.equalsIgnoreCase("y")){
                return true;
            } else if(decisionUser.equalsIgnoreCase("n")){
                return false;
            } else {
                System.out.println("Opción no válida, intente nuevamente.");
            }
        }
    }

}

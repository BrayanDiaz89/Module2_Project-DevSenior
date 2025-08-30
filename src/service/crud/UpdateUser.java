package service.crud;

import model.entities.User;
import model.entities.UserActionSys;
import model.enums.UserRole;
import service.SelectUser;

import java.util.Scanner;

public class UpdateUser implements SelectUser {

    private User user = new User();

    public User findUser(Scanner keyboard) {
        System.out.println("Digite el nombre del usuario (apodo): ");
        var username = keyboard.nextLine();
        for (int i = 0; i < User.users.length; i++) {
            if (User.users != null && User.users[i].getUsername().equalsIgnoreCase(username)) {
                return User.users[i];
            }
        }
        return null;
    }

    public void updateUser(Scanner keyboard) {
        var userUpdate = findUser(keyboard);
        if (userUpdate == null) {
            System.out.println("No existe un usuario con el username especificado.");
        } else {
            requestDataByUpdateUser(keyboard, userUpdate);
        }
    }

    public boolean validatePassword(User user, String oldPassword) {
        var passwordUser = user.getPassword();
        return passwordUser.equalsIgnoreCase(oldPassword);
    }

    public String requestDataByUpdateUser(Scanner keyboard, User userUpdate) {
        String menuOptions = """
                ¿Qué deseas actualizar?
                1) Nombre completo
                2) Contraseña
                3) Nombre y contraseña
                4) Nada, quiero salir.
                """;
        System.out.println(menuOptions);
        Integer decisionUser = keyboard.nextInt();
        return switch (decisionUser) {
            case 1 -> updateNameUser(userUpdate, keyboard);
            case 2 -> {
                if(!userUpdate.getRole().equals(UserRole.GUEST)) {
                   yield updatePassword(userUpdate, keyboard);
                }
                yield "Eres usuario invitado, no tienes contraseña.";
            }
            case 3 -> {
                if(!userUpdate.getRole().equals(UserRole.GUEST)) {
                    updateNameUser(userUpdate, keyboard);
                    yield updatePassword(userUpdate, keyboard);
                }
                yield "Eres usuario invitado, no tienes contraseña.";
            }
            case 4 -> {
                user.updateActionsUser(user, "Se canceló la operación de actualización.");
                yield "Saliendo...";
            }
            default -> {
                user.updateActionsUser(user, "Se canceló la operación de actualización.");
                yield "Opción no válida.";
            }
        };
    }

    public String updatePassword(User user, Scanner keyboard) {
        if (!thePasswordIsCorrect(3, user, keyboard)) {
            return "No ha sido posible actualizar la contraseña.";
        } else {
            System.out.println("Digite su nueva contraseña: ");
            var passwordUpdate = keyboard.nextLine();
            user.setPassword(passwordUpdate);
            user.updateActionsUser(user, "Haz actualizado exitosamente tú contraseña.");
            return "Se han actualizado correctamente los datos del usuario.";
        }
    }

    public String updateNameUser(User user, Scanner keyboard){
        System.out.println("Digite su nombre: ");
        var firstName = keyboard.nextLine();
        user.setFirst_name(firstName);
        System.out.println("Digite su apellido: ");
        var lastName = keyboard.nextLine();
        user.setLast_name(lastName);
        user.updateActionsUser(user, "Haz actualizado exitosamente tú nombre.");
        return "Se han actualizado correctamente los datos del usuario.";
    }

    public Boolean thePasswordIsCorrect(Integer numberOfAttempsMax, User user, Scanner keyboard){
        String oldPassword;
        Integer numberOfAttempsUser = 0;
        do {
            System.out.println("Digite su contraseña actual: ");
            oldPassword = keyboard.nextLine();
            if (!validatePassword(user, oldPassword)) {
                numberOfAttempsUser++;
                System.out.printf("Contraseña incorrecta, le quedan %d intentos", (numberOfAttempsMax - numberOfAttempsUser));
            } else {
                user.updateActionsUser(user, String.format("%s se logeó correctamente en el sistema.", user.getUsername()));
                System.out.println("Contraseña correcta, puede continuar.");
                return true;
            }
        } while (numberOfAttempsUser < numberOfAttempsMax);
        user.updateActionsUser(user, String.format("%s Ha bloqueado su cuenta bloqueada por superar número máximo de intentos en validación de contraseña.", user.getUsername()));
        user.lockUser();
        System.out.println("Ha superado la cantidad máxima de intentos, el usuario ha sido bloqueado por seguridad.");
        return false;
    }
}

package service.crud;

import model.entities.User;
import java.util.Scanner;

public class UpdateUser implements SelectUser{

    private CreateUser getUsersArray = new CreateUser();

    public User findUser(Scanner keyboard){
        System.out.println("Digite el nombre del usuario (apodo): ");
        var username = keyboard.nextLine();
        var arrayUsers = getUsersArray.getUsers();
        for(int i = 0; i < arrayUsers.length; i++){
            if(arrayUsers != null && arrayUsers[i].getUsername().equalsIgnoreCase(username)){
                return arrayUsers[i];
            }
        }
        return null;
    }

    public void updateUser(Scanner keyboard){
        var userUpdate = findUser(keyboard);
        if(userUpdate == null){
            System.out.println("No existe un usuario con el username especificado.");
        } else {
            requestDataByUpdateUser(keyboard, userUpdate);
        }
    }

    public boolean validatePassword(User user, String oldPassword){
        var passwordUser = user.getPassword();
        return passwordUser.equalsIgnoreCase(oldPassword);
    }

    public String requestDataByUpdateUser(Scanner keyboard, User userUpdate){
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
            case 2 -> updatePassword(userUpdate, keyboard);
            case 3 -> {
                updateNameUser(userUpdate, keyboard);
                yield updatePassword(userUpdate, keyboard);
            }
            case 4 -> "Saliendo...";
            default -> "Opción no válida.";
        };
    }

    public String updatePassword(User user, Scanner keyboard){
        String oldPassword;
        Integer numberOfAttempsMax = 3;
        Integer numberOfAttempsUser = 0;
        do{
            System.out.println("Digite su contraseña actual: ");
            oldPassword = keyboard.nextLine();
            if(!validatePassword(user, oldPassword)){
                numberOfAttempsUser++;
            } else {
                System.out.println("Digite su nueva contraseña: ");
                var passwordUpdate = keyboard.nextLine();
                user.setPassword(passwordUpdate);
                return "Se han actualizado correctamente los datos del usuario.";
            }
        }while (numberOfAttempsUser <= numberOfAttempsMax);
            user.lockUser();
            return "Ha superado la cantidad máxima de intentos, el usuario ha sido bloqueado por seguridad.";
    }

    public String updateNameUser(User user, Scanner keyboard){
        System.out.println("Digite su nombre: ");
        var firstName = keyboard.nextLine();
        user.setFirst_name(firstName);
        System.out.println("Digite su apellido: ");
        var lastName = keyboard.nextLine();
        user.setLast_name(lastName);
        return "Se han actualizado correctamente los datos del usuario.";
    }
}

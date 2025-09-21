package service;

import model.ActionUser;
import model.User;
import model.dto.UserActionsDTO;
import model.enums.RoleUser;
import model.enums.StateUser;

import java.util.Scanner;

public class UserGenericService {

    private final int MAX_ATTEMPTS = 3;

    public User createFirstUser(Scanner keyboard){
        System.out.println("Digite el primer nombre: ");
        String firstName = keyboard.nextLine();
        System.out.println("Digite el apellido: ");
        String lastName = keyboard.nextLine();
        System.out.println("Digite el nombre de usuario: ");
        String userName = keyboard.nextLine();
        System.out.println("Digite la contraseña: ");
        String password = keyboard.nextLine();
        return new User(firstName, lastName, userName, password, RoleUser.ADMIN);
    }

    public User createStandardUser(Scanner keyboard) {
        System.out.println("Digite el primer nombre: ");
        String firstName = keyboard.nextLine();
        System.out.println("Digite el apellido: ");
        String lastName = keyboard.nextLine();
        System.out.println("Digite el nombre de usuario: ");
        String userName = keyboard.nextLine();
        while (!existsUserName(userName)){
            System.out.println("El username ya existe, por favor escoge otro: ");
            userName = keyboard.nextLine();
        }
        System.out.println("Digite la contraseña: ");
        String password = keyboard.nextLine();
        return new User(firstName, lastName, userName, password, RoleUser.STANDARD);
    }

    public boolean existsUserName(String username){
        for (User user : User.users) {
            if (user.getUserName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public User getUserByUsername(Scanner keyboard){
        System.out.println("Digite el nombre de usuario: ");
        String username = keyboard.nextLine();
        for (User user : User.users) {
            if (user.getUserName().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean updateMePassword(Scanner keyboard, User user) {
        int attemps = 0;
        if(user == null || user.getState() != StateUser.ACTIVE) return false;
        boolean passwordCorrect = false;
        while (attemps < MAX_ATTEMPTS){
            System.out.println("Digite su contraseña actual: ");
            String passwordActual = keyboard.nextLine();

            if (passwordIsCorrect(user, passwordActual)) {
                passwordCorrect = true;
                break;
            }
            attemps += 1;
            user.insertAction(new ActionUser("Intento fallido de cambio de contraseña."));
            System.out.printf("Contraseña incorrecta, tienes: %d intentos", MAX_ATTEMPTS - attemps);
        }

        if(!passwordCorrect){
            System.out.println("Ha excedido el número máximo de intentos, su usuario ha sido bloqueado.");
            user.setState(StateUser.LOCKED);
            user.insertAction(new ActionUser("Usuario bloqueado, por superar número máximo de intentos fallidos, en ingreso de contraseña."));
            return false;
        }

        System.out.println("Digita la nueva contraseña: ");
        String newPassword = keyboard.nextLine();
        user.setPassword(newPassword);
        return true;
    }

    public boolean passwordIsCorrect(User user, String password){
       return user.getPassword().equalsIgnoreCase(password);
    }

    public User[] getAllUsers(User user){
        user.insertAction(new ActionUser("Consulta de todos los usuarios."));
        return User.users;
    }

    public UserActionsDTO getMeActions(User user){
        user.insertAction(new ActionUser("Consulta de acciones propias."));
        return new UserActionsDTO(user.getUserName(), user.getActions());
    }

    public boolean updateMeUser(Scanner keyboard, User user){
        System.out.println("Digite el primer nombre: ");
        String firstName = keyboard.nextLine();
        System.out.println("Digite el apellido: ");
        String lastName = keyboard.nextLine();
        if(updateMePassword(keyboard, user)){
            user.setFirst_name(firstName);
            user.setLast_name(lastName);
            user.insertAction(new ActionUser("Usuario actualizado."));
            return true;
        }
        return false;
    }

}

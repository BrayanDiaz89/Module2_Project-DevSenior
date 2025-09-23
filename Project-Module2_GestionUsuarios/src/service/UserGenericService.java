package service;

import model.ActionUser;
import model.User;
import model.dto.UserActionsDTO;
import model.dto.UserGenericDTO;
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
        System.out.println("Usuario  creado con éxito.");
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

    public User createUserGuest(Scanner keyboard){
        System.out.println("Digite su primer nombre: ");
        String firstName = keyboard.nextLine();
        System.out.println("Digite su apellido: ");
        String lastName = keyboard.nextLine();
        User userGuest = new User(firstName, lastName);
        return userGuest;
    }

    public User login(Scanner keyboard) {
        var users = User.users;
        User user = null;
        System.out.println("Ingrese el nombre de usuario (Username): ");
        String username = keyboard.nextLine();
        for (int i = 0; i < users.length; i++) {
            if (users[i].getUserName().equalsIgnoreCase(username)) {
                user = users[i];
            }
        }
        if (user != null) {
            if (passwordValidationThreeAttemps(keyboard, user)) {
                user.insertAction(new ActionUser("Se ha iniciado sesión."));
                System.out.println("Inicio de sesión success");
                return user;
            } else{
                System.out.println("No pudiste iniciar sesión, tú usuario ha sido bloqueado.");
                user.insertAction(new ActionUser("El usuario supero el número máximo de intentos fallidos al digitar su contraseña (LOCKED)."));
            }
        }
        return user;
    }

    public boolean existsUserName(String username){
        for (User user : User.users) {
            if (user.getUserName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public User getMeUserData(User user){
        return user;
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
        if(passwordValidationThreeAttemps(keyboard, user)) {
            System.out.println("Digita la nueva contraseña: ");
            String newPassword = keyboard.nextLine();
            user.setPassword(newPassword);
            return true;
        }
        return false;
    }

    public boolean passwordIsCorrect(User user, String password){
       return user.getPassword().equalsIgnoreCase(password);
    }

    public boolean passwordValidationThreeAttemps(Scanner keyboard, User user){
        var attemps = 0;
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
        return false;
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

    public UserGenericDTO[] getAllUsers(User user){
        var users = User.users;
        UserGenericDTO[] usersGenerics = new UserGenericDTO[users.length];
        System.out.println("Obteniendo a todos los usuarios...");
        for (int i = 0; i < users.length; i++){
            String nameCompleted = users[i].getFirst_name() + users[i].getLast_name();
            usersGenerics[i] = new UserGenericDTO(users[i].getId(), nameCompleted, users[i].getUserRole());
        }
        user.insertAction(new ActionUser("Consulta de todos los usuarios."));
        return usersGenerics;
    }

}

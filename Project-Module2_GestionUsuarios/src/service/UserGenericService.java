package service;

import model.ActionUser;
import model.User;
import model.dto.UserActionsDTO;
import model.dto.UserGenericDTO;
import model.enums.RoleUser;
import model.enums.StateUser;

import java.rmi.server.UID;
import java.util.Scanner;

public class UserGenericService {

    private final int MAX_ATTEMPTS = 3;

    public UserGenericService() {
    }

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
        User user = new User(firstName, lastName, userName, password, RoleUser.ADMIN);
        if(insertUserInArray(user)){
            System.out.println("Usuario guardado con éxito.");
        }
        return user;
    }

    public User createStandardUser(Scanner keyboard) {
        System.out.println("Digite el primer nombre: ");
        String firstName = keyboard.nextLine();
        System.out.println("Digite el apellido: ");
        String lastName = keyboard.nextLine();
        System.out.println("Digite el nombre de usuario: ");
        String userName = keyboard.nextLine();
        while (existsUserName(userName)){
            System.out.println("El username ya existe, por favor escoge otro: ");
            userName = keyboard.nextLine();
        }
        System.out.println("Digite la contraseña: ");
        String password = keyboard.nextLine();
        User user = new User(firstName, lastName, userName, password, RoleUser.STANDARD);
        if(insertUserInArray(user)){
            System.out.println("Usuario guardado con éxito.");
        }
        return user;
    }

    public User createUserGuest(Scanner keyboard){
        System.out.println("Digite su primer nombre: ");
        String firstName = keyboard.nextLine();
        System.out.println("Digite su apellido: ");
        String lastName = keyboard.nextLine();
        User userGuest = new User(firstName, lastName);
        if(insertUserInArray(userGuest)){
            System.out.println("Usuario guardado con éxito.");
        }
        return userGuest;
    }

    public boolean insertUserInArray(User user){
        for(int i = 0; i < User.users.length; i++){
            if(User.users[i] == null){
                User.users[0] = user;
                return true;
            }
        }
        return false;
    }

    public User login(Scanner keyboard) {
        User user = getUserByUsername(keyboard);
        if (user != null) {
            if (passwordValidationThreeAttemps(keyboard, user)) {
                user.insertAction(new ActionUser("Se ha iniciado sesión."));
                System.out.println("Inicio de sesión success");
                return user;
            }
        }
        return user;
    }

    public boolean existsUserName(String username){
        for (int i = 0; i < User.users.length; i++) {
            if(User.users[0].getUserName().equalsIgnoreCase(username)){
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
        for (int i = 0; i < User.users.length; i++) {
            if(User.users[0].getUserName().equalsIgnoreCase(username)){
                return User.users[0];
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
        // Verificar que los campos no estén vacíos
        if (firstName.trim().isEmpty() || lastName.trim().isEmpty()) {
            System.out.println("Error: El nombre y apellido no pueden estar vacíos.");
            return false;
        }
        
        // Verificar la contraseña actual
        System.out.println("Digite su contraseña actual para confirmar los cambios:");
        String currentPassword = keyboard.nextLine();
        
        if (user.getPassword().equals(currentPassword)) {
            // Actualizar los datos del usuario
            user.setFirst_name(firstName.trim());
            user.setLast_name(lastName.trim());
            user.insertAction(new ActionUser("Datos de usuario actualizados."));
            System.out.println("¡Datos actualizados correctamente!");
            return true;
        } else {
            System.out.println("Error: Contraseña incorrecta. No se realizaron cambios.");
            return false;
        }
    }

    public UserGenericDTO[] getAllUsers(User user){
        UserGenericDTO[] usersGenerics = new UserGenericDTO[User.users.length];
        System.out.println("Obteniendo a todos los usuarios...");
        for (int i = 0; i < User.users.length; i++){
            if(User.users[i] != null){
                String nameCompleted = User.users[i].getFirst_name() + User.users[i].getLast_name();
                usersGenerics[i] = new UserGenericDTO(User.users[i].getId(), nameCompleted, User.users[i].getUserRole());
            }
        }
        user.insertAction(new ActionUser("Consulta de todos los usuarios."));
        return usersGenerics;
    }

}

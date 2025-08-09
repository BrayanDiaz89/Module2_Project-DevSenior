package service.crud;
import model.entities.User;
import model.enums.UserRole;

import java.util.Scanner;

public class CreateUser {

    private User[] users = new User[50];

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Boolean userAdminExists(){
        for(int i = 0; i < users.length; i++){
            if(users[i] != null && users[i].getRole() == UserRole.ADMIN){
                return true;
            }
        }
        return false;
    }

    public UserRole getUserRole(Scanner keyboard){
        String roleMenu = """
                Digita el rol del usuario:
                1) ADMIN
                2) STANDARD
                3) GUEST
                """;
        Integer userDecision = keyboard.nextInt();
        return switch (userDecision) {
            case 1 -> UserRole.ADMIN;
            case 2 -> UserRole.STANDARD;
            case 3 -> UserRole.GUEST;
            default ->  {
                System.out.println("Opción no válida. Se asignará usuario GUEST por defecto.");
                yield  UserRole.GUEST;
            }
        };
    }

    public Boolean validateUsernameExists(String username){
        for(int i = 0; i < users.length; i++){
            if (users[i].getUsername().equalsIgnoreCase(username)){
                return true;
            }
        }
        return false;
    }

    public void addUsersToArray(User user){
        Boolean userAdd = false;
        for(int i = 0; i < users.length; i++){
            if(users[i] == null){
                users[i] = user;
                userAdd = true;
                break;
            }
        }
        if(!userAdd){
            System.out.println("Se han llenado todas las plazas, no hay cupo para un usuario más.");
        }
    }

    public void createUser(Scanner keyboard){
        if(!userAdminExists()){
            Boolean validationName = true;
            System.out.println("Módulo de creación usuario ADMIN.");
            System.out.println("Digita tú nombre: ");
            String nameUser = keyboard.nextLine();
            System.out.println("Digita tú apellido: ");
            String lastNameUser = keyboard.nextLine();
            do {
                System.out.println("Digita tú apodo (Inicio de sesión): ");
                String userNameLogin = keyboard.nextLine();
                validationName = validateUsernameExists(userNameLogin);
            }while (!validationName);
            System.out.println("Digita tú contraseña (Inicio de sesión): ");
            String password = keyboard.nextLine();
            var userRole = UserRole.ADMIN;
            var user = new User(nameUser, lastNameUser, userNameLogin, password, userRole);
            addUserToArrayUsers(user);
        }
    }
}

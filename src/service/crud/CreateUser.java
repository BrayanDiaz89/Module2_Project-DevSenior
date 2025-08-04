package service.crud;
import java.util.Scanner;
import model.entitie.User;
import model.enums.UserRole;

public class CreateUser {

    private User[] users = new User[50];

    public Boolean userAdminExists(){
        if(users.length == 0){
            return false;
        }
        return true;
    }

    public UserRole getRoleUser(Scanner keyboard){
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
            default -> {
                System.out.println("Opción no válida. Se asignará usuario Guest por defecto.");
                yield UserRole.GUEST;
            }
        };
    }

    public void addUserToArrayUsers(User user){
        Boolean userAdded = false;
        for(int i = 0; i < users.length; i++){
            if(users[i] == null){
                users[i] = user;
                userAdded = true;
                break;
            }
        }
        if(!userAdded){
            System.out.println("Se han llenado todas las plazas, no hay cupo para un usuario más.");
        }
    }
    
    public void createUser(Scanner keyboard){
        if(!userAdminExists()){
            System.out.println("Módulo de creación usuario ADMIN.");
            System.out.println("Digita tú nombre: ");
            String nameUser = keyboard.nextLine();
            System.out.println("Digita tú apellido: ");
            String lastNameUser = keyboard.nextLine();
            System.out.println("Digita tú apodo (Inicio de sesión): ");
            String userNameLogin = keyboard.nextLine();
            System.out.println("Digita tú contraseña (Inicio de sesión): ");
            String password = keyboard.nextLine();
            var userRole = UserRole.ADMIN;
            var user = new User(nameUser, lastNameUser, userNameLogin, password, userRole);
            addUserToArrayUsers(user);
        } else {
            var userRole = getRoleUser(keyboard);
            if(userRole != UserRole.GUEST){
                System.out.println("Módulo de creación usuario STANDARD.");
                System.out.println("Digita tú nombre: ");
                String nameUser = keyboard.nextLine();
                System.out.println("Digita tú apellido: ");
                String lastNameUser = keyboard.nextLine();
                System.out.println("Digita tú apodo (Inicio de sesión): ");
                String userNameLogin = keyboard.nextLine();
                System.out.println("Digita tú contraseña (Inicio de sesión): ");
                String password = keyboard.nextLine();
                var user = new User(nameUser, lastNameUser, userNameLogin, password, userRole);
                addUserToArrayUsers(user);
            } else {
                System.out.println("Módulo de creación usuario GUEST.");
                System.out.println("Digita tú nombre: ");
                String nameUser = keyboard.nextLine();
                System.out.println("Digita tú apellido: ");
                String lastNameUser = keyboard.nextLine();
                var user = new User(nameUser, lastNameUser);
                addUserToArrayUsers(user);
            }
        }
    }

    public User[] getUsers() {
        return users;
    }

}

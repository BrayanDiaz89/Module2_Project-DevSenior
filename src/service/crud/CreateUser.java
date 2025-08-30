package service.crud;
import model.entities.User;
import model.enums.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateUser {

    public Boolean userAdminExists(){
        for(int i = 0; i < User.users.length; i++){
            if(User.users[i] != null && User.users[i].getRole() == UserRole.ADMIN){
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
        for(int i = 0; i < User.users.length; i++){
            if(User.users.length == 0){
                return false;
            }else if(User.users[i].getUsername().equalsIgnoreCase(username)){
                return true;
            }
        }
        return false;
    }

    public void addUsersToArray(User user){
        Boolean userAdd = false;
        for(int i = 0; i < User.users.length; i++){
            if(User.users[i] == null){
                User.users[i] = user;
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
            createFirstUserProgram(keyboard);
        } else {
            createUserDuringProgramFlow(keyboard);
        }
    }

    public void createFirstUserProgram(Scanner keyboard){
        System.out.println("Módulo de creación usuario ADMIN.");
        List<String> basicInformationUser = requestBasicInformation(keyboard);
        List<String> informationAuthenticateUser = requestDataForAuthentication(keyboard);
        var userRole = UserRole.ADMIN;
        var user = new User(basicInformationUser.get(0), basicInformationUser.get(1),
                informationAuthenticateUser.get(0), informationAuthenticateUser.get(1), userRole);
        addUsersToArray(user);
    }

    public void createUserDuringProgramFlow(Scanner keyboard){
        System.out.println("Módulo de creación de usuarios.");
        var userRole = getUserRole(keyboard);
        if(userRole == UserRole.GUEST){
            createUserGuest(keyboard);
        }
        List<String> basicInformationUser = requestBasicInformation(keyboard);
        List<String> informationAuthenticateUser = requestDataForAuthentication(keyboard);
        var user = new User(basicInformationUser.get(0), basicInformationUser.get(1),
                informationAuthenticateUser.get(0), informationAuthenticateUser.get(1), userRole);
        addUsersToArray(user);
    }

    public void createUserGuest(Scanner keyboard){
        System.out.println("Creación de usuario invitado.");
        List<String> basicInformationUser = requestBasicInformation(keyboard);
        var userGuest = new User(basicInformationUser.get(0), basicInformationUser.get(1));
        addUsersToArray(userGuest);
    }

    public List<String> requestBasicInformation(Scanner keyboard){
        System.out.println("Digita tú nombre: ");
        String nameUser = keyboard.nextLine();
        System.out.println("Digita tú apellido: ");
        String lastNameUser = keyboard.nextLine();
        return new ArrayList<>(List.of(nameUser, lastNameUser));
    }

    public List<String> requestDataForAuthentication(Scanner keyboard){
        Boolean validationName;
        String userNameLogin;
        do {
            System.out.println("Digita tú apodo (Inicio de sesión): ");
            userNameLogin = keyboard.nextLine();
            validationName = validateUsernameExists(userNameLogin);
        }while (!validationName);
        System.out.println("Digita tú contraseña (Inicio de sesión): ");
        String password = keyboard.nextLine();
        return new ArrayList<>(List.of(userNameLogin, password));
    }
}

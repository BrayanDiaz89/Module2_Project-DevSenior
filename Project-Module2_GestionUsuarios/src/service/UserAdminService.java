package service;

import com.sun.security.jgss.GSSUtil;
import model.ActionUser;
import model.User;
import model.dto.UserActionsDTO;
import model.dto.UserCompletedDTO;
import model.enums.RoleUser;
import model.enums.StateUser;

import java.util.Scanner;

public class UserAdminService {

    private UserGenericService service = new UserGenericService();

    public User createUserFromPanelAdmin(Scanner keyboard, User userAdmin){
        System.out.println("Digite el primer nombre: ");
        String firstName = keyboard.nextLine();
        System.out.println("Digite el apellido: ");
        String lastName = keyboard.nextLine();
        System.out.println("Digite el nombre de usuario: ");
        String userName = keyboard.nextLine();
        while (!service.existsUserName(userName)){
            System.out.println("El username ya existe, por favor escoge otro: ");
            userName = keyboard.nextLine();
        }
        System.out.println("Digite la contraseña: ");
        String password = keyboard.nextLine();
        var role = selectRoleUser(keyboard);
        userAdmin.insertAction(new ActionUser(String.format("Se creó el usuario %s.", userName)));
        return new User(firstName, lastName, userName, password, role);
    }

    public RoleUser selectRoleUser(Scanner keyboard){
        System.out.println("""
                Seleccione uno de los dos roles disponibles: 
                1. ADMIN
                2. STANDARD
                """);
        int option = keyboard.nextInt();
        keyboard.nextLine();
        RoleUser role = switch (option){
            case 1 -> RoleUser.ADMIN;
            case 2 -> RoleUser.STANDARD;
            default -> {
                System.out.println("No ha seleccionado una opción válida, su usuario será standard.");
                yield RoleUser.STANDARD;
            }
        };
        return role;
    }

    public boolean deleteUser(String username, User userAdmin){
        var users = User.users;
        if(users != null){
            for(int i = 0; i < users.length; i++){
                if(users[i].getUserName().equalsIgnoreCase(username)){
                    users[i] = null;
                    userAdmin.insertAction(new ActionUser(String.format("Se eliminó el usuario %s.", username)));
                    return true;
                }
            }
        }
        userAdmin.insertAction(new ActionUser(String.format("Intento fallido de eliminación del usuario %s.", username)));
        return false;
    }

    public User updateUser(Scanner keyboard, User userAdmin){
        var user = service.getUserByUsername(keyboard);
        if(user != null){
            System.out.println("Digite el primer nombre: ");
            String firstName = keyboard.nextLine();
            System.out.println("Digite el apellido: ");
            String lastName = keyboard.nextLine();
            user.setFirst_name(firstName);
            user.setLast_name(lastName);
            userAdmin.insertAction(new ActionUser(String.format("Se actualizó a el usuario %s.", user.getUserName())));
        }

        return user;
    }

    public UserActionsDTO[] getAllUsersActions(User userAdmin){
        userAdmin.insertAction(new ActionUser("Consulta de acciones de todos los usuarios."));
        var users = User.users;
        var usersAndActions = new UserActionsDTO[users.length];
        if(users != null){
            for(int i = 0; i < users.length; i++){
                usersAndActions[i] = new UserActionsDTO(users[i].getUserName(), users[i].getActions());
            }
        }
        return usersAndActions;
    }

    public UserActionsDTO getUserActions(Scanner keyboard, User userAdmin){
        userAdmin.insertAction(new ActionUser("Consulta de acciones de un usuario."));
        var user = service.getUserByUsername(keyboard);
        if(user != null){
            return new UserActionsDTO(user.getUserName(), user.getActions());
        }
        return null;
    }

    public UserCompletedDTO[] getAllUsersLocked(User user){
        var users = User.users;
        UserCompletedDTO[] usersCompetedInfo = new UserCompletedDTO[users.length];
        System.out.println("Obteniendo a todos los usuarios bloqueados.");
        for(int i = 0; i < users.length; i++){
            if(users[i].getState() != StateUser.ACTIVE) {
                String nameCompleted = users[i].getFirst_name() + users[i].getLast_name();
                usersCompetedInfo[i] = new UserCompletedDTO(users[i].getId(),
                        nameCompleted,
                        users[i].getUserName(),
                        users[i].getUserRole(),
                        users[i].getState());
            }
        }
        if(usersCompetedInfo[0] == null){
            System.out.println("No existen usuarios bloqueados por el momento.");
        }
        return usersCompetedInfo;
    }

    public UserCompletedDTO[] getAllUsersActive(User user){
        var users = User.users;
        UserCompletedDTO[] usersCompetedInfo = new UserCompletedDTO[users.length];
        System.out.println("Obteniendo a todos los usuarios bloqueados.");
        for(int i = 0; i < users.length; i++){
            if(users[i].getState() == StateUser.ACTIVE) {
                String nameCompleted = users[i].getFirst_name() + users[i].getLast_name();
                usersCompetedInfo[i] = new UserCompletedDTO(users[i].getId(),
                        nameCompleted,
                        users[i].getUserName(),
                        users[i].getUserRole(),
                        users[i].getState());
            }
        }
        if(usersCompetedInfo[0] == null){
            System.out.println("No existen usuarios bloqueados por el momento.");
        }
        return usersCompetedInfo;
    }

}

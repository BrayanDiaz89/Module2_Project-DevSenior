package view;

import model.entities.User;
import model.entities.UserActionSys;
import model.enums.UserRole;
import service.UserService;
import service.logicBussines.ArrayUsers;
import view.entitie.MenuApp;

import java.awt.*;
import java.util.Arrays;
import java.util.Scanner;

public class ProgramViews {

    public ProgramViews(){}

    private UserService service = new UserService();

    private String menuMain= """
            ¡Bienvenido al sistema de usuarios!
            Selecciona una de las siguientes opciones:
            1) No tengo cuenta, quiero crear una
            2) Ya tengo cuenta, deseo iniciar sesión :)
            3) Salir.
            """;


    public void initialProgramFlow(Scanner keyboard){
        System.out.println(menuMain);
        boolean exit = false;
        Integer decisionUser = keyboard.nextInt();
        keyboard.nextLine();
        do {
            switch (decisionUser) {
                case 1:
                    service.createUserService(keyboard);
                    var user = service.authenticationUser(keyboard);
                    programFlow(user);
                    break;
                case 2:
                    user = service.authenticationUser(keyboard);
                    programFlow(user);
                    break;
                case 3:
                    System.out.println("Saliendo del programa...");
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida, intente nuevamente.");
                    break;
            }
        } while (!exit);
    }


    public void programFlow(User user){
        Scanner keyboard = new Scanner(System.in);
        Integer optionUser;
        boolean exit = false;
        if(user != null){
            while (!exit){
                System.out.println(MenuApp.menuApp);
                optionUser = keyboard.nextInt();
                keyboard.nextLine();
                switch (optionUser){
                    case 1:
                        System.out.println(MenuApp.subMenuPersonalAccount);
                        optionUser = keyboard.nextInt();
                        keyboard.nextLine();
                        switch (optionUser){
                            case 1:
                                System.out.println(user);
                                break;
                            case 2:
                                service.updateUserService(keyboard);
                                break;
                            case 3:
                                ArrayUsers.getActionsFromOneAccount(user);
                                break;
                            case 4:
                                System.out.println("Regresando al menú principal...");
                                break;
                            default:
                                System.out.println("Haz seleccionado una opción no válida.");
                                break;
                        }
                    case 2:
                        if(user.getRole().equals(UserRole.ADMIN)){
                            System.out.println(MenuApp.subMenuUserManagementByAdmin);
                            boolean exitSubMenu = false;
                            while (!exitSubMenu){
                                optionUser = keyboard.nextInt();
                                keyboard.nextLine();
                                switch (optionUser){
                                    case 1:
                                        ArrayUsers.getAllUsers();
                                        user.updateActionsUser(user, String.format("%s ha obtenido el historial de todos los usuarios.", user.getUsername()));
                                        break;
                                    case 2:
                                        var userConsult = getUserInFlowProgram(keyboard);
                                        if(userConsult.getRole().equals(UserRole.ADMIN) || userConsult.getRole().equals(UserRole.GUEST)){
                                            System.out.println(userConsult);
                                            user.updateActionsUser(user, String.format("%s ha obtenido la información completa del usuario %s", user.getUsername(), userConsult.getUsername()));
                                        } else{
                                            System.out.println(userConsult.userGuest());
                                            user.updateActionsUser(user, String.format("%s ha obtenido la información completa del usuario %s", user.getUsername(), userConsult.getUsername()));
                                        }
                                        break;
                                    case 3:
                                        userConsult = getUserInFlowProgram(keyboard);
                                        ArrayUsers.getInfoNameAndHistoryUser(userConsult);
                                        user.updateActionsUser(user, String.format("%s ha obtenido el nombre y el historial del usuario %s", user.getUsername(), userConsult.getUsername()));
                                        break;
                                    case 4:
                                        service.updateUserService(keyboard);
                                        break;
                                    case 5:
                                        service.deleteUser(user, keyboard);
                                        break;
                                    case 6:
                                        ArrayUsers.getAllHistoryByUsersArray();
                                        user.updateActionsUser(user, String.format("%s ha obtenido el historial de todos los usuarios registrados.", user.getUsername()));
                                        break;
                                    case 7:
                                        ArrayUsers.getAllHistoryByGuestUsers();
                                        user.updateActionsUser(user, String.format("%s ha obtenido el historial de todos los usuarios invitados.", user.getUsername()));
                                        break;
                                    case 8:
                                        userConsult = getUserInFlowProgram(keyboard);
                                        userConsult.lockUser();
                                        user.updateActionsUser(user, String.format("%s ha bloqueado a el usuario: | %s |", user.getUsername(), userConsult.getUsername()));
                                        break;
                                    case 9:
                                        userConsult = getUserInFlowProgram(keyboard);
                                        userConsult.assignedToUserRoleAdmin();
                                        user.updateActionsUser(user, String.format("%s le ha asignado rol admin a el usuario | %s |", user.getUsername(), userConsult.getUsername()));
                                        break;
                                    case 10:
                                        ArrayUsers.getAllLockedUsers();
                                        user.updateActionsUser(user, String.format("%s ha consultado todos los usuarios bloqueados.", user.getUsername()));
                                        break;
                                    case 11:
                                        System.out.println("Regresando al menú principal...");
                                        exitSubMenu = true;
                                        break;
                                    default:
                                        System.out.println("Ha seleccionado una opción no válida.");
                                        break;
                                }
                            }
                        }else {
                            boolean exitSubmenu = false;
                            while (!exitSubmenu) {
                                System.out.println(MenuApp.subMenuUserManagementByOthersUsers);
                                optionUser = keyboard.nextInt();
                                switch (optionUser){
                                    case 1:
                                        ArrayUsers.getListNamesOfAllUsers();
                                        break;
                                    case 2:
                                        System.out.println("Regresando al menú principal...");
                                        exitSubmenu = true;
                                        break;
                                    default:
                                        System.out.println("Opción no válida.");
                                }
                            }
                        }
                    case 3:
                        System.out.println("Cerrando sesión...");
                        user.updateActionsUser(user, String.format("%s ha cerrado sesión.", user.getUsername()));
                        exit = true;
                        break;
                    default:
                        System.out.println("Opción no válida.");
                        break;
                }
            }

        }
    }

    public User getUserInFlowProgram(Scanner keyboard) {
        System.out.println(MenuApp.optionSearchUserSelected);
        var optionUser = keyboard.nextInt();
        keyboard.nextLine();
        switch (optionUser) {
            case 1:
                System.out.println("Digite el nombre del usuario: ");
                String username = keyboard.nextLine();
                var user = ArrayUsers.getUserByUsername(username);
                System.out.println(user);
            case 2:
                System.out.println("Digite el id del usuario: ");
                Integer idUser = keyboard.nextInt();
                keyboard.nextLine();
                return ArrayUsers.getUserById(idUser);
            case 3:
                var userDecision = optionSearchUserByRoleInFlowProgram(keyboard);
                var userRole = switch (userDecision) {
                    case 1 -> UserRole.ADMIN;
                    case 2 -> UserRole.STANDARD;
                    case 3 -> UserRole.GUEST;
                    default -> UserRole.NONE;
                };
                if (!userRole.equals(UserRole.NONE)) {
                    return ArrayUsers.getUserByRole(userRole);
                } else {
                    System.out.println("Ha indicado un rol que no existe.");
                    return null;
                }
            case 4:
                System.out.println("Regresando al submenu.");
                return null;
            default:
                System.out.println("Opción no válida");
                return null;
        }
    }

    public Integer optionSearchUserByRoleInFlowProgram(Scanner keyboard){
        System.out.println("Digite el rol del usuario (El sistema traerá el primer usuario con ese rol): ");
        System.out.println(MenuApp.optionSearchUserByRol);
        Integer userDecision = keyboard.nextInt();
        keyboard.nextLine();
        return userDecision;

    }
}

package view;

import model.ActionUser;
import model.User;
import model.dto.UserCompletedDTO;
import model.dto.UserGenericDTO;
import model.enums.RoleUser;
import model.enums.StateUser;
import service.UserAdminService;
import service.UserGenericService;

import java.util.Arrays;
import java.util.Scanner;

public class MenuApp {

    UserAdminService serviceAdmin = new UserAdminService();
    UserGenericService serviceGeneric = new UserGenericService();

    public MenuApp() {}

    private String mainMenu = """
            ¡Bienvenido a User Manager system!
            1) Deseo registrarme, no tengo cuenta.
            2) Deseo iniciar sesión.
            3) Deseo ingresar como invitado.
            4) Salir.
            """;
    private String menuAdmin = """
            Estás en el panel ADMIN
            ¿Qué deseas hacer?
            1) Revisar mi información.
            2) Revisar información de otros usuarios.
            3) Salir.
            """;

    private String subMenuGuest= """
            ¡Bienvenido West!
            1) Ver mis acciones realizadas
            2) Ver información básica de usuarios registrados
            3) Salir
            """;

    private String subMenuAdminAndStandard = """
            Panel personal
            1) Ver mis datos
            2) Actualizar mi nombre completo
            3) Actualizar mi contraseña
            4) Ver mis acciones realizadas
            5) Ver información básica de usuarios registrados
            6) Salir
            """;
    private String subMenuPanelAdmin = """
            Panel Administrador de usuarios
            1) Crear un usuario
            2) Obtener información de un usuario por su Username
            3) Ver todos los usuarios  (Activos) (Inf completa)
            4) Ver a todos los usuarios (Activos) (Inf resumida)
            5) Ver usuarios bloqueados
            6) Ver historial de acciones de un usuario
            7) Ver historial de acciones de todos los usuarios
            8) Eliminar a un usuario
            9) Actualizar a un usuario
            10) Asignar rol admin a un usuario standard
            11) Salir.
            """;

    public void menuApp(Scanner keyboard){
        boolean exit = false;
        System.out.println("¡Bienvenido a User Manager System!");
        System.out.println("Para iniciar con tú exploración es necesario crear el primer usuario ADMIN.");
        serviceGeneric.createFirstUser(keyboard);
        
        while (!exit) {
            System.out.println(mainMenu);
            Integer optionUser = keyboard.nextInt();
            keyboard.nextLine();
            
            if (optionUser == 4) {
                exit = true;
                continue;
            }
            Integer subMenuOption = 0;
            Integer subMenuAdminOption = 0;
            boolean exitSubMenu = false;
            switch (optionUser) {
                case 1:
                    serviceGeneric.createStandardUser(keyboard);
                    break;
                case 2:
                    System.out.println(Arrays.toString(User.users));
                    var user = serviceGeneric.login(keyboard);
                    if(user != null && user.getState() == StateUser.ACTIVE){
                        if(user.getUserRole() != RoleUser.ADMIN){
                            while (!exitSubMenu) {
                                System.out.println("\n" + subMenuAdminAndStandard);
                                subMenuOption = keyboard.nextInt();
                                keyboard.nextLine();  // Limpiar el buffer
                                
                                switch (subMenuOption) {
                                    case 1:
                                        user = serviceGeneric.getMeUserData(user);
                                        if(user != null){
                                            System.out.printf("\nInformación del usuario %s: ", user.getUserName());
                                            System.out.println(user);
                                        } else{
                                            System.out.println("Error. Usuario no encontrado.");
                                        }
                                        break;
                                    case 2:
                                        if(serviceGeneric.updateMeUser(keyboard, user)){
                                            System.out.println("\nUsuario modificado correctamente.");
                                            System.out.println(user);
                                        }
                                        break;
                                    case 3:
                                        if(serviceGeneric.updateMePassword(keyboard, user)){
                                            System.out.println("Contraseña cambiada con éxito.");
                                            break;
                                        } else {
                                            System.out.println("Error cambiando contraseña.");
                                            break;
                                        }
                                    case 4:
                                        var userActions = serviceGeneric.getMeActions(user);
                                        System.out.println(userActions);
                                        break;
                                    case 5:
                                        var usersInfo = serviceGeneric.getAllUsers(user);
                                        System.out.println(Arrays.toString(usersInfo));
                                        break;
                                    case 6:
                                        System.out.println("Regresando al menú principal.");
                                        exitSubMenu = true;
                                        break;
                                    default:
                                        System.out.println("Opción no válida.");
                                        break;
                                }
                            }
                        } else {
                            System.out.println(subMenuPanelAdmin);
                            subMenuAdminOption = keyboard.nextInt();
                            boolean exitSubMenuAdmin = false;
                            while (!exitSubMenuAdmin){
                                switch (subMenuAdminOption) {
                                    case 1:
                                        var userCreated = serviceAdmin.createUserFromPanelAdmin(keyboard, user);
                                        System.out.printf("Usuario creado con éxito Username: %s.", userCreated.getUserName());
                                        break;
                                    case 2:
                                        var userInfo = serviceGeneric.getUserByUsername(keyboard);
                                        String nameUserCompleted = userInfo.getFirst_name() + userInfo.getLast_name();
                                        System.out.println(new UserCompletedDTO(userInfo.getId(), nameUserCompleted, userInfo.getUserName(),
                                                           userInfo.getUserRole(), userInfo.getState()));
                                        user.insertAction(new ActionUser(String.format("Se consultó a el usuario %s.", userInfo.getUserName())));
                                        break;
                                    case 3:
                                        var usersInfoCompeted = serviceAdmin.getAllUsersActive(user);
                                        System.out.println(Arrays.toString(usersInfoCompeted));
                                        break;
                                    case 4:
                                        var usersInfoResume = serviceGeneric.getAllUsers(user);
                                        System.out.println(Arrays.toString(usersInfoResume));
                                        break;
                                    case 5:
                                        var usersLocked = serviceAdmin.getAllUsersLocked(user);
                                        System.out.println(Arrays.toString(usersLocked));
                                        break;
                                    case 6:
                                        var userActions = serviceAdmin.getUserActions(keyboard, user);
                                        System.out.println(userActions);
                                        break;
                                    case 7:
                                        var usersAllActions = serviceAdmin.getAllUsersActions(user);
                                        System.out.println(Arrays.toString(usersAllActions));
                                        break;
                                    case 8:
                                        System.out.println("Panel de eliminación de usuario.");
                                        System.out.println("Digite el username del usuario: ");
                                        String username = keyboard.nextLine();
                                        if(serviceAdmin.deleteUser(username, user)){
                                            System.out.println("Usuario eliminado con éxito.");
                                        } else {
                                            System.out.println("Error eliminando a el usuario. (No encontrado)");
                                        }
                                        break;
                                    case 9:
                                        var userUpdate = serviceAdmin.updateUser(keyboard, user);
                                        if(userUpdate != null){
                                            System.out.println("Usuario actualizado con éxito.");
                                            String nameCompleted = userUpdate.getFirst_name() + userUpdate.getLast_name();
                                            System.out.println(new UserGenericDTO(userUpdate.getId(), nameCompleted, userUpdate.getUserRole()));
                                        } else {
                                            System.out.println("Error actualizando usuario (No existe el username).");
                                        }
                                        break;
                                    case 10:
                                        var userStandard = serviceGeneric.getUserByUsername(keyboard);
                                        if(userStandard != null){
                                            userStandard.asignedToAdminRole(user);
                                            String nameCompleted = userStandard.getFirst_name() + userStandard.getLast_name();
                                            System.out.println(new UserGenericDTO(userStandard.getId(), nameCompleted, userStandard.getUserRole()));
                                        } else {
                                            System.out.println("Usuario no existe. Error asignando rol admin.");
                                        }
                                        break;
                                    case 11:
                                        System.out.println("Saliendo del programa...");
                                        exitSubMenuAdmin = true;
                                        break;
                                    default:
                                        System.out.println("Opción no válida.");
                                        break;
                                }
                            }
                        }
                    }else{
                        System.out.println("Usuario que intenta ingresar no existe.");
                    }
                    break;
                case 3:
                    var userGuest = serviceGeneric.createUserGuest(keyboard);
                    System.out.println(subMenuGuest);
                    Integer subMenuGuestOption = keyboard.nextInt();
                    boolean exitSubMenuGuest = false;
                    while (!exitSubMenuGuest){
                        switch (subMenuGuestOption) {
                            case 1:
                                var userActions = serviceGeneric.getMeActions(userGuest);
                                System.out.println(userActions);
                                break;
                            case 2:
                                var usersInfo = serviceGeneric.getAllUsers(userGuest);
                                System.out.println(Arrays.toString(usersInfo));
                                break;
                            case 3:
                                System.out.println("Regresando al menú principal.");
                                exitSubMenuGuest = true;
                                break;
                            default:
                                System.out.println("Opción no válida.");
                                break;
                        }
                    }
                    break;
                case 4:
                    System.out.println("Saliendo del programa...");
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }

    }

}

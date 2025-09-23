package view;

import model.ActionUser;
import model.User;
import model.enums.RoleUser;
import service.UserAdminService;
import service.UserGenericService;

import java.util.Arrays;
import java.util.Scanner;

public class MenuApp {

    private UserAdminService serviceAdmin;
    private UserGenericService serviceGeneric;

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
            3) Ver todos los usuarios (Inf completa)
            4) Ver a todos los usuarios (Inf resumida)
            5) Ver historial de acciones de un usuario
            6) Ver historial de acciones de todos los usuarios
            7) Eliminar a un usuario
            8) Actualizar a un usuario
            9) Asignar rol admin a un usuario standard
            10) Ver usuarios bloqueados
            11) Ver usuarios activos
            12) Crear un usuario
            13) Salir.
            """;

    public void menuApp(Scanner keyboard){
        boolean exit = false;
        System.out.println("¡Bienvenido a User Manager System!");
        System.out.println("Para iniciar con tú exploración es necesario crear el primer usuario ADMIN.");
        serviceGeneric.createFirstUser(keyboard);
        System.out.println(mainMenu);
        Integer optionUser = keyboard.nextInt();
        while (optionUser != 4){
            Integer subMenuOption = 0;
            Integer subMenuAdminOption = 0;
            switch (optionUser) {
                case 1:
                    serviceGeneric.createStandardUser(keyboard);
                    break;
                case 2:
                    var user = serviceGeneric.login(keyboard);
                    if(user != null){
                        if(user.getUserRole() != RoleUser.ADMIN){
                            System.out.println(subMenuAdminAndStandard);
                            subMenuOption = keyboard.nextInt();
                            while (subMenuOption != 6){
                                switch (subMenuOption) {
                                    case 1:
                                        user = serviceGeneric.getMeUserData(user);
                                        if(user != null){
                                            System.out.printf("Información del usuario %s: ", user.getUserName());
                                            System.out.println(user);
                                        } else{
                                            System.out.println("Error. Usuario no encontrado.");
                                        }
                                        break;
                                    case 2:
                                        if(serviceGeneric.updateMeUser(keyboard, user)){
                                            System.out.println("Usuario modificado correctamente.");
                                            System.out.println(user);
                                        }
                                        break;
                                    case 3:
                                        if(serviceGeneric.updateMePassword(keyboard, user)){
                                            System.out.println("Contraseña cambiada con éxito.");
                                        } else {
                                            System.out.println("Error cambiando contraseña.");
                                        }
                                        break;
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
                                        break;
                                    default:
                                        System.out.println("Opción no válida.");
                                        break;
                                }
                            }
                        } else {
                            System.out.println(subMenuPanelAdmin);
                            subMenuAdminOption = keyboard.nextInt();
                            while (subMenuAdminOption != 13){

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
                    while (subMenuGuestOption != 3){
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
                                break;
                            default:
                                System.out.println("Opción no válida.");
                                break;
                        }
                    }
                    break;
                case 4:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }

    }

}

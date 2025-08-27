package view;

import model.entities.User;
import model.enums.UserRole;
import service.UserService;

import java.util.Scanner;

public class ProgramViews {

    private UserService service = new UserService();

    private String menuMain= """
            ¡Bienvenido al sistema de usuarios!
            Selecciona una de las siguientes opciones:
            1) No tengo cuenta, quiero crear una
            2) Ya tengo cuenta, deseo iniciar sesión :)
            3) Salir.
            """;


    public void programFlow(Scanner keyboard){
        System.out.println(menuMain);
        boolean exit = false;
        Integer decisionUser = keyboard.nextInt();
        do {
            switch (decisionUser) {
                case 1:
                    service.createUserService(keyboard);
                    var user = service.authenticationUser(keyboard);

                    break;
                case 2:
                    service.authenticationUser(keyboard);
                    break;
                case 3:
                    System.out.println("Saliendo del programa...");
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida, intente nuevamente.");
                    break;
            }
        } while (exit);
    }


    public void programFlow(User user){
        if(user != null){
            var userRole = user.getRole();
            String menuAvailable = switch (userRole){
                case UserRole.ADMIN -> {

                }
            }
        }
    }


}

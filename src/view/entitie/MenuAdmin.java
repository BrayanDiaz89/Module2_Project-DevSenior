package view.entitie;

import model.entities.User;

public class MenuAdmin {
    public static String menuAdmin = """
            |* Panel de administrador *|
            1) Gestión de mi cuenta.
            2) Gestión de usuarios.
            3) Cerrar sesión
            """;
    public static String subMenuPersonalAccountAdmin = """
            1) Ver mis datos
            2) Editar mis datos
            3) Ver mi historial de actividad
            4) Regresar
            """;

    public static String subMenuUserManagementByAdmin = """
            1) Ver lista de usuarios
            2) Buscar usuario (Obtener Inf. Completa)
            3) Buscar usuario (Obtener nombre y historial)
            4) Editar datos de un usuario
            5) Eliminar a un usuario
            6) Ver historial de los usuarios
            7) Ver usuarios invitados con historial
            8) Desbloquear usuario
            9) Asignar rol ADMIN a un usuario
            10) Ver usuarios bloqueados
            11) Regresar
            """;

    public static String optionSearchUserSelected = """
            1) Buscar por username
            2) Buscar por ID
            3) Buscar por rol (Obtener el primero encontrado)
            """;

}

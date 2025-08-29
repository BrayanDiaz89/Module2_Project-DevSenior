package view.entitie;

public class MenuApp {

    public static String menuApp = """
            |* Panel de usuario *|
            1) Gestión de mi cuenta.
            2) Gestión de usuarios.
            3) Cerrar sesión
            """;
    public static String subMenuUserManagementByOthersUsers = """
            1) Ver lista de usuarios
            2) Regresar
            """;
    public static String subMenuPersonalAccount = """
            1) Ver mis datos
            2) Editar mis datos
            3) Ver mi historial de actividad
            4) Regresar
            """;

    public static String subMenuUserManagementByAdmin = """
            1) Ver lista de usuarios (Inf. Completa)
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
            4) Regresar
            """;

    public static String optionSearchUserByRol = """
            1) ADMIN
            2) STANDARD
            3) GUEST
            """;

}

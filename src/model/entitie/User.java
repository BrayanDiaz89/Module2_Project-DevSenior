package model.entitie;
import model.enums.UserRole;
import model.enums.UserState;

public class User {

    private String first_name;
    private String last_name;
    private static Integer id = 0;
    private String username;
    private String password;
    private UserRole role;
    private UserAction[] actions;
    private UserState state;

    public String getFirst_name() {
        return first_name;
    }
    public String getLast_name() {
        return last_name;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public UserRole getRole() {
        return role;
    }
    public UserAction[] getActions() {
        return actions;
    }
    public UserState getState() {
        return state;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(UserRole role) {
        this.role = role;
    }
    public void setActions(UserAction[] actions) {
        this.actions = actions;
    }
    public void setState(UserState state) {
        this.state = state;
    }

    public User(String first_name, String last_name, String username, String password, UserRole role) {
        this.first_name = first_name;
        this.last_name = last_name;
        id++;
        this.username = username;
        this.password = password;
        this.role = role;
        actions = new UserAction[100];
        actions[0] = new UserAction(String.format("Se creó el usuario %s", username));
        state = UserState.ACTIVE;
    }

    public User(String first_name, String last_name) {
        this.first_name = first_name;
        this.last_name = last_name;
        id++;
        username = "Guest"+id;
        role = UserRole.GUEST;
        state = UserState.ACTIVE;
    }

}

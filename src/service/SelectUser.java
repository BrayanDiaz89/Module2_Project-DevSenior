package service;

import model.entities.User;

import java.util.Scanner;

public interface SelectUser {
    User findUser(Scanner keyboard);
}

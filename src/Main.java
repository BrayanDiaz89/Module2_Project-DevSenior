import view.ProgramViews;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        ProgramViews viewProgram = new ProgramViews();
        viewProgram.initialProgramFlow(keyboard);
    }

}
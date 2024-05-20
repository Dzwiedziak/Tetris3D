import Controller.ControllerGraphics;
import Controller.ControllerTxt;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        while(true) {
            System.out.println("Jak chcesz grac? Wpisz: Graficznie lub Tekstowo");
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();
            if (userInput.equals("Graficznie")) {
                ControllerGraphics controllerGraphics = new ControllerGraphics();
            } else if (userInput.equals("Tekstowo")) {
                ControllerTxt controllerTxt = new ControllerTxt();
            }
            else{
                System.out.println("Wprowadzono niepoprawna wartosc. Stosuj sie do polecenia ponizej.");
            }
        }
    }
}
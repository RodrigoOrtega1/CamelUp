import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        CamelUp round = new CamelUp();
        int playerNum;
        while (true) {
            System.out.println("Inserte el numero de jugadores:");
            playerNum = scanner.nextInt();
            if (playerNum > 6 || playerNum < 4){
                System.out.println("Solo pueden jugar de 4-6 jugadores, inserta un numero valido");
            } else {
                break;
            }       
        }
        scanner.nextLine(); // para que scanner no se salte la siguiente llamada a este
        round.setUpRound(4, scanner); 
        round.game(scanner);
    }
}

import Extras.SimpleLinkedList;
import Extras.Stack;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;

/**
 * Clase que modela una ronda de CamelUp
 * @author Rodrigo Ortega 318036104
 * @version 1.0 Oct 6 2022
 * @since ED 2023-1
 */

public class Round {
    public SimpleLinkedList<Player> playerList = new SimpleLinkedList<>();
    public SimpleLinkedList<Camel> camelList = new SimpleLinkedList<>();
    public Tile[] board = new Tile[16];
    public SimpleLinkedList<Camel> alreadyMoved = new SimpleLinkedList<>();
    GameStack gameStack = new GameStack();
    boolean isTurnOver = false;
    boolean isRoundOver = false;
    boolean isGameOver = false;
    Stack<String> history = new Stack<>();

    public void placeCamel(Tile[] board){
        SimpleLinkedList<Camel> camelPos = new SimpleLinkedList<>();
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].camelStack.size(); j++){
                camelPos.add(camelPos.size(), board[i].camelStack.get(j));
            }
        }
        
        for(int i = 0; i < 5; i++){
            switch(i){
                case 0:
                    camelPos.get(i).setPlace(5);
                    break;
                case 1:
                    camelPos.get(i).setPlace(4);
                    break;
                case 2:
                    camelPos.get(i).setPlace(3);
                    break;
                case 3:
                    camelPos.get(i).setPlace(2);
                    break;
                case 4:
                    camelPos.get(i).setPlace(1);
                    break;
            }
        }

        for(int i = 0; i < camelPos.size(); i++){
            for(int j = 0; j < playerList.size(); j++){
                for(int k = 0; k < playerList.get(j).betCardList.size(); k++){
                    if(camelPos.get(i).identifier == playerList.get(j).betCardList.get(k).camelName){
                        playerList.get(j).betCardList.get(k).setPlace(camelPos.get(i).place);
                    }
                }    
            }
        }

        System.out.println("Posiciones temporales de la ronda (atras para adelante): " + camelPos);
    }

    /**
     * Revuelve la lista de camellos aleatoriamente
     * @param camelList
     */
    private void shuffle(SimpleLinkedList<Camel> camelList){
        
        Camel[] tempArr = new Camel[camelList.size()];
        Random random = new Random();

        for (int i = 0; i < camelList.size(); i++){
            tempArr[i] = camelList.get(i);
        }
        
        camelList.clear();

        for (int i = 0; i < tempArr.length; i++){
            int randIn = random.nextInt(tempArr.length);
            Camel temp = tempArr[randIn];
            tempArr[randIn] = tempArr[i];
            tempArr[i] = temp;
        }
        
        for (int i = 0; i < tempArr.length; i++){
            camelList.add(i, tempArr[i]);
        }
    }

    /**
     * Prepara el juego 
     * @param num numero de jugadores 
     */
    public void setUpRound(int num, Scanner scanner){
        
        if(num < 6 || num > 4){
            System.out.println("Solo pueden jugar 4-6 jugadores");
        }

        System.out.println("Preparando el juego...");

        //Inicializa el tablero
        for(int i = 0; i < board.length; i++){
            board[i] = new Tile();
        }

        //Inicializa a los jugadores
        for(int i = 0; i < num; i++){
            System.out.println("Inserta el nombre del jugador " + (i + 1));
            String name = scanner.nextLine();
            Player jugador = new Player(name, 3);
            jugador.fillCamelCardList();
            playerList.add(i, jugador);
        }

        //Inicializa a los camellos
        for(int i = 0; i < 5; i++){
            Camel camello;
            switch(i){
                case 0:
                    camello = new Camel("Red");
                    camello.fillCamelStack();
                    camelList.add(i, camello);
                    break;
                case 1:
                    camello = new Camel("Green");
                    camello.fillCamelStack();
                    camelList.add(i, camello);                        
                    break;
                case 2:
                    camello = new Camel("Yellow");
                    camello.fillCamelStack();
                    camelList.add(i, camello);
                    break;
                case 3:
                    camello = new Camel("Purple");
                    camello.fillCamelStack();
                    camelList.add(i, camello);
                    break;
                case 4:
                    camello = new Camel("Blue");
                    camello.fillCamelStack();
                    camelList.add(i, camello);
                    break;
            }
        }

        //Mueve a los camellos
        for(int i = 0; i < camelList.size(); i++){
            camelList.get(i).move(camelList.get(i), board);
        }
        
        //Revuelve el orden de los camellos
        shuffle(camelList);

        System.out.println("Listo");
    }

    public void turn(int currentPlayer, Scanner scanner){
        System.out.println("Turno de: " + playerList.get(currentPlayer).getName());
        System.out.println("Tablero: " + "\n" + Arrays.toString(board));
        System.out.println("-- Perfil:--"  + "\n" +
                           "Nombre: " + playerList.get(currentPlayer).getName() + "\n" +
                           "Dinero: " + playerList.get(currentPlayer).getMoney() + "\n" +
                           "Cartas de apuesta: " + playerList.get(currentPlayer).betCardList + "\n" + 
                           "Cartas de camello: " + playerList.get(currentPlayer).camelCardList + "\n" +
                           "--------------------"); 
        
        do{
            System.out.println("Acciones:\n" +
                           "[1]Apostar por el camello ganador de la ronda\n" + 
                           "[2]Apostar por el camello ganador de la carrera\n" +
                           "[3]Apostar por el camello perdedor de la carrera\n" +
                           "[4]Colocar modificador\n" +
                           "[5]Tirar un dado\n" +
                           "[0]Terminar el juego");
        
        System.out.println("Escribe la acción que quieras hacer:");
        int playerInput = scanner.nextInt();
        int actionInput;
        switch(playerInput){
            case 1:
                System.out.println("Elige un camello para apostar (0-4)");
                System.out.println(camelList);
                actionInput = scanner.nextInt();
                playerList.get(currentPlayer).betWinRound(camelList.get(actionInput));
                history.push("El jugador " + playerList.get(currentPlayer).getName() + " aposto que " + camelList.get(actionInput).identifier + " ganaria la ronda");
                isTurnOver = true;
                break;
            case 2:
                System.out.println("Elige una carta de camello para apostar (0-4)");
                System.out.println(playerList.get(currentPlayer).camelCardList);
                actionInput = scanner.nextInt();
                playerList.get(currentPlayer).bet(gameStack.winner, actionInput);
                history.push("El jugador " + playerList.get(currentPlayer).getName() + " aposto que " + camelList.get(actionInput).identifier + " ganara la partida");
                isTurnOver = true;
                break;
            case 3:
                System.out.println("Elige una carta de camello para apostar (0-4)");
                System.out.println(playerList.get(currentPlayer).camelCardList);
                actionInput = scanner.nextInt();
                playerList.get(currentPlayer).bet(gameStack.loser, actionInput);
                history.push("El jugador " + playerList.get(currentPlayer).getName() + " aposto que " + camelList.get(actionInput).identifier + " perdera la partida");
                isTurnOver = true;
                break;
            case 4:
                System.out.println("Elige true si quieres que el modificador avance y false si quieres que retroceda");
                boolean input = scanner.nextBoolean();
                System.out.println("Elige en que casilla lo quieras colocar (0-16)");
                actionInput = scanner.nextInt();
                playerList.get(currentPlayer).placeMod(board, input, actionInput);
                history.push("El jugador " + playerList.get(currentPlayer).getName() + " coloco un modificador");
                isTurnOver = true;
                break;
            case 5:
                Random random = new Random();
                int randomInt = random.nextInt(camelList.size());
                playerList.get(currentPlayer).rollDie(camelList, board, randomInt);
                alreadyMoved.add(alreadyMoved.size(), camelList.get(randomInt));
                camelList.remove(randomInt);
                System.out.println("Has movido a: " +  alreadyMoved.get(alreadyMoved.size() - 1).identifier);
                System.out.println("Se han movido los siguientes camellos esta ronda: " + alreadyMoved);
                history.push("El jugador " + playerList.get(currentPlayer).getName() + " movio a " + alreadyMoved.get(alreadyMoved.size() - 1).identifier);
                isTurnOver = true;
                break;
            case 0:
                System.out.println("Terminando juego");
                isTurnOver = true;
                break;
            default:
                System.out.println("Accion invalida");
        }
        } while(isTurnOver != true);
    }

    public void round(Scanner scanner){
        
        while(isRoundOver != true){
            int currentPlayer = 0;
            while (currentPlayer < playerList.size()){
                if(camelList.isEmpty()){
                    isRoundOver = true;
                    break;
                }
                turn(currentPlayer, scanner);
                currentPlayer++;
            }
        }

        System.out.println("\n---- Final de la ronda ----\n");

        placeCamel(board);

        for(int i = 0; i < playerList.size(); i++){
            playerList.get(i).grade();
            history.push("El jugador " + playerList.get(i).getName() + " tiene " + playerList.get(i).getMoney() + " monedas");
        }

        playerList.add(playerList.size(), playerList.get(0));
        playerList.remove(0);

        System.out.println("----Historial de ronda----");
        while(!history.isEmpty()){
            System.out.println(history.pop());
        }
    }

    //if(!board[board.length].camelStack.isEmpty()){
    //    isGameOver = true;
    //} To end the game

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Round round = new Round();
        round.setUpRound(4, scanner);
        round.round(scanner);
    }
}

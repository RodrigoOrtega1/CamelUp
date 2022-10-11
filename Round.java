import Extras.SimpleLinkedList;
import Extras.Stack;
import java.util.Scanner;
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
    public SimpleLinkedList<Camel> camelListAlwaysFull = new SimpleLinkedList<>();
    public Tile[] board = new Tile[16];
    public SimpleLinkedList<Camel> alreadyMoved = new SimpleLinkedList<>();
    public Stack<Player.CamelCard> winnerStack = new Stack<>();
    public Stack<Player.CamelCard> loserStack = new Stack<>();
    public Stack<String> history = new Stack<>();
    public boolean isTurnOver = false;
    public boolean isRoundOver = false;
    public boolean isGameOver = false;
    

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

        System.out.println("\n----Posiciones de la ronda:----");
        camelPos.revert();
        for(int i = 0; i < camelPos.size(); i++){
            switch(camelPos.get(i).place){
                case 1:
                    System.out.println(camelPos.get(i).identifier + " va en 1er lugar");
                    break;
                case 2:
                    System.out.println(camelPos.get(i).identifier + " va en 2do lugar");
                    break;
                case 3:
                    System.out.println(camelPos.get(i).identifier + " va en 3er lugar");
                    break;
                case 4:
                    System.out.println(camelPos.get(i).identifier + " va en 4to lugar");
                    break;
                case 5:  
                    System.out.println(camelPos.get(i).identifier + " va en 5to lugar");
                    break;
            }
        }
        //System.out.println("Posiciones temporales de la ronda (atras para adelante): " + camelPos);
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

    private void displayBoard(){
        System.out.println("Tablero:");
        for(int i = 0; i < board.length; i++){
            String tile =  "[" + board[i].camelStack + "]";
            if(board[i].hasModifier()){
                if(board[i].modifier.get(0).getValue() == true){
                    tile = "[M+]";
                } else if(board[i].modifier.get(0).getValue() == false) {
                    tile = "[M-]";
                } 
            }
            if(i == 0){
                tile += " <--- Inicio";
            } else if(i == board.length - 1) {
                tile += " <--- Meta";
            }
            System.out.println(tile);
        }
    }

    private void reverseHistory(Stack<String> history){
        SimpleLinkedList<String> temp = new SimpleLinkedList<>();
        while(!history.isEmpty()){
            temp.add(temp.size(), history.pop());
        }
        temp.revert();
        for(int i = 0; i < temp.size(); i++){
            System.out.println((i + 1) + ".- " + temp.get(i));
        }
    }

    /**
     * Prepara el juego 
     * @param num numero de jugadores
     * @param scanner scanner para recibir datos de los jugadores
     */
    public void setUpRound(int num, Scanner scanner){

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
            jugador.fillModifier();
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
            camelList.get(i).move(camelList.get(i), board, history);
        }
        
        //Revuelve el orden de los camellos
        shuffle(camelList);

        for(int i = 0; i < camelList.size(); i++){
            camelListAlwaysFull.add(i, camelList.get(i));
        }

        System.out.println("Listo");
    }

    public void turn(int currentPlayer, Scanner scanner){
        
        System.out.println("Turno de: " + playerList.get(currentPlayer).getName());
        displayBoard();
        System.out.println("----Perfil:----"  + "\n" +
                           "Nombre: " + playerList.get(currentPlayer).getName() + "\n" +
                           "Dinero: " + playerList.get(currentPlayer).getMoney() + "\n" +
                           "Cartas de apuesta: " + playerList.get(currentPlayer).betCardList + "\n" + 
                           "Cartas de camello: " + playerList.get(currentPlayer).camelCardList + "\n" +
                           "---------------"); 
        
        do{
            System.out.println("Acciones:\n" +
                           "[1]Apostar por el camello ganador de la ronda\n" + 
                           "[2]Apostar por el camello ganador de la carrera\n" +
                           "[3]Apostar por el camello perdedor de la carrera\n" +
                           "[4]Colocar modificador\n" +
                           "[5]Tirar un dado\n");
        System.out.println("Escribe la acción que quieras hacer:");
        int playerInput = scanner.nextInt();
        int actionInput;
        switch(playerInput){
            case 1:
                while (true) {
                System.out.println("Elige un camello para apostar (1-5):");
                System.out.println(camelListAlwaysFull);
                actionInput = scanner.nextInt() - 1;
                if (actionInput > 4 || actionInput < 0){
                    System.out.println("Numero invalido, intenta de nuevo");
                } else {
                    break;
                }       
                }
                playerList.get(currentPlayer).betWinRound(camelList.get(actionInput));
                System.out.println("Has apostado por " + camelList.get(actionInput).identifier + " para que gane la ronda");
                history.push("El jugador " + playerList.get(currentPlayer).getName() + " aposto que " + camelList.get(actionInput).identifier + " ganaria la ronda");
                isTurnOver = true;
                break;

            case 2:
                if(!playerList.get(currentPlayer).camelCardList.isEmpty()){
                    System.out.println("Elige una carta de camello para apostar (0-4)");
                    System.out.println(playerList.get(currentPlayer).camelCardList);
                    actionInput = scanner.nextInt();
                    playerList.get(currentPlayer).bet(winnerStack, actionInput);
                    history.push("El jugador " + playerList.get(currentPlayer).getName() + " aposto que " + camelList.get(actionInput).identifier + " ganara la partida");
                    isTurnOver = true;
                    break;
                } else {
                    System.out.println("El jugador ya coloco todas sus cartas de camello");
                    break;
                }
                

            case 3:
                if(!playerList.get(currentPlayer).camelCardList.isEmpty()){
                    System.out.println("Elige una carta de camello para apostar (0-4)");
                    System.out.println(playerList.get(currentPlayer).camelCardList);
                    actionInput = scanner.nextInt();
                    playerList.get(currentPlayer).bet(loserStack, actionInput);
                    history.push("El jugador " + playerList.get(currentPlayer).getName() + " aposto que " + camelList.get(actionInput).identifier + " perdera la partida");
                    isTurnOver = true;
                    break;
                } else {
                    System.out.println("El jugador ya coloco todas sus cartas de camello");
                    break;
                }

            case 4:
                if(!playerList.get(currentPlayer).modifierL.isEmpty()){
                    System.out.println("Elige true si quieres que el modificador avance y false si quieres que retroceda");
                    boolean input = scanner.nextBoolean();
                    System.out.println("Elige en que casilla lo quieras colocar (0-15)");
                    actionInput = scanner.nextInt();
                    playerList.get(currentPlayer).placeMod(board, input, actionInput);
                    history.push("El jugador " + playerList.get(currentPlayer).getName() + " coloco un modificador");
                    isTurnOver = true;
                    break;
                } else {
                    System.out.println("El jugador ya coloco su modificador esta ronda");
                    break;
                }
                
            case 5:
                Random random = new Random();
                int randomInt = random.nextInt(camelList.size());
                playerList.get(currentPlayer).rollDie(camelList, board, randomInt, history);
                alreadyMoved.add(alreadyMoved.size(), camelList.get(randomInt));
                camelList.remove(randomInt);
                System.out.println("Has movido a: " +  alreadyMoved.get(alreadyMoved.size() - 1).identifier);
                System.out.println("Se han movido los siguientes camellos esta ronda: " + alreadyMoved);
                history.push("El jugador " + playerList.get(currentPlayer).getName() + " movio a " + alreadyMoved.get(alreadyMoved.size() - 1).identifier);
                isTurnOver = true;
                break;
                
            default:
                System.out.println("Accion invalida");
        }
        } while(isTurnOver != true);
    }

    public void round(Scanner scanner){
        // while(isRoundOver != true){
        //     int currentPlayer = 0;
        //     while (currentPlayer < playerList.size()){
        //         if(camelList.isEmpty()){
        //             isRoundOver = true;
        //             break;
        //         }
        //         turn(currentPlayer, scanner);
        //         currentPlayer++;
        //     }
        // }
        while (isGameOver == false){
            int currentPlayer = 0;
            while (currentPlayer < playerList.size()){
                if(!board[board.length - 1].camelStack.isEmpty()){
                    isGameOver = true;
                    break;
                }
                if(camelList.isEmpty()){
                    System.out.println("\n----Final de la ronda----");
        
                    displayBoard();
                    placeCamel(board);
        
                    System.out.println("\n----Dinero de la ronda----");
                    for(int i = 0; i < playerList.size(); i++){
                        playerList.get(i).grade();
                        System.out.println((i + 1) + ".-" + "El jugador " + playerList.get(i).getName() + " tiene " + playerList.get(i).getMoney() + " monedas");
                    }

                    System.out.println("\n----Historial de ronda----");
                    reverseHistory(history);

                    for(int i = 0; i < board.length; i++){
                        board[i].modifier.clear();
                    }
        
                    for(int i = 0; i < playerList.size(); i++){
                        playerList.get(i).fillModifier();
                    }

                    for(int i = 0; i < alreadyMoved.size(); i++){
                        camelList.add(i, alreadyMoved.get(i));
                    }
                    alreadyMoved.clear();
        
                    for(int i = 0; i < camelList.size(); i++){
                        camelList.get(i).fillCamelStack();
                    }
                    
                    playerList.add(playerList.size(), playerList.get(0));
                    playerList.remove(0);
                    break;
                }
                turn(currentPlayer, scanner);
                currentPlayer++;
            }
            displayBoard();
        }
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Round round = new Round();
        // int input;
        // while (true) {
        //     System.out.println("Inserte el numero de jugadores:");
        //     input = scanner.nextInt();
        //     if (input > 6 || input < 4){
        //         System.out.println("Solo pueden jugar de 4-6 jugadores, inserta un numero valido");
        //     } else {
        //         break;
        //     }       
        // }
        // scanner.nextLine(); // para que scanner no se salte la siguiente llamada a este
        round.setUpRound(4, scanner); 
        round.round(scanner);
    }
}

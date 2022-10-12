import Extras.*;
import java.util.Scanner;
import java.util.Random;

/**
 * Clase que modela una ronda de CamelUp
 * @author Rodrigo Ortega 318036104
 * @version 1.0 Oct 6 2022
 * @since ED 2023-1
 */

public class CamelUp {
    private SimpleLinkedList<Player> players = new SimpleLinkedList<>();
    private SimpleLinkedList<Camel> camels = new SimpleLinkedList<>();
    private SimpleLinkedList<Camel> alreadyMovedCamels = new SimpleLinkedList<>();
    private SimpleLinkedList<Camel> camelsAlwaysFull = new SimpleLinkedList<>();
    private Stack<Player.CamelCard> winnerStack = new Stack<>();
    private Stack<Player.CamelCard> loserStack = new Stack<>();
    private Stack<String> history = new Stack<>();
    private Tile[] board = new Tile[16];
    private boolean isTurnOver = false;
    private boolean isGameOver = false;
    
    private void placeCamel(Tile[] board){
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
            for(int j = 0; j < players.size(); j++){
                for(int k = 0; k < players.get(j).betCardList.size(); k++){
                    if(camelPos.get(i).identifier == players.get(j).betCardList.get(k).camelName){
                        players.get(j).betCardList.get(k).setPlace(camelPos.get(i).place);
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
     * @param camels
     */
    private void shuffle(SimpleLinkedList<Camel> camels){
        
        Camel[] tempArr = new Camel[camels.size()];
        Random random = new Random();

        for (int i = 0; i < camels.size(); i++){
            tempArr[i] = camels.get(i);
        }
        
        camels.clear();

        for (int i = 0; i < tempArr.length; i++){
            int randIn = random.nextInt(tempArr.length);
            Camel temp = tempArr[randIn];
            tempArr[randIn] = tempArr[i];
            tempArr[i] = temp;
        }
        
        for (int i = 0; i < tempArr.length; i++){
            camels.add(i, tempArr[i]);
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
    private void setUpRound(int num, Scanner scanner){

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
            players.add(i, jugador);
        }

        //Inicializa a los camellos
        for(int i = 0; i < 5; i++){
            Camel camello;
            switch(i){
                case 0:
                    camello = new Camel("Red");
                    camello.fillCamelStack();
                    camels.add(i, camello);
                    break;
                case 1:
                    camello = new Camel("Green");
                    camello.fillCamelStack();
                    camels.add(i, camello);                     
                    break;
                case 2:
                    camello = new Camel("Yellow");
                    camello.fillCamelStack();
                    camels.add(i, camello);
                    break;
                case 3:
                    camello = new Camel("Purple");
                    camello.fillCamelStack();
                    camels.add(i, camello);
                    break;
                case 4:
                    camello = new Camel("Blue");
                    camello.fillCamelStack();
                    camels.add(i, camello);
                    break;
            }
        }

        //Mueve a los camellos
        for(int i = 0; i < camels.size(); i++){
            camels.get(i).move(camels.get(i), board, history);
        }
        
        //Revuelve el orden de los camellos
        shuffle(camels);

        for(int i = 0; i < camels.size(); i++){
            camelsAlwaysFull.add(i, camels.get(i));
        }

        System.out.println("Listo");
    }

    private void turn(int currentPlayer, Scanner scanner){
        
        System.out.println("Turno de: " + players.get(currentPlayer).getName());
        displayBoard();
        System.out.println("----Perfil:----"  + "\n" +
                           "Nombre: " + players.get(currentPlayer).getName() + "\n" +
                           "Dinero: " + players.get(currentPlayer).getMoney() + "\n" +
                           "Cartas de apuesta: " + players.get(currentPlayer).betCardList + "\n" + 
                           "Cartas de camello: " + players.get(currentPlayer).camelCardList + "\n" +
                           "---------------"); 
        
        do{
            System.out.println("Acciones:\n" +
                           "[1]Apostar por el camello ganador de la ronda\n" + 
                           "[2]Apostar por el camello ganador de la carrera\n" +
                           "[3]Apostar por el camello perdedor de la carrera\n" +
                           "[4]Colocar modificador\n" +
                           "[5]Mover a un camello aleatorio");
        System.out.println("Escribe la acción que quieras hacer:");
        int playerInput = scanner.nextInt();
        int actionInput;
        switch(playerInput){
            case 1:
                while (true) {
                    System.out.println("Elige un camello para apostar (1-5):");
                    System.out.println(camelsAlwaysFull);
                    actionInput = scanner.nextInt() - 1;
                    if (actionInput > 4 || actionInput < 0){
                        System.out.println("Numero invalido, intenta de nuevo");
                    } else {
                        break;
                    }       
                }
                players.get(currentPlayer).betWinRound(camels.get(actionInput));
                System.out.println("Has apostado por " + camels.get(actionInput).identifier + " para que gane la ronda");
                history.push("El jugador " + players.get(currentPlayer).getName() + " aposto que " + camels.get(actionInput).identifier + " ganaria la ronda");
                isTurnOver = true;
                break;

            case 2:
                if(!players.get(currentPlayer).camelCardList.isEmpty()){
                    while (true) {
                        System.out.println("Elige una carta de camello para apostar (1-5)");
                        System.out.println(players.get(currentPlayer).camelCardList);
                        actionInput = scanner.nextInt() - 1;
                        if (actionInput > 4 || actionInput < 0){
                            System.out.println("Numero invalido, intenta de nuevo");
                        } else {
                            break;
                        }       
                    }
                    players.get(currentPlayer).bet(winnerStack, actionInput);
                    history.push("El jugador " + players.get(currentPlayer).getName() + " aposto que " + camels.get(actionInput).identifier + " ganara la partida");
                    isTurnOver = true;
                    break;
                } else {
                    System.out.println("El jugador ya coloco todas sus cartas de camello");
                    break;
                }
                

            case 3:
                if(!players.get(currentPlayer).camelCardList.isEmpty()){
                    while (true) {
                        System.out.println("Elige una carta de camello para apostar (1-5)");
                        System.out.println(players.get(currentPlayer).camelCardList);
                        actionInput = scanner.nextInt() - 1;
                        if (actionInput > 4 || actionInput < 0){
                            System.out.println("Numero invalido, intenta de nuevo");
                        } else {
                            break;
                        }       
                    }
                    players.get(currentPlayer).bet(loserStack, actionInput);
                    history.push("El jugador " + players.get(currentPlayer).getName() + " aposto que " + camels.get(actionInput).identifier + " perdera la partida");
                    isTurnOver = true;
                    break;
                } else {
                    System.out.println("El jugador ya coloco todas sus cartas de camello");
                    break;
                }

            case 4:
                if(!players.get(currentPlayer).modifierL.isEmpty()){
                    while (true) {
                        System.out.println("Presiona una de las siguientes teclas\n" +
                                            "[1] Si quieres que el modificador avance a los camellos una casilla\n" +
                                            "[2] Si quieres que el modificador retrace a los camellos una casilla");
                        actionInput = scanner.nextInt();
                        if (actionInput > 2 || actionInput < 1){
                            System.out.println("Numero invalido, intenta de nuevo");
                        } else {
                            break;
                        }       
                    }
                    boolean modifierDirection = false;
                    switch(actionInput){
                        case 1:
                            modifierDirection = true; 
                            break;
                        case 2:
                            modifierDirection = false;
                            break;
                        default:
                            System.out.println("Valor invalido, intenta de nuevo");
                    }
                    while (true) {
                        System.out.println("Elige en que casilla lo quieres colocar (0-15)");
                        actionInput = scanner.nextInt();
                        if (actionInput > 15 || actionInput < 0){
                            System.out.println("Numero invalido, intenta de nuevo");
                        } else if (board[actionInput].hasModifier() || board[actionInput - 1].hasModifier() || board[actionInput + 1].hasModifier() || !board[actionInput].camelStack.isEmpty()){
                            System.out.println("Casilla invalido, intenta de nuevo");
                        } else {
                            break;
                        }       
                    }
                    players.get(currentPlayer).placeMod(board, modifierDirection, actionInput);
                    String historyModifier = "El jugador " + players.get(currentPlayer).getName() + " coloco un modificador";
                    if(modifierDirection == false){
                        historyModifier += " hacia atras";
                    } else if(modifierDirection == true){
                        historyModifier += " hacia adelante";
                    }
                    history.push(historyModifier);
                    isTurnOver = true;
                    break;
                } else {
                    System.out.println("El jugador ya coloco su modificador esta ronda");
                    break;
                }
                
            case 5:
                Random random = new Random();
                int randomInt = random.nextInt(camels.size());
                players.get(currentPlayer).rollDie(camels, board, randomInt, history);
                alreadyMovedCamels.add(alreadyMovedCamels.size(), camels.get(randomInt));
                camels.remove(randomInt);
                System.out.println("Has movido a: " +  alreadyMovedCamels.get(alreadyMovedCamels.size() - 1).identifier);
                System.out.println("Se han movido los siguientes camellos esta ronda: " + alreadyMovedCamels);
                history.push("El jugador " + players.get(currentPlayer).getName() + " movio a " + alreadyMovedCamels.get(alreadyMovedCamels.size() - 1).identifier);
                isTurnOver = true;
                break;
                
            default:
                System.out.println("Accion invalida");
        }
        } while(isTurnOver != true);
    }

    private void round(Scanner scanner){
        int currentPlayer = 0;
        while (currentPlayer < players.size()){
            if(!board[board.length - 1].camelStack.isEmpty()){
                System.out.println("\n----Final de la ronda----");
                displayBoard();
                placeCamel(board);

                System.out.println("\n----Dinero de la ronda----");
                for(int i = 0; i < players.size(); i++){
                    players.get(i).grade();
                    System.out.println((i + 1) + ".-" + "El jugador " + players.get(i).getName() + " tiene " + players.get(i).getMoney() + " monedas");
                }
                
                System.out.println("\n----Historial de ronda----");
                reverseHistory(history);
                isGameOver = true;
                break;
            }
            if(camels.isEmpty()){ 
                System.out.println("\n----Final de la ronda----");
    
                displayBoard();
                placeCamel(board);

                System.out.println("\n----Dinero de la ronda----");
                for(int i = 0; i < players.size(); i++){
                    players.get(i).grade();
                    System.out.println((i + 1) + ".-" + "El jugador " + players.get(i).getName() + " tiene " + players.get(i).getMoney() + " monedas");
                }

                System.out.println("\n----Historial de ronda----");
                reverseHistory(history);

                for(int i = 0; i < board.length; i++){
                    board[i].modifier.clear();
                }

                for(int i = 0; i < players.size(); i++){
                    players.get(i).fillModifier();
                }

                for(int i = 0; i < alreadyMovedCamels.size(); i++){
                    camels.add(i, alreadyMovedCamels.get(i));
                }
                alreadyMovedCamels.clear();

                for(int i = 0; i < camels.size(); i++){
                    camels.get(i).fillCamelStack();
                }
                
                players.add(players.size(), players.get(0));
                players.remove(0);
                break;
            }
            turn(currentPlayer, scanner);
            currentPlayer++;
        }
    }

    private void game(Scanner scanner){
        while(isGameOver == false){
            round(scanner);
        }   
        System.out.println("\n---- Fin del juego ----\n");
        displayBoard();
        placeCamel(board);
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        CamelUp round = new CamelUp();
        // int playerNum;
        // while (true) {
        //     System.out.println("Inserte el numero de jugadores:");
        //     playerNum = scanner.nextInt();
        //     if (playerNum > 6 || playerNum < 4){
        //         System.out.println("Solo pueden jugar de 4-6 jugadores, inserta un numero valido");
        //     } else {
        //         break;
        //     }       
        // }
        // scanner.nextLine(); // para que scanner no se salte la siguiente llamada a este
        round.setUpRound(4, scanner); 
        //round.round(scanner);
        round.game(scanner);
    }
}

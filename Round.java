import Extras.SimpleLinkedList;
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
    boolean isGameOver = false;

    private void shuffle(SimpleLinkedList<Camel> camelList){
        
        Camel[] tempArr = new Camel[camelList.size()];
        Random random = new Random();

        for (int i = 0; i < camelList.size(); i++){
            tempArr[i] = camelList.get(i);
            camelList.remove(i);
        }
        
        for (int i = 0; i < tempArr.length; i++){
            int randIn = random.nextInt(tempArr.length);
            Camel temp = tempArr[randIn];
            tempArr[randIn] = tempArr[i];
            tempArr[i] = temp;
            camelList.add(i, tempArr[i]);
        }
    }

    /**
     * Prepara el juego 
     * @param num numero de jugadores 
     */
    public void setUpRound(int num){
        
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
            Player jugador = new Player(("Player " + i), 3);
            //jugador.setMoney(jugador.getMoney() + 1);
            jugador.fillCamelCardList();
            playerList.add(i, jugador);
        }

        //Inicializa a los camellos
        for(int i = 0; i < 5; i++){
            Camel camello = new Camel("Camel " + i);
            camello.fillCamelStack();
            //System.out.println(camello.camelStack.top().value);
            camelList.add(i, camello);
        }

        //Mueve a los camellos
        for(int i = 0; i < camelList.size(); i++){
            camelList.get(i).move(camelList.get(i), board);
        }
        System.out.println(camelList);
        shuffle(camelList);
        System.out.println(camelList);
        
        //camelList.add(camelList.size(), camelList.get(0));  Could be useful to switch player's turns
        //camelList.remove(0); Could be useful to switch player's turns

    }

    public static void round(){}

    public static void main(String[] args){
        Round round = new Round();
        round.setUpRound(4);
    }
}

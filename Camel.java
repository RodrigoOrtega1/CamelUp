import java.util.Random;
import Extras.Stack;

/**
 * Clase que modela un camello de CamelUp
 * @author Rodrigo Ortega 318036104
 * @version 1.0 Oct 6 2022
 * @since ED 2023-1
 */

public class Camel {
    /**
     * El identificador del camello
     */
    String identifier;

    /**
     * La casilla actual del camello en el tablero
     */
    int position = 0;
    
    /**
     * La pila de cartas de camello
     */
    Stack<betCard> camelStack = new Stack<>();
    
    /**
     * Lugar en el que va el camello
     */
    int place = 0;


    /**
     * El dado del camello
     */
    int[] die = {3,3,2,2,1,1};

    /**
     * Constructor de un camello
     * @param identifier el identificador del camello;
     */
    public Camel(String identifier){
        this.identifier = identifier;
    }
    
    /**
     * Cambia el atributo posicion
     * @param position nuevo valor de posicion
     */
    public void setPosition(int position){
        this.position = position;
    }

    public void setPlace(int place){
        this.place = place;
    }

    /**
     * Clase interna que representa una carta de apuesta
     */
    public class betCard{
        int value;
        int camelPlace = place;
        String camelName = identifier;

        public betCard(int value){
            this.value = value;
        }

        public void setPlace(int place){
            this.camelPlace = place;
        }

        public String toString(){
            String str = "" + this.value;
            return str;
        }
    }

    /**
     * Gira el dado del camello
     * @return el numero que tiró el dado
     */
    private int roll(){
        int rndNum = new Random().nextInt(die.length);
        return die[rndNum];
    }

    /**
     * Llena la pila del caballo con cartas de apuestas
     */
    public void fillCamelStack(){
        betCard card1 = new betCard(5);
        betCard card2 = new betCard(2);
        betCard card3 = new betCard(1);
        camelStack.push(card3);
        camelStack.push(card3);
        camelStack.push(card2);
        camelStack.push(card1);
    }

    public String toString(){
        return this.identifier;
    }

    /**
     * Mueve el camello
     * @param camel el camello a mover
     * @param board el tablero en el que se mueve el camello
     */
    public void move(Camel camel, Tile[] board){
        int destination = camel.position + camel.roll();
        int len = board[destination].camelStack.size();
        
        if (board[destination].hasModifier){
            if(board[destination].getModifier() == true) {
                destination = camel.position + camel.roll() + 1;
            } else if (board[destination].getModifier() == false) {
                destination = camel.position + camel.roll() - 1;
                //Poner la condicion de cuando retroceden por un modificador
            }
        }
        
        if (board[destination].camelStack.isEmpty()){
            board[destination].camelStack.add(0, camel);
            if(board[camel.position].camelStack.contains(camel)){
                board[camel.position].camelStack.delete(camel);
            }
            camel.setPosition(destination);
        } else if (board[camel.position].camelStack.size() > 1){
            for(int j = board[camel.position].camelStack.indexOf(camel); j < board[camel.position].camelStack.size(); j++){
                board[destination].camelStack.add(len, board[camel.position].camelStack.get(j));
                board[destination].camelStack.get(j).setPosition(destination);
                board[camel.position].camelStack.delete(board[camel.position].camelStack.get(j));
            }
        } else {
            board[destination].camelStack.add(len, camel);
            if(board[camel.position].camelStack.contains(camel)){
                board[camel.position].camelStack.delete(camel);
            }
            camel.setPosition(destination);
        }
    }

}

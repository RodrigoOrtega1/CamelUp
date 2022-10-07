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
     * La posicion actual del camello
     */
    int position = 0;
    
    /**
     * La pila de cartas de camello
     */
    Stack<betCard> camelStack = new Stack<>();
    
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

    /**
     * Clase interna que representa una carta de apuesta
     */
    public class betCard{
        int value;
        public betCard(int value){
            this.value = value;
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
        int i = camel.position + camel.roll();
        int len = board[i].camelStack.size();
        
        if (board[i].hasModifier){
            if(board[i].getModifier() == true) {
                i = camel.position + camel.roll() + 1;
            } else if (board[i].getModifier() == false) {
                i = camel.position + camel.roll() - 1;
                //Poner la condicion de cuando retroceden por un modificador
            }
        }
        
        if (board[i].camelStack.isEmpty()){
            if(board[camel.position].camelStack.contains(camel)){
                board[camel.position].camelStack.delete(camel);
            }
            board[i].camelStack.add(0, camel);
            camel.setPosition(i);
        } else if (board[camel.position].camelStack.size() > 1){
            for(int j = board[camel.position].camelStack.indexOf(camel); j < board[camel.position].camelStack.size(); j++){
                board[i].camelStack.add(len, board[camel.position].camelStack.get(j));
                board[i].camelStack.get(j).setPosition(i);
            }
        } else {
            if(board[camel.position].camelStack.contains(camel)){
                board[camel.position].camelStack.delete(camel);
            }
            board[i].camelStack.add(len, camel);
            camel.setPosition(i);
        }
    }
}

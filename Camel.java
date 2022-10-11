import java.util.Random;
import Extras.*;

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
     * Mueve al camello
     * @param camel el camello a mover
     * @param board el tablero en el que se mueve el camello
     */
    public void move(Camel camel, Tile[] board, Stack<String> history){
        int origin = camel.position;
        int destination = origin + camel.roll();
        int len = board[destination].camelStack.size();
        
        
        if (board[destination].hasModifier()){
            Player owner = board[destination].modifier.get(0).getOwner();
            owner.setMoney(owner.getMoney() + 1); //give player a coin
            history.push(camel.identifier + " cayo en el modificador de " + owner.getName() + " el jugador y recibe +1 moneda");
            if(board[destination].getModifier() == true) {
                destination = destination + 1;
            }
        }

        if (board[origin].camelStack.size() > 1){
            SimpleLinkedList<Camel> temp = new SimpleLinkedList<>();
            for(int i = board[origin].camelStack.indexOf(camel); i < board[origin].camelStack.size(); i++){
                temp.add(temp.size(), board[origin].camelStack.get(i));
            }
            if(board[destination].hasModifier()){
                //owner.setMoney(owner.getMoney() + 1); //give player a coin
                if(board[destination].getModifier() == false){
                    SimpleLinkedList<Camel> temp2 = new SimpleLinkedList<>(); // camellos en la casilla anterior
                    destination = destination - 1;
                    for(int i = 0; i < board[destination].camelStack.size(); i++){
                        temp2.add(temp2.size(), board[destination].camelStack.get(i));
                    }
                    board[destination].camelStack.clear();
                    for (int i = 0; i < temp.size(); i++){
                        board[origin].camelStack.delete(temp.get(i));
                        if(temp2.contains(temp.get(i))){
                            temp2.delete(temp.get(i));
                        }
                        board[destination].camelStack.add(board[destination].camelStack.size(), temp.get(i));
                    }
                    for (int i = 0; i < temp2.size(); i++){
                        board[destination].camelStack.add(board[destination].camelStack.size(), temp2.get(i));
                    }
                }
            } else {
                for (int i = 0; i < temp.size(); i++){
                    board[origin].camelStack.delete(temp.get(i));
                    board[destination].camelStack.add(board[destination].camelStack.size(), temp.get(i));
                }
            }
            for(int j = 0; j < board[destination].camelStack.size(); j++){
                board[destination].camelStack.get(j).setPosition(destination);
            }
        } else {
            if(board[destination].hasModifier()){
                //owner.setMoney(owner.getMoney() + 1); //give player a coin
                if(board[destination].getModifier() == false){
                    destination = destination - 1;
                    board[origin].camelStack.delete(camel);
                    board[destination].camelStack.add(0, camel);
                    camel.setPosition(destination);
                }
            } else {
                board[origin].camelStack.delete(camel);
                board[destination].camelStack.add(len, camel);
                camel.setPosition(destination);
            }
        }
    }
}

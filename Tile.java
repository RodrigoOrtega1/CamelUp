import Extras.SimpleLinkedList;

/** 
 * Modela una casilla de tablero de Camel Up
 * @autor Rodrigo Ortega 318036104
 * @version 1.0 Oct 7 2023
 * @since ED2023-1
 */

public class Tile {
    
    SimpleLinkedList<Player.Modifier> modifier = new SimpleLinkedList<>();

    /**
     * La "pila" de camellos de la casilla
     */
    SimpleLinkedList<Camel> camelStack = new SimpleLinkedList<>();
    
    public String toString(){
        return camelStack.toString();
    }

    /**
     * Checks if the tile has a modifier
     * @return true if the tile has a modifier
     */
    public boolean hasModifier(){
        return !modifier.isEmpty();
    }

    public boolean getModifier(){
        return modifier.get(0).getValue();
    }
    

}

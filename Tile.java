import Extras.SimpleLinkedList;

/** 
 * Modela una casilla de tablero de Camel Up
 * @autor Rodrigo Ortega 318036104
 * @version 1.0 Oct 7 2023
 * @since ED2023-1
 */

public class Tile {
    /**
     * Indica si la casilla tiene un modificador aplicado
     */
     boolean hasModifier = false;

    /**
     * Indica el sentido del modificador
     */
    boolean modifier;

    /**
     * La "pila" de camellos de la casilla
     */
    SimpleLinkedList<Camel> camelStack = new SimpleLinkedList<>();
    
    
    public boolean getHasModifier() {
        return hasModifier;
    }
    public void setHasModifier(boolean hasModifier) {
        this.hasModifier = hasModifier;
    }
    public boolean getModifier() {
        return modifier;
    }
    public void setModifier(boolean modifier) {
        this.modifier = modifier;
    }

    public String toString(){
        return camelStack.toString();
    }
    

}
